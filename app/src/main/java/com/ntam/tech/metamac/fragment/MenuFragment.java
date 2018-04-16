package com.ntam.tech.metamac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ntam.tech.metamac.adapter.HomeMenuItem;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.SharedPref;
import com.ntam.tech.metamac.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuFragment extends Fragment implements OnClickListenerAdapter {
    private RecyclerView recyclerView;
    private ArrayList<Integer> menuImages;
    private ArrayList<String> menuStrings;
    private HomeMenuItem adapter;

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

        initObject();
    }

    private void findViewById(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        initObject();

    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)
            getTotalMessage();
    }


    private void getTotalMessage() {
        RetrofitRequest.getMessageCount(SharedPref.getMyAccount(getContext()).getUserId(), new RetrofitResponse<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                if (integer > 0)
                    adapter.updateMessageCountText(integer);
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }


    private void initObject() {
        // set menu image array
        menuImages = new ArrayList<>();
        menuStrings = new ArrayList<>();

        menuImages.add(R.drawable.attendees);
        menuImages.add(R.drawable.notebook);
        menuImages.add(R.drawable.megaphone);
        menuImages.add(R.drawable.livevoteicon);
        menuImages.add(R.drawable.photoicon);
        menuImages.add(R.drawable.envelope);
        menuImages.add(R.drawable.ic_done_all);
        menuImages.add(R.drawable.ic_fingerprint);
        menuImages.add(R.drawable.about_f);
        menuImages.add(R.drawable.resturant);
        menuImages.add(R.drawable.ic_person);
        menuImages.add(R.drawable.admin);
        // set menu string array
        menuStrings.add("Attendees");
        menuStrings.add("Agenda");
        menuStrings.add("Announcements");
        menuStrings.add("Voting");
        menuStrings.add("Photos");
        menuStrings.add("Messages");
        menuStrings.add("General\nInformation");
        menuStrings.add("Leadership\nPrinciples");
        menuStrings.add("Ferring\nPhilosophy");
        menuStrings.add("Dinner");
        menuStrings.add("Profile");
        menuStrings.add("Admin");
        // check image and string array
        // if not equal throw exception to make app crush
        if (menuImages.size() != menuStrings.size())
            throw new RuntimeException("Image Array Not Equal String Array");
        // set item menu in recycler view
        setHomeMenu();
    }

    private void setHomeMenu() {
        // create gridlayout manager
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        // check if user not admin remove last item
        if (!SharedPref.getMyAccount(getContext()).isAdmin()) {
            menuStrings.remove(menuStrings.size() - 1);
            menuImages.remove(menuImages.size() - 1);
        }
        // create adapter
        adapter = new HomeMenuItem(this, menuStrings, menuImages);
        // set layout manager
        recyclerView.setLayoutManager(manager);
        // set adapter
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                Utils.goToFragment(getActivity(), new AttendeeListFragment(), "Back", null);
                break;
            case 1:
                getActivity().startActivity(new Intent(getContext(), AgendaActivity.class));
                break;
            case 2:
                Utils.goToFragment(getActivity(), new NotificationListFragment(), "Back", null);
                break;
            case 3:
                Utils.goToFragment(getActivity(), new LiveVoteWithRequestsFragment(), "Back", null);
                break;
            case 4:
                Utils.goToFragment(getActivity(), new PhotosFragment(), "Back", null);
                break;
            case 5:
                Utils.goToFragment(getActivity(), new ChatListFragment(), "Back", null);
                break;
            case 6:
                Intent i = new Intent(getContext(), WebViewRequestsActivity.class);
                i.putExtra("key", Constant.INFORMATION_KEY);
                startActivity(i);
                break;
            case 7:
                Intent in = new Intent(getContext(), WebViewRequestsActivity.class);
                in.putExtra("key", Constant.LEADERSHIP_KEY);
                startActivity(in);
                break;
            case 8:
                Bundle bundle2 = new Bundle();
                bundle2.putInt(Constant.INTENT_TWITTER_ABOUT_KEY, AboutAndTwitterFragment.ABOUT_PAGE);
                Utils.goToFragment(getActivity(), new AboutAndTwitterFragment(), "Back", bundle2);
                break;
            case 9:
                Intent intent = new Intent(getContext(), WebViewRequestsActivity.class);
                intent.putExtra("key", Constant.DINNER_KEY);
                startActivity(intent);
                break;
            case 10:
                Utils.goToFragment(getActivity(), new SettingFragment(), "Back", null);
                break;
            case 11:
                Utils.goToFragment(getActivity(), new AdminFragment(), "Back", null);
                break;
        }
    }
}
