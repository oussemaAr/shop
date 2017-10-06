package com.foursquad.ottimashopping.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by oussemaar on 8/6/17.
 * <In Code I Trust />
 */

public class FirebaseInstanceService extends FirebaseInstanceIdService {
    
    private static final String TAG = FirebaseInstanceService.class.getSimpleName();
    
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
