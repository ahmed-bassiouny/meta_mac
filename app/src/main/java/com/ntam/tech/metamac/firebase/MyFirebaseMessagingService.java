package com.ntam.tech.metamac.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ntam.tech.metamac.utils.MyNotification;

/**
 * Created by bassiouny on 24/10/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new MyNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()).showNotification();
    }

}
