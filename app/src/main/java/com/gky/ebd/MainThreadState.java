package com.gky.ebd;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 凯阳 on 2016/6/6.
 */
public class MainThreadState extends ThreadState {

    public MainThreadState(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public void post(Object event) {
        mEventBus.post(event);
    }
}
