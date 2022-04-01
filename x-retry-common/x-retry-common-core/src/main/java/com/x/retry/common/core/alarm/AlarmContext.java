package com.x.retry.common.core.alarm;

import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author: www.byteblogs.com
 * @date : 2021-11-25 09:38
 */
@Data
public class AlarmContext {

    private String text;

    private String title;

    private String notifyAddress;

    public AlarmContext text(String pattern, Object ...arguments) {
        this.text = MessageFormatter.arrayFormat(pattern, arguments).getMessage();
        return this;
    }

    public AlarmContext text(String text) {
        this.text = text;
        return this;
    }

    public AlarmContext notifyAddress(String notifyAddress) {
        this.notifyAddress = notifyAddress;
        return this;
    }

    public AlarmContext title(String pattern, Object ... arguments) {
        this.title = MessageFormatter.arrayFormat(pattern, arguments).getMessage();
        return this;
    }

    public AlarmContext title(String title) {
        this.title = title;
        return this;
    }

    public static AlarmContext build() {
        return new AlarmContext();
    }


}
