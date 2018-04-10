package com.adroitandroid.p2pchat.ChatBot;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.adroitandroid.p2pchat.R;

import java.util.ArrayList;
import java.util.Locale;

public class ChatbotMainScreen extends AppCompatActivity {

    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Message> messageList;
    ChatListAdapter chatListAdapter;

    ImageView sendBtn;
    EditText textbox;

    boolean isListenerOn = true;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_main_screen);
        messageList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.chatbot_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.
                setLayoutManager(linearLayoutManager);
        chatListAdapter = new ChatListAdapter(this, messageList);

        chatListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount());
            }
        });

        recyclerView.setAdapter(chatListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendBtn = (ImageView) findViewById(R.id.fab_img);
        textbox = (EditText) findViewById(R.id.editText);
        textbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(textbox.getText())){
                    sendBtn.setImageResource(R.drawable.ic_send);
                    isListenerOn = false;
                } else{
                    sendBtn.setImageResource(R.drawable.ic_mic);
                    isListenerOn = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( isListenerOn){
                    speechInput();
                }else{
                    String userMsg = textbox.getText().toString();
                    Message txtUser = new Message( userMsg, Message.USER_MSG);
                    messageList.add(txtUser);

                    Log.i("aichat", "chatscreen : " + userMsg);

                    new BotAsyncConnect(ChatbotMainScreen.this, messageList, chatListAdapter).execute(userMsg, null, null);

                    recyclerView.scrollToPosition(chatListAdapter.getItemCount() -1);
                    textbox.setText("");
                }
            }
        });
    }

    private void speechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textbox.setText(result.get(0));
                }
                break;
            }

        }
    }
}
