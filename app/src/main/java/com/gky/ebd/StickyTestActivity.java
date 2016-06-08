package com.gky.ebd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 凯阳 on 2016/6/7.
 */
public class StickyTestActivity extends AppCompatActivity{

    private static final String TAG = StickyTestActivity.class.getSimpleName();

    private static final boolean DEBUG = true;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickytest);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        mUnbinder.unbind();
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SimpleEvent event){
        if(DEBUG) Log.i(TAG, "onEvent::"+"event:"+event.getEvent());
        Toast.makeText(this, "Current Event:"+event.getEvent()+"/"+getClass().getName(), Toast.LENGTH_SHORT).show();
        SimpleEvent eventTmp = EventBus.getDefault().getStickyEvent(SimpleEvent.class);
        if(eventTmp != null){
            EventBus.getDefault().removeStickyEvent(eventTmp);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBaseEvent(BaseEvent event){
        if(DEBUG) Log.i(TAG, "onBaseEvent::"+"event:"+event.getEvent());
        Toast.makeText(this, "Current Event:"+event.getEvent()+"/"+getClass().getName(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_startactivity)
    public void startactivity(){
        Intent i = new Intent(this, StickyTestActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
