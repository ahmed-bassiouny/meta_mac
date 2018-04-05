package com.ntam.tech.metamac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.activity.AgendaActivity;
import com.ntam.tech.metamac.activity.LogisticsActivity;
import com.ntam.tech.metamac.activity.WebViewRequestsActivity;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.SharedPref;
import com.ntam.tech.metamac.utils.Utils;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private TextView tvSpeeker;
    private TextView tvAttendee;
    private TextView tvAgenda;
    private ImageView ivSpeeker;
    private ImageView ivAttendee;
    private ImageView ivAgenda;
    private TextView tvLogistics;
    private TextView tvNewfeed;
    private TextView tvAnnouncement;
    private ImageView ivLogistics;
    private ImageView ivNewfeed;
    private ImageView ivAnnouncement;
    private TextView tvLiveVote;
    private TextView tvPhoto;
    private TextView tvDinner;
    private ImageView ivLiveVote;
    private ImageView ivPhoto;
    private ImageView ivDinner;
    private TextView tvMessage;
    private TextView tvProfile;
    private TextView tvAbout;
    private ImageView ivMessage;
    private ImageView ivProfile;
    private ImageView ivAbout;
    private TextView tvAdmin;
    private ImageView ivAdmin;
    private TextView tvLeader;
    private ImageView ivLeader;
    private TextView tvInformation;
    private ImageView ivInformation;
    private Toolbar mToolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        onClick();
        setToolbar();
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    private void onClick() {
        tvSpeeker.setOnClickListener(this);
        tvAttendee.setOnClickListener(this);
        tvAgenda.setOnClickListener(this);
        ivSpeeker.setOnClickListener(this);
        ivAttendee.setOnClickListener(this);
        ivAgenda.setOnClickListener(this);
        tvLogistics.setOnClickListener(this);
        tvNewfeed.setOnClickListener(this);
        tvAnnouncement.setOnClickListener(this);
        ivLogistics.setOnClickListener(this);
        ivNewfeed.setOnClickListener(this);
        ivAnnouncement.setOnClickListener(this);
        tvLiveVote.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvDinner.setOnClickListener(this);
        ivLiveVote.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivDinner.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        ivAbout.setOnClickListener(this);
        tvAdmin.setOnClickListener(this);
        ivAdmin.setOnClickListener(this);
        tvLeader.setOnClickListener(this);
        ivLeader.setOnClickListener(this);
        tvInformation.setOnClickListener(this);
        ivInformation.setOnClickListener(this);
    }


    private void findViewById(View view) {
        tvSpeeker = view.findViewById(R.id.tv_speeker);
        tvAttendee = view.findViewById(R.id.tv_attendee);
        tvAgenda = view.findViewById(R.id.tv_agenda);
        ivSpeeker = view.findViewById(R.id.iv_speeker);
        ivAttendee = view.findViewById(R.id.iv_attendee);
        ivAgenda = view.findViewById(R.id.iv_agenda);
        tvLogistics = view.findViewById(R.id.tv_logistics);
        tvNewfeed = view.findViewById(R.id.tv_newfeed);
        tvAnnouncement = view.findViewById(R.id.tv_announcement);
        ivLogistics = view.findViewById(R.id.iv_logistics);
        ivNewfeed = view.findViewById(R.id.iv_newfeed);
        ivAnnouncement = view.findViewById(R.id.iv_announcement);
        tvLiveVote = view.findViewById(R.id.tv_live_vote);
        tvPhoto = view.findViewById(R.id.tv_photo);
        tvDinner = view.findViewById(R.id.tv_dinner);
        ivLiveVote = view.findViewById(R.id.iv_live_vote);
        ivPhoto = view.findViewById(R.id.iv_photo);
        ivDinner = view.findViewById(R.id.iv_dinner);
        tvMessage = view.findViewById(R.id.tv_message);
        tvProfile = view.findViewById(R.id.tv_profile);
        tvAbout = view.findViewById(R.id.tv_about);
        ivMessage = view.findViewById(R.id.iv_message);
        ivProfile = view.findViewById(R.id.iv_profile);
        ivAbout = view.findViewById(R.id.iv_about);
        tvAdmin = view.findViewById(R.id.tv_admin);
        ivAdmin = view.findViewById(R.id.iv_admin);
        mToolbar = view.findViewById(R.id.toolbar);
        tvLeader = view.findViewById(R.id.tv_leader);
        ivLeader = view.findViewById(R.id.iv_leader);
        tvInformation = view.findViewById(R.id.tv_general_information);
        ivInformation = view.findViewById(R.id.iv_general_information);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_speeker:
            case R.id.iv_speeker:
                Utils.goToFragment(getActivity(), new SpeakerListFragment(), "Back", null);
                break;
            case R.id.tv_attendee:
            case R.id.iv_attendee:
                Utils.goToFragment(getActivity(), new AttendeeListFragment(), "Back", null);
                break;

            case R.id.tv_agenda:
            case R.id.iv_agenda:
                getActivity().startActivity(new Intent(getContext(), AgendaActivity.class));
                break;

            case R.id.tv_logistics:
            case R.id.iv_logistics:
                getActivity().startActivity(new Intent(getContext(), LogisticsActivity.class));
                break;

            case R.id.tv_newfeed:
            case R.id.iv_newfeed:
                Utils.goToFragment(getActivity(), new NewsFeedFragment(), "Back", null);
                break;

            case R.id.tv_announcement:
            case R.id.iv_announcement:
                Utils.goToFragment(getActivity(), new NotificationListFragment(), "Back", null);
                break;
            case R.id.tv_live_vote:
            case R.id.iv_live_vote:
                Utils.goToFragment(getActivity(), new LiveVoteWithRequestsFragment(), "Back", null);
                break;

            case R.id.tv_photo:
            case R.id.iv_photo:
                Utils.goToFragment(getActivity(), new PhotosFragment(), "Back", null);
                break;
            case R.id.tv_dinner:
            case R.id.iv_dinner:
                /*Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTENT_TWITTER_ABOUT_KEY,AboutAndTwitterFragment.TWITTER_PAGE);
                Utils.goToFragment(getActivity(), new AboutAndTwitterFragment(), "Back", bundle);*/
                Intent intent = new Intent(getContext(), WebViewRequestsActivity.class);
                intent.putExtra("key",Constant.DINNER_KEY);
                startActivity(intent);

                break;
            case R.id.tv_message:
            case R.id.iv_message:
                Utils.goToFragment(getActivity(), new ChatListFragment(), "Back", null);
                break;
            case R.id.tv_profile:
            case R.id.iv_profile:
                Utils.goToFragment(getActivity(), new SettingFragment(), "Back", null);
                break;
            case R.id.tv_about:
            case R.id.iv_about:
                Bundle bundle2 = new Bundle();
                bundle2.putInt(Constant.INTENT_TWITTER_ABOUT_KEY,AboutAndTwitterFragment.ABOUT_PAGE);
                Utils.goToFragment(getActivity(), new AboutAndTwitterFragment(), "Back", bundle2);
                break;
            case R.id.tv_admin:
            case R.id.iv_admin:
                Utils.goToFragment(getActivity(), new AdminFragment(), "Back", null);
                break;
            case R.id.tv_leader:
            case R.id.iv_leader:
                Intent in = new Intent(getContext(), WebViewRequestsActivity.class);
                in.putExtra("key",Constant.LEADERSHIP_KEY);
                startActivity(in);
                break;
            case R.id.tv_general_information:
            case R.id.iv_general_information:
                Intent i = new Intent(getContext(), WebViewRequestsActivity.class);
                i.putExtra("key",Constant.INFORMATION_KEY);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(SharedPref.getMyAccount(getContext()).isAdmin()){
            tvAdmin.setVisibility(View.VISIBLE);
            ivAdmin.setVisibility(View.VISIBLE);
        }else {
            tvAdmin.setVisibility(View.INVISIBLE);
            ivAdmin.setVisibility(View.INVISIBLE);
        }
        getTotalMessage();
    }

    private void getTotalMessage(){
        RetrofitRequest.getMessageCount(SharedPref.getMyAccount(getContext()).getUserId(), new RetrofitResponse<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                if(integer > 0)
                    tvMessage.setText("Messaging ( "+integer+" )");
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

}
