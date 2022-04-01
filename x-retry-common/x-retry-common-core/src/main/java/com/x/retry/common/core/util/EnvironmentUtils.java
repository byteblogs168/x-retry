package com.x.retry.common.core.util;

import com.x.retry.common.core.context.SpringContext;
import org.springframework.core.env.Environment;

/**
 * 获取环境信息
 *
 * @author: www.byteblogs.com
 * @date : 2021-12-01 14:27
 */
public class EnvironmentUtils {

    private static final Environment environment = SpringContext.applicationContext.getBean(Environment.class);
    public static final String DEFAULT_ENV = "default ";

    /**
     * 获取日志状态
     *
     * @return
     */
    private static Boolean getLogStatus() {

        Environment environment = SpringContext.applicationContext.getBean(Environment.class);
        return environment.getProperty("flaky.retry.log.status", Boolean.class, Boolean.TRUE);
    }

    /**
     * 获取环境
     *
     * @return DEV、FAT、UAT、PROD
     */
    public static String getActiveProfile() {

        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            return DEFAULT_ENV;
        }

        StringBuilder envs = new StringBuilder();
        for (String activeProfile : activeProfiles) {
            envs.append(activeProfile + " ");
        }
        return envs.toString();
    }

}
