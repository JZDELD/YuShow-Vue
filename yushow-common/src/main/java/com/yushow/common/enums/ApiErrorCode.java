package com.yushow.common.enums;

import com.yushow.common.core.api.IErrorCode;

/**
 * REST API 错误码
 *
 * @author kyj
 * @date 2022/6/8 15:38
 */
public enum ApiErrorCode implements IErrorCode {
    /**
     * 成功
     */
    SUCCESS(0, "执行成功"),

    /**
     * 失败
     */
    FAILED(1, "操作失败"),

    /**
     * 无响应
     */
    NO_RESULT(2, "无响应"),

    /**
     * 认证失效，请重新登录
     */
    AUTH_ERROR(401, "认证失效，请重新登录！"),

    /**
     * 权限不足
     */
    NO_PERMISSION(403, "权限不足"),

    /**
     * 参数错误
     */
    PARAM_ERROR(100400, "参数错误"),

    /**
     * 数据不存在
     */
    DATA_NO_FOUND(100404, "数据不存在"),

    /**
     * 系统错误，请稍后再试
     */
    ERROR(500, "系统错误，请稍后再试！");

    private final long code;
    private final String msg;

    ApiErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiErrorCode fromCode(long code) {
        ApiErrorCode[] ecs = ApiErrorCode.values();
        for (ApiErrorCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
    }
}
