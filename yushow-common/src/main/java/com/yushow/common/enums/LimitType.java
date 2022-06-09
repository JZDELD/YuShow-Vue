package com.yushow.common.enums;

/**
 * 限流类型
 *
 * @author kyj
 * @date 2022/6/8 11:51
 */
public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP
}
