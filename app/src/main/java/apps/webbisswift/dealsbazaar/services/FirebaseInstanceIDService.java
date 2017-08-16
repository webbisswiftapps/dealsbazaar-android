package apps.webbisswift.dealsbazaar.services;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by biswas on 01/06/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //TODO: Implement this for server side notifications
    }
}
