package com.androidstudy.pushchat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    static final String TAG = "GCM_Demo";

    String SENDER_ID = "171794693509";

    ScrollView scrollChatList;

    GoogleCloudMessaging gcm;
    String regid, mTargetRegId;

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    HashMap<String, String> mTargetList = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollChatList = (ScrollView)findViewById(R.id.scrollChatList);

        gcm = GoogleCloudMessaging.getInstance(this);
        registerInBackground();

        initalizeTargetList();
    }

    private void initalizeTargetList()
    {
        mTargetList.put("스노야", "APA91bHxxF-D-xDoFzaFC0BRkVxs05Dxa43X-uj2S_-1_JAjij-nTNgds7tJfGXiSoq-oVaH4c2fXbB6dgH44WrAVGEy-41IjluERGkXL29Ilk6o6mwW7CboYAzEt27ty11BN3dXMxpzO2W0tdNR2gZh7ocmama90w");
        mTargetList.put("멜트", "APA91bH0zkR6P0PtKboTE3xOOM5Ssib3tDFVrzHhBMM_d4iyWs_y9EsUurznqnipxKdbWGLjdgV2s4bK71ORl8_1bjqPmAon8AOMDGLugFYxK8BNEb759qdFuInmwupw7UQDLtErK4L57yPEYToKxvXjw006DS7O7A");
        mTargetList.put("에이든", "APA91bFDqjJ-N1EtEehf841I1Ha0nxkdi_iWnqr8sLx_h9X9wldhmCTj32WWRflb8Jkt_iJ_T2MHU6T6xcSZIVIJdaltlSdmlBB6wSmAM-PCgD0z2_vdG59VUYn6g1-Gqsc2r5jkDoW7Yx-6wqANzhaByiqtPKlO-A");
        mTargetList.put("권터", "APA91bGYEDi_ZPEC6T2lGPxUAls90ASSAqjDF2zB9lPYNmSKQMzh3gJssuyz04GxK1DQT7RNVSs5T3nbJme_VaTkXST0DI6y_baUtN4PMLcRHh8dj7dIU0vIZvMwUk5q1_6BB4u32F17PSlJSNhMLylhfOdqja0nNg");
        mTargetList.put("클로이", "APA91bGM9KAMb2aKJYUmbxp9_w-oRk7NKiWJR8FG7li4ScZq09CO8wscLpUDqkHI8TozLB0f3Jf1-5s-s2v9t9m6dDzR3myZtt0ugZ_I926vOxV2FaQwyUYZY7D-7ya5s23EklsnN6bJNyzDQaC0P2iUF_pEnqLl1Q");
        mTargetList.put("Angel", "APA91bGl0tL0cjk8PP2kA4EgiVssYPVAqRRwZFJ32U5spS55yUkP7FXlRv9lxtMtKuSj0cNriv14cnz48NuPP7yOgoRVsfmERgEyt3t1CNTO7rstG5KbBUoMPxPicx_02wvnFyfaOoYfaQk0r2orq6uggvH5VUV03w");
        mTargetList.put("emmily", "APA91bHj2GrBg8aOD3p4Q_zrh6-1L7kPamB5Nmo2s7ynFKzS2oZXiZlrQCWjmznEgQs0rDrN4-e7NIeejIHEmSTU6fkY44vpO-GFlagtq2gRRpPLxqNrGcIStOsYvy2Soiw3kq9ZYSB0SUejLgtNqnq672IX7gSIKw");
        mTargetList.put("홍(Hong)", "APA91bFiV1SG40xrYA54by0E0zuX2sgJdxJ5MU1mDXFS-Q2-WkGgfkMClk54DC0d2PoQ4vktZqcSE42zeeRTe36GpPO-FprmxA83KdbMIPl5mvvWT3hgEA4_9WWYRQlemqEK0OSijuL19F72x3jl7GBF1dj0oloh1g");
        mTargetList.put("Park", "APA91bHlyqeJdHWMnWcNzdpSk03KuaTNLhNFYcKiLm0pVWh4Iu8SqauU_fNNr04niIUtcrJUSZ8L0gysuQe9g2ncUBqx8AuWIxLthI7sIu3_OEhWWLSmwUEDF_37nFwroKcXi9Dx6qWE7YoR0wQQC7qqgUjACY6otQ");
        mTargetList.put("taiwan", "APA91bG9IK0Qn12QmtHGspfJkwu56Ngie23HOCqSHzGr7HOsEAjzPdGhayGrpt6vCwHRMxVBFxSOQlAxgVuhNZJV1UzbcuJnoXFXrP_iHzv8-VN4zTnqAhjTz5DV3bGyIWn_gWXqCE0O4liayQVM8nIm84Lxz8umcg");
        mTargetList.put("아이린", "APA91bGZ5z9htExdKbCpHOuOoArCykowqdqNQVE2lbkTXLpwUm3n7PttOF4Fa69AQM5qhMd4V5T3VMcnp501WsVQRaWpXA-xZfzRxp9kUYZkmBHfOf2iL9nyfcvgfn7664PombPfVAkbcVAN9-SEu-f4NwUPeQMWiQ");
        mTargetList.put("Geun", "APA91bHvTXBPJ3AUthtpgyRa9r8SNst8-PDxcov0r19vMtkVAXseJXdAqzlHt95_2iZtGO4TsCpdsJ5VAbZL2AfBd3xkKKt2aY3GYT-Tl4bklwW27Q1OmBD3YVrWHjwSs1ACEFUqgn408EbWsjm-5z5hJIVGA0HKnw");
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
        if (view.getId() == R.id.btnSelect) {

            final ArrayList<String> nameList = new ArrayList<String>();
            for (String name : mTargetList.keySet())
                nameList.add(name);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Target");
            builder.setItems(nameList.toArray(new String[0]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String targetName = nameList.get(which);
                    Button btnSelect = (Button)findViewById(R.id.btnSelect);
                    btnSelect.setText(targetName);
                    mTargetRegId = mTargetList.get(targetName);
                }
            });
            builder.show();
        }
        else if (view.getId() == R.id.btnSend) {
            Log.i(TAG, "Push button clicked!");
            EditText edtContent = (EditText)findViewById(R.id.edtContent);
            sendPush(edtContent.getText().toString());
        }
    }

    public static String API_KEY = "AIzaSyDKSIu5JIw_E27pAarcQDnSe2QAM4A8J48";

    public void sendPush(final String msg) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Sender sender = new Sender(API_KEY);
                Message message = new Message.Builder()
                        .addData("msg", msg)
                        .build();
                try {
                    sender.send(message, mTargetRegId, 0);
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
                return null;
            }
        }.execute();

        View item = View.inflate(this, R.layout.item_chat, null);
        TextView lblMessage = (TextView)item.findViewById(R.id.lblMessage);
        lblMessage.setText(msg);

        LinearLayout layoutChatList = (LinearLayout)findViewById(R.id.layoutChatList);
        layoutChatList.addView(item);

        scrollChatList.postDelayed(new Runnable() {
            public void run() {
                scrollChatList.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 500);
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
