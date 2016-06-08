package com.gky.ebd;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * Created by 凯阳 on 2016/6/6.
 */
public class MainEventBus {

    private static final int MAX_THREAD = 5;

    private EventBus mEventBus;

    private static class MainEventBusLoader {
        public static MainEventBus INSTANCE = new MainEventBus();
    }

    private MainEventBus(){
        mEventBus = EventBus.builder()
            .logNoSubscriberMessages(false)
            .logSubscriberExceptions(false)
            .sendNoSubscriberEvent(false)
            .sendSubscriberExceptionEvent(false)
            .executorService(Executors.newFixedThreadPool(MAX_THREAD))
            .eventInheritance(false)
            .addIndex(new EventBusIndex())
            .build();
    }

    public static MainEventBus getInstance(){
        return MainEventBusLoader.INSTANCE;
    }

    public void register(Object subscriber){
        mEventBus.register(subscriber);
    }

    public void unregister(Object subscriber){
        mEventBus.unregister(subscriber);
    }

    public boolean isRegistered(Object subscriber){
        return mEventBus.isRegistered(subscriber);
    }

    public void post(Object event){
        mEventBus.post(event);
    }
}
