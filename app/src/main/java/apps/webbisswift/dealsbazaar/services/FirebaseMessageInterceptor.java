package apps.webbisswift.dealsbazaar.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by biswas on 01/06/2017.
 */

public class FirebaseMessageInterceptor extends FirebaseMessagingService {

    public static final String TAG = "FCMInterceptor";
    @Override
    public void onMessageReceived(RemoteMessage message) {
        //super.onMessageReceived(message);

        Map data = message.getData();
        Log.d(TAG, "New Push Message from: " + message.getFrom());
        Log.d(TAG, "Data: " + data.toString());

        if(data.containsKey("channel")){
            
        }else{
            Log.d(TAG, "No channel Specified.");
        }


    }
}
