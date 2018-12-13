package com.qiniu.droid.rtc.demo.utils;

import de.greenrobot.event.EventBus;

public class EventBusBase {

    private static EventBus eventBus;

    public static EventBus getInstance() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                if (eventBus == null)
                    eventBus = new EventBus();
            }
        }
        return eventBus;
    }
}
