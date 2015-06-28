package com.androidstudy.pushchat;

import android.os.AsyncTask;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class ChatFragment extends Fragment {

    static final String TAG = "ChatFragment";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static String API_KEY = "AIzaSyDKSIu5JIw_E27pAarcQDnSe2QAM4A8J48";
    String SENDER_ID = "171794693509";
    String MY_NICK = "스노야";

    LinearLayout layoutChatList;
    ScrollView scrollChatList;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChatFragment newInstance(int sectionNumber) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        layoutChatList = (LinearLayout)rootView.findViewById(R.id.layoutChatList);
        scrollChatList = (ScrollView)rootView.findViewById(R.id.scrollChatList);

        ArrayList<TalkModel> talkList = MyApp.dbHelper.getLalkLog();
        for (TalkModel talk : talkList)
            addTalk(talk);
        scrollDown();

        return rootView;
    }

    public void sendPush(final String content) {
        final TalkModel talk = new TalkModel();
        talk.author = MY_NICK;
        talk.created = new Date();
        talk.content = content;
        talk.my_talk = true;
        MyApp.dbHelper.addTalk(talk);
        addTalk(talk);
        scrollDown();
/*
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Sender sender = new Sender(API_KEY);
                Message message = new Message.Builder()
                        .addData("author", talk.author)
                        .addData("created", CalUtil.dateToString(talk.created))
                        .addData("content", talk.content)
                        .build();
                try {
                    sender.send(message, mTargetRegIdList, 0);
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
                return null;
            }
        }.execute();*/
    }

    private void addTalk(TalkModel talk) {
        View item = getLayoutInflater(null).inflate(R.layout.item_chat, null);

        LinearLayout layoutChat = (LinearLayout)item.findViewById(R.id.layoutChat);
        if (talk.my_talk) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)layoutChat.getLayoutParams();
            lp.gravity = Gravity.RIGHT;
            lp.leftMargin = DisplayUtil.PixelFromDP(50);
            lp.rightMargin = 0;
            layoutChat.setLayoutParams(lp);
            layoutChat.setBackgroundResource(R.drawable.shape_talk_my);
        }
        else {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)layoutChat.getLayoutParams();
            lp.gravity = Gravity.LEFT;
            lp.leftMargin = 0;
            lp.rightMargin = DisplayUtil.PixelFromDP(50);
            layoutChat.setLayoutParams(lp);
            layoutChat.setBackgroundResource(R.drawable.shape_talk_others);
        }

        // 작성자
        if (talk.author != null) {
            TextView lblAuthor = (TextView)item.findViewById(R.id.lblAuthor);
            lblAuthor.setText(talk.author);
        }

        // 날짜 및 시간
        if (talk.created != null) {
            TextView lblDatetime = (TextView)item.findViewById(R.id.lblDatetime);
            lblDatetime.setText(CalUtil.dateToString(talk.created));
        }

        // 메시지
        if (talk.content != null) {
            TextView lblMessage = (TextView)item.findViewById(R.id.lblMessage);
            lblMessage.setText(talk.content);
        }

        layoutChatList.addView(item);
    }

    private void scrollDown() {
        scrollChatList.post(new Runnable() {
            public void run() {
                scrollChatList.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
