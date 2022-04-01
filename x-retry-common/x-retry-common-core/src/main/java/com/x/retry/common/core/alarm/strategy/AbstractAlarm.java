package com.x.retry.common.core.alarm.strategy;

import com.x.retry.common.core.alarm.Alarm;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: www.byteblogs.com
 * @date : 2021-11-25 09:19
 */
public abstract class AbstractAlarm<T> implements Alarm<T> {

   protected static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

}
