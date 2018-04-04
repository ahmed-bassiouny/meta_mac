package com.ntam.tech.metamac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.fragment.SessionFragment;
import com.ntam.tech.metamac.utils.Constant;
import com.ntam.tech.metamac.utils.Utils;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.INTENT_SESSION_KEY,  getIntent().getSerializableExtra(Constant.INTENT_SESSION_KEY));
        Utils.goToFragment(this,new SessionFragment(),null,bundle);
    }
}
