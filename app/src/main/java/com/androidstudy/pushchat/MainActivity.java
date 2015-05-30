package com.androidstudy.pushchat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    static final String TAG = "GCM_Demo";

    String SENDER_ID = "171794693509";

    GoogleCloudMessaging gcm;
    String regid;

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gcm = GoogleCloudMessaging.getInstance(this);
        registerInBackground();
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String msg = "";
                try {
                    regid = gcm.register(SENDER_ID);
                    Log.i(TAG, "[GCM Registration Success] " + regid);
                }
                catch (IOException e) {
                    Log.i(TAG, "[GCM Registration Failed] " + e.getMessage());
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String s) {
            }
        }.execute(null, null, null);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnSend) {
            Log.i(TAG, "Push button clicked!");
            sendPush("안녕하세요. 테스트입니다!");
        }
    }

    public static String API_KEY = "AIzaSyDKSIu5JIw_E27pAarcQDnSe2QAM4A8J48";
    // 여기에 상대방의 Registration ID를 넣음
    public static String TARGET_REG_ID = "APA91bHxxF-D-xDoFzaFC0BRkVxs05Dxa43X-uj2S_-1_JAjij-nTNgds7tJfGXiSoq-oVaH4c2fXbB6dgH44WrAVGEy-41IjluERGkXL29Ilk6o6mwW7CboYAzEt27ty11BN3dXMxpzO2W0tdNR2gZh7ocmama90w";

    public void sendPush(final String msg) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Sender sender = new Sender(API_KEY);
                Message message = new Message.Builder()
                        .addData("msg", msg)
                        .build();
                try {
                    sender.send(message, TARGET_REG_ID, 0);
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
