package com.ntam.tech.thefutureisnow.firebase;

import android.util.Log;

import com.ntam.tech.thefutureisnow.utils.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ahmed on 11/10/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e( "onTokenRefresh: ",refreshedToken );
        SharedPref.setToken(this,refreshedToken);
    }
}
