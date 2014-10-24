package andm.simplecarbonfiberwatchface;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;





public  class watch_activity extends Activity
        implements sleep_activity.Listener {

    private static final String LOG_TAG = watch_activity.class.getName();


    private TextView mDateTimeTextView;
    private boolean mScreenDim = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchface_activity);
        mDateTimeTextView = (TextView) findViewById(R.id.dateTime);


        registerReceiver(this.mIntentTimeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        sleep_activity.attach(this, savedInstanceState, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
        unregisterReceiver(mIntentTimeTickReceiver);
    }
    private final BroadcastReceiver mIntentTimeTickReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent){
            if ("android.intent.action.TIME_TICK".equals(paramAnonymousIntent.getAction())){
                Log.d(LOG_TAG, "Received TIME_TICK event");
                updateUI();
            }
        }
    };

    @Override
    public void onScreenDim() {
        Log.d(LOG_TAG, "onScreenDim()");
        mScreenDim = true;

        updateUI();

    }

    @Override
    public void onScreenAwake() {
        Log.d(LOG_TAG, "onAwake()");
        mScreenDim = false;
        updateUI();

    }

    @Override
    public void onWatchFaceRemoved() {
        Log.d(LOG_TAG, "onWatchFaceRemoved()");
        mScreenDim = true;

    }

    @Override
    public void onScreenOff() {
        Log.d(LOG_TAG, "onScreenOff()");
        mScreenDim = true;
        updateUI();


    }

    private void updateUI(){
        Log.d(LOG_TAG, "updating UI - mScreenDim = " + mScreenDim);
        if (mScreenDim) return;


        Date now = new Date();
        String str = new SimpleDateFormat("EEE,MMM d").format(now);
        mDateTimeTextView.setText(str);

    }
}
