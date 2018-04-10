package com.adroitandroid.p2pchat.ChatBot;


import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;
import ai.api.model.AIContext;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class BotAsyncConnect extends AsyncTask<String, Void, AIResponse> {

    private Gson gson = GsonFactory.getGson();
    private AIDataService aiDataService;
    Context context;
    ChatListAdapter chatListAdapter;
    List<Message> messageList;
    TextToSpeech tts;

    public BotAsyncConnect(Context context, List<Message> messageList, ChatListAdapter chatListAdapter) {
        this.context = context;
        this.messageList = messageList;
        this.chatListAdapter = chatListAdapter;
        tts=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }

            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        final LanguageConfig config = new LanguageConfig("en", "f115d10eb377462e8f2373934a939d1c");
        initService(config);
    }

    @Override
    protected AIResponse doInBackground(final String... params) {

        final AIRequest request = new AIRequest();
        String query = params[0];
        String event = params[1];
        String context = params[2];
        Log.i("aichat", "request : "+ query);

        if (!TextUtils.isEmpty(query)){
            request.setQuery(query);
        }

        if (!TextUtils.isEmpty(event)){
            request.setEvent(new AIEvent(event));
        }

        RequestExtras requestExtras = null;
        if (!TextUtils.isEmpty(context)) {
            final List<AIContext> contexts = Collections.singletonList(new AIContext(context));
            requestExtras = new RequestExtras(contexts, null);
        }

        try {
            return aiDataService.request(request, requestExtras);
        } catch (final AIServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(final AIResponse response) {
        if (response != null) {
            gson.toJson(response);
            final Result result = response.getResult();

            String speech = result.getFulfillment().getSpeech();

            Message botMsg = new Message(speech,Message.BOT_MSG);
            messageList.add(botMsg);
            tts.speak(botMsg.msg,TextToSpeech.QUEUE_FLUSH,null);
            chatListAdapter.notifyDataSetChanged();

            Log.i("aichat", "response : "+speech);
        } else {
            Log.i("debug", "Post Execute Error");
        }
    }


    private void initService(final LanguageConfig languageConfig) {
        final AIConfiguration.SupportedLanguages lang =  AIConfiguration.SupportedLanguages.fromLanguageTag(languageConfig.getLanguageCode());
        final AIConfiguration config = new AIConfiguration(languageConfig.getAccessToken(), lang, AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(context, config);
    }

}
