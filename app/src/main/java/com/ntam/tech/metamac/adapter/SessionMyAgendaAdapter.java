package com.ntam.tech.metamac.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.activity.SessionActivity;
import com.ntam.tech.metamac.fragment.SessionFragment;
import com.ntam.tech.metamac.model.Session;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.Utils;

import java.util.List;

/**
 * Created by ahmed on 11/10/17.
 */

public class SessionMyAgendaAdapter extends RecyclerView.Adapter<SessionMyAgendaAdapter.CutomViewHolder> {


    List<Session> sessions;
    FragmentActivity context;
    public SessionMyAgendaAdapter(FragmentActivity context) {
        this.context=context;
    }

    @Override
    public SessionMyAgendaAdapter.CutomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_agenda, null);
        CutomViewHolder cutomViewHolder = new CutomViewHolder(view);
        return cutomViewHolder;
    }

    @Override
    public void onBindViewHolder(SessionMyAgendaAdapter.CutomViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.tvName.setText(session.getSessionName());
        holder.tvStartTime.setText(session.getStartTime());
        holder.tvEndTime.setText(session.getEndTime());

        holder.tvInterested.setText(session.getSessioninterested());
        holder.tvLike.setText(session.getSessionLikes());
        holder.tvChat.setText(session.getSessionComments()+"");
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    class CutomViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvName;
        private TextView tvInterested;
        private TextView tvLike;
        private TextView tvChat;

        public CutomViewHolder(View view) {
            super(view);
            tvStartTime = view.findViewById(R.id.tv_start_time);
            tvEndTime = view.findViewById(R.id.tv_end_time);
            tvName = view.findViewById(R.id.tv_name);
            tvInterested = view.findViewById(R.id.tv_interested);
            tvLike = view.findViewById(R.id.tv_like);
            tvChat = view.findViewById(R.id.tv_chat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Session session = sessions.get(getAdapterPosition());
                    Intent intent = new Intent(context, SessionActivity.class);
                    intent.putExtra(Constant.INTENT_SESSION_KEY,  session);
                    context.startActivity(intent);*/
                }
            });

        }
    }
    public void setData(List<Session> sessions){
        this.sessions = sessions;
    }
}
