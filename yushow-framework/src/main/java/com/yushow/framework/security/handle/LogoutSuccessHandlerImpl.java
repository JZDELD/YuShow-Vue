package com.yushow.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.yushow.common.constant.Constant;
import com.yushow.common.core.api.AjaxResult;
import com.yushow.common.core.domain.model.LoginUser;
import com.yushow.common.utils.ServletUtils;
import com.yushow.common.utils.StringUtils;
import com.yushow.framework.manager.AsyncManager;
import com.yushow.framework.manager.factory.AsyncFactory;
import com.yushow.framework.web.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author yushow
 */
@Configuration
@RequiredArgsConstructor
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private final TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constant.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.ok("退出成功")));
    }
}
