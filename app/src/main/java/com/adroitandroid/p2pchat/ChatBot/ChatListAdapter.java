package com.adroitandroid.p2pchat.ChatBot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.adroitandroid.p2pchat.R;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context context;
    private List<Message> messagesList;

    public ChatListAdapter(Context context, List<Message> messagesList) {
        this.context = context;
        this.messagesList = messagesList;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msglist,parent,false);
        return new ChatListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        ViewHolder myHolder = holder;
        Message current = messagesList.get(position);

        if(current.owner == Message.BOT_MSG){
            myHolder.bot.setText(current.msg);
            myHolder.user.setVisibility(View.INVISIBLE);

        }else{
            myHolder.user.setText(current.msg);
            myHolder.bot.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        TextView bot, user;
        RelativeLayout botSide, userSide;

        public ViewHolder(View itemView) {
            super(itemView);

            bot = (TextView) itemView.findViewById(R.id.bot_msg);
            user = (TextView) itemView.findViewById(R.id.user_msg);
        }
    }
}
