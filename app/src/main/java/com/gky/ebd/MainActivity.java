package com.gky.ebd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final boolean DEBUG = true;

    private ThreadState mThreadState;

    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mEventBus = EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
        mThreadState = new MainThreadState(mEventBus);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onHandleEvent(MessageEvent event){
        if(DEBUG) Log.i(TAG, "onHandleEvent");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+event.getMessage());
        /*Toast.makeText(this,"onHandleEvent:"+"Current Thead:"+Thread.currentThread().getName()+" Message:"+event.getMessage(),
            Toast.LENGTH_LONG).show();*/
        mEventBus.cancelEventDelivery(event);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onHandleEventAysc(MessageEvent event){
        if(DEBUG) Log.i(TAG, "onHandleEventAysc");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+event.getMessage());
        /*Toast.makeText(this,"onHandleEventAysc:"+"Current Thead:"+Thread.currentThread().getName()+" Message:"+event.getMessage(),
            Toast.LENGTH_LONG).show();*/
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onHandleEventBackground(MessageEvent event){
        if(DEBUG) Log.i(TAG, "onHandleEventBackground");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+event.getMessage());
        /*Toast.makeText(this,"onHandleEventBackground:"+"Current Thead:"+Thread.currentThread().getName()+" Message:"+event.getMessage(),
            Toast.LENGTH_LONG).show();*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onHandleEventMain(MessageEvent event){
        if(DEBUG) Log.i(TAG, "onHandleEventMain");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+event.getMessage());
        Toast.makeText(this,"onHandleEventMain:"+"Current Thead:"+Thread.currentThread().getName()+" Message:"+event.getMessage(),
            Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2)
    public void onHandleEventMain2(MessageEvent event){
        if(DEBUG) Log.i(TAG, "onHandleEventMain2");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+event.getMessage());
        Toast.makeText(this,"onHandleEventMain2:"+"Current Thead:"+Thread.currentThread().getName()+" Message:"+event.getMessage(),
            Toast.LENGTH_LONG).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleEventExpection(ThrowableFailureEvent failureEvent){
        if(DEBUG) Log.i(TAG, "onHandleEventMain2");
        if(DEBUG) Log.i(TAG,"Current Thread:"+Thread.currentThread().getName());
        if(DEBUG) Log.i(TAG,"Message:"+failureEvent.getThrowable().toString());
    }

    @OnClick(R.id.bt_register)
    public void register(){
        if(mEventBus.isRegistered(this)){
            Toast.makeText(this, "Have regitsered event", Toast.LENGTH_SHORT).show();
            return;
        }
        mEventBus.register(this);
        Toast.makeText(this, "Register Event", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_unregister)
    public void unregister(){
        if(!mEventBus.isRegistered(this)){
            Toast.makeText(this, "Have not registered event", Toast.LENGTH_SHORT).show();
            return;
        }
        mEventBus.unregister(this);
        Toast.makeText(this, "Unregister Event", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_post)
    public void postEvent(){
        if(!mEventBus.isRegistered(this)){
            Toast.makeText(this, "Have not register event", Toast.LENGTH_SHORT).show();
            return;
        }
        mThreadState.post(new MessageEvent("new Message from MainActivity"));
    }

    @OnClick(R.id.bt_threadswitch)
    public void threadSwitch(){
        if(mThreadState instanceof MainThreadState){
            mThreadState = new BackgroundThreadState(mEventBus);
            ((Button)ButterKnife.findById(this, R.id.bt_threadswitch)).setText("Thread Switch(Background)");
        }else{
            mThreadState = new MainThreadState(mEventBus);
            ((Button)ButterKnife.findById(this, R.id.bt_threadswitch)).setText("Thread Switch(Main)");
        }
    }

    @OnClick(R.id.bt_poststicky)
    public void postSticky(){
        if(DEBUG) Log.i(TAG, "post sticky event");
        mEventBus.postSticky(new SimpleEvent("A sticky event "+ System.currentTimeMillis()));
    }

    @OnClick(R.id.bt_startactivity)
    public void startactivity(){
        Intent i = new Intent(this, StickyTestActivity.class);
        startActivity(i);
    }
}
