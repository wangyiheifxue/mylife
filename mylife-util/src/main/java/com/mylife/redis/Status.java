package com.mylife.redis;

import java.util.concurrent.TimeUnit;

/**
 * @descirption : 状态枚举
 * @author : wyh
 * @date : 2020/6/28 11:41
 */
public abstract class Status {

    /**
     * 过期时间相关枚举
     */
    public static enum ExpireEnum{
        UNREAD_MSG(30L, TimeUnit.DAYS);//未读消息的有效期为30天

        /**
         * 过期时间
         */
        private Long time;
        /**
         * 时间单位
         */
        private TimeUnit timeUnit;

        ExpireEnum(Long time, TimeUnit timeUnit) {
            this.time = time;
            this.timeUnit = timeUnit;
        }

        public Long getTime() {
            return time;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }
}

