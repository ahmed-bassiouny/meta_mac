package com.ntam.tech.metamac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.fragment.NewsFeedFragment;
import com.ntam.tech.metamac.utils.Utils;

public class MainContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.goToFragment(this,new NewsFeedFragment(),null,null);
    }
}
