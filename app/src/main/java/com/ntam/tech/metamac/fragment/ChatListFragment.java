package com.ntam.tech.metamac.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.activity.ChatActivity;
import com.ntam.tech.metamac.adapter.ChatListAdapter;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;
import com.ntam.tech.metamac.model.Chat;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.SharedPref;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    private Toolbar mToolbar;
    RecyclerView recycleview;
    ProgressBar progress;
    ChatListAdapter chatListAdapter;
    TextView tvNoMessage;

    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if(getActivity() == null)
            return;
        RetrofitRequest.getChatList(SharedPref.getMyAccount(getActivity()).getUserId(), new RetrofitResponse<List<Chat>>() {
            @Override
            public void onSuccess(final List<Chat> chats) {
                if (chats.size() == 0) {
                    recycleview.setVisibility(View.GONE);
                    tvNoMessage.setVisibility(View.VISIBLE);
                }
                if(getActivity() == null)
                    return;
                chatListAdapter = new ChatListAdapter(chats, getActivity(), new OnClickListenerAdapter() {
                    @Override
                    public void onClick(int position) {
                        Intent intent= new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(Constant.INTENT_ANOTHER_USER_ID_KEY,chats.get(position).getId());
                        intent.putExtra(Constant.INTENT_ANOTHER_USER_IMAGE_KEY,chats.get(position).getImage());
                        startActivity(intent);
                    }
                });
                recycleview.setAdapter(chatListAdapter);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String errorMessage) {
                if(getActivity() == null)
                    return;
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViewById(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        recycleview = view.findViewById(R.id.recycleview);
        progress = view.findViewById(R.id.progress);
        tvNoMessage = view.findViewById(R.id.tv_no_message);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Messages");

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
