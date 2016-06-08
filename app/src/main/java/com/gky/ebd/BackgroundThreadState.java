package com.gky.ebd;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 凯阳 on 2016/6/6.
 */
public class BackgroundThreadState extends ThreadState{

    public BackgroundThreadState(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public void post(final Object event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mEventBus.post(event);
            }
        },getClass().getSimpleName()).start();
    }
}
