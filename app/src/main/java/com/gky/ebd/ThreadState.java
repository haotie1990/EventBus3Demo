package com.gky.ebd;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 凯阳 on 2016/6/6.
 */
public abstract class ThreadState {

    protected EventBus mEventBus;

    public ThreadState(EventBus eventBus) {
        this.mEventBus = eventBus;
    }

    public abstract void post(Object event);
}
