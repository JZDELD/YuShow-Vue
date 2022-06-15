package com.yushow.framework.web.service;

import com.yushow.common.constant.Constant;
import com.yushow.common.constant.RedisKeyConstant;
import com.yushow.common.constant.UserConstant;
import com.yushow.common.core.domain.entity.SysUser;
import com.yushow.common.core.domain.model.RegisterBody;
import com.yushow.common.core.redis.RedisCache;
import com.yushow.common.exception.user.CaptchaException;
import com.yushow.common.exception.user.CaptchaExpireException;
import com.yushow.common.utils.MessageUtils;
import com.yushow.common.utils.SecurityUtils;
import com.yushow.common.utils.StringUtils;
import com.yushow.framework.manager.AsyncManager;
import com.yushow.framework.manager.factory.AsyncFactory;
import com.yushow.system.service.ISysConfigService;
import com.yushow.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
@RequiredArgsConstructor
public class SysRegisterService {
    private final RedisCache redisCache;

    private final ISysUserService userService;
    private final ISysConfigService configService;


    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstant.USERNAME_MIN_LENGTH
                || username.length() > UserConstant.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstant.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constant.REGISTER,
                        MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = RedisKeyConstant.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
