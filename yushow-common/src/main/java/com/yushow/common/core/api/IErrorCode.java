package com.yushow.common.core.api;

/**
 * REST API 错误码接口
 *
 * @author kyj
 * @date 2022/6/8 15:39
 */
public interface IErrorCode {

    /**
     * 错误编码 -1、失败 0、成功
     *
     * @return
     */
    long getCode();

    /**
     * 错误描述
     *
     * @return
     */
    String getMsg();
}
