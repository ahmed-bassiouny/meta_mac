package com.ntam.tech.metamac.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.activity.SessionActivity;
import com.ntam.tech.metamac.fragment.SessionFragment;
import com.ntam.tech.metamac.interfaces.AgendaInterface;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;
import com.ntam.tech.metamac.model.Session;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.Utils;

import java.util.List;

/**
 * Created by ahmed on 11/10/17.
 */

public class SessionAgendaAdapter extends RecyclerView.Adapter<SessionAgendaAdapter.CutomViewHolder> {


    List<Session> sessions;
    FragmentActivity context;
    AgendaInterface onClickListenerAdapter;

    public SessionAgendaAdapter(FragmentActivity context) {
        this.context = context;
        this.onClickListenerAdapter = (AgendaInterface) context;
    }

    @Override
    public SessionAgendaAdapter.CutomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, null);
        CutomViewHolder cutomViewHolder = new CutomViewHolder(view);
        return cutomViewHolder;
    }

    @Override
    public void onBindViewHolder(SessionAgendaAdapter.CutomViewHolder holder, final int position) {
        Session session = sessions.get(position);
        holder.tvSessionName.setText(session.getSessionName());
        if(session.getVenue().isEmpty()){
            holder.tvSessionLocation.setVisibility(View.GONE);
        }else {
            holder.tvSessionLocation.setText(session.getVenue());
            holder.tvSessionLocation.setVisibility(View.VISIBLE);
        }
        if(session.getSpeaker().size()==0) {
            holder.tvSessionSpeaker.setVisibility(View.GONE);
        }else {
            holder.tvSessionSpeaker.setVisibility(View.VISIBLE);
            holder.tvSessionSpeaker.setText(session.getSpeaker().get(0).getName());
        }
        holder.tvSessionTime.setText(session.getFullTimeSession());
        if (session.isMyAgenda()) {
            holder.ivAddToMyAgenda.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.calendarred));
            holder.ivAddToMyAgenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListenerAdapter.removeToMyAgenda(position);
                }
            });
        }
        else {
            holder.ivAddToMyAgenda.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.calendar));
            holder.ivAddToMyAgenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListenerAdapter.addToMyAgenda(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    class CutomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSessionName;
        private TextView tvSessionTime;
        private TextView tvSessionLocation;
        private TextView tvSessionSpeaker;
        private ImageView ivAddToMyAgenda;

        public CutomViewHolder(View view) {
            super(view);
            tvSessionName = view.findViewById(R.id.tv_session_name);
            tvSessionTime = view.findViewById(R.id.tv_session_time);
            tvSessionLocation = view.findViewById(R.id.tv_session_location);
            tvSessionSpeaker = view.findViewById(R.id.tv_session_speaker);
            ivAddToMyAgenda = view.findViewById(R.id.iv_add_to_my_agenda);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*    Session session = sessions.get(getAdapterPosition());
                    Intent intent = new Intent(context, SessionActivity.class);
                    intent.putExtra(Constant.INTENT_SESSION_KEY,  session);
                    context.startActivity(intent);*/
                }
            });

        }
    }

    public void setData(List<Session> sessions) {
        this.sessions = sessions;
    }
    public void updateData(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
    }
}
