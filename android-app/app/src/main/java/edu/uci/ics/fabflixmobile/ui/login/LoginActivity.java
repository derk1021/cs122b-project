package edu.uci.ics.fabflixmobile.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.PersistentCookieStore;
import edu.uci.ics.fabflixmobile.databinding.ActivityLoginBinding;
import edu.uci.ics.fabflixmobile.ui.movielist.MainActivity;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView message;

    /**
     * The default preferences string.
     */
    private final static String PREF_DEFAULT_STRING = "";

    /**
     * The preferences name.
     */
    private final static String PREFS_NAME = PersistentCookieStore.class.getName();

    /**
     * The preferences session cookie key.
     */
    private final static String PREF_SESSION_COOKIE = "session_cookie";

    /*
      In Android, localhost is the address of the device or the emulator.
      To connect to your machine, you need to use the below IP address
     */
    private final String host = "13.56.8.234";
    private final String port = "8080";
    private final String domain = "backend";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // upon creation, inflate and initialize the layout
        setContentView(binding.getRoot());

        if(getJsonSessionCookieString().equals("200")){
            //Complete and destroy login activity once successful
            finish();
            // initialize the activity(page)/destination
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            // activate the list page.
            startActivity(mainIntent);
        }

        username = binding.username;
        password = binding.password;
        message = binding.message;
        final Button loginButton = binding.login;

        //assign a listener to call a function to handle the user request when clicking a button
        loginButton.setOnClickListener(view -> {
            //Complete and destroy login activity once successful
            hideKeyboard(LoginActivity.this);
            username.clearFocus();
            password.clearFocus();
            login();
        });
    }

    private void login(){
        try {
            message.setText("Trying to login");
            String email = username.getText().toString().trim();
            String pwd = password.getText().toString().trim();
            if(email.isEmpty()) username.setError("Email is Required");
            else if(pwd.isEmpty()) password.setError("Password is Required");
            else {
                binding.pgb.setVisibility(View.VISIBLE);
                final RequestQueue queue = NetworkManager.sharedManager(LoginActivity.this).queue;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", email);
                jsonBody.put("password", pwd);
                final String requestBody = jsonBody.toString();

                final StringRequest loginRequest = new StringRequest(Request.Method.POST, baseURL + "/api/login", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        binding.pgb.setVisibility(View.GONE);
                        // TODO: should parse the json response to redirect to appropriate functions
                        //  upon different response value.
                        Log.d("login.success", response);
                        saveSessionCookie(response);
                        //Complete and destroy login activity once successful
                        finish();
                        // initialize the activity(page)/destination
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        // activate the list page.
                        startActivity(mainIntent);
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY onErrorResponse", error.toString());
                        binding.pgb.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        message.setText("Error! Wrong Credentials.");
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                queue.add(loginRequest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private String getJsonSessionCookieString() {
        return getPrefs().getString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);
    }

    private void saveSessionCookie(String response) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(PREF_SESSION_COOKIE, response);
        editor.apply();
    }

    private SharedPreferences getPrefs() {
        return LoginActivity.this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

}