package edu.uci.ics.fabflixmobile.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;
import edu.uci.ics.fabflixmobile.ui.movielist.MainActivity;

public class NetworkManager {
    private static NetworkManager instance = null;
    public RequestQueue queue;

    private NetworkManager() {
        NukeSSLCerts.nuke();  // disable ssl cert self-sign check
    }

    public static NetworkManager sharedManager(Context ctx) {
        if (instance == null) {
            instance = new NetworkManager();
            // Create a new cookie store, which handles sessions information with the server.
            // This cookie store will be shared across all the network requests.
            CookieHandler.setDefault(new CookieManager());
//            CookieHandler.setDefault(new CookieManager(new PersistentCookieStore(ctx.getApplicationContext()), CookiePolicy.ACCEPT_ALL));
            instance.queue = Volley.newRequestQueue(ctx.getApplicationContext());
        }

        return instance;
    }
}
