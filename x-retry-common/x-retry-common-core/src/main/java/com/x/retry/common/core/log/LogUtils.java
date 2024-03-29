package com.x.retry.common.core.log;

import com.x.retry.common.core.context.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

/**
 * 全局日志
 *
 * @author: www.byteblogs.com
 * @date : 2021-11-19 16:27
 */
public class LogUtils {

    private LogUtils() {
    }

    public static void debug(Logger log, String format, Object... arguments) {

        if (!getLogStatus()) {
            return;
        }

        log.debug(format, arguments);

    }

    public static void info(Logger log, String format, Object... arguments) {

        if (!getLogStatus()) {
            return;
        }

        log.info(format, arguments);

    }

    public static void error(Logger log, String format, Object... arguments) {
        if (!getLogStatus()) {
            return;
        }

        log.error(format, arguments);
    }

    public static void error(Logger log, String format, Throwable t) {

        if (!getLogStatus()) {
            return;
        }

        log.error(format, t);
    }

    public static void warn(Logger log, String format, Object... arguments) {
        if (!getLogStatus()) {
            return;
        }

        log.warn(format, arguments);
    }

    /**
     * 获取日志状态
     *
     * @return
     */
    private static Boolean getLogStatus() {

        try {
            Environment environment = SpringContext.applicationContext.getBean(Environment.class);
            return environment.getProperty("x.retry.log.status", Boolean.class, Boolean.TRUE);
        } catch (Exception ignored) {
        }

        return Boolean.TRUE;
    }
}
