package com.example.rennan.neoedu;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import java.io.UnsupportedEncodingException;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.http.HttpResponseCache;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by Rennan on 21/11/2016.
 * A login screen that offers login via email/password.
 */
public class EntrarActivity extends AppCompatActivity implements OnClickListener {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    EditText mEmailView;
    EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private int p;
    String uemail;
    String unome;
    String uimg;
    String ulogin;
    boolean retorna = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.edtUser);
        View focusView = mEmailView;
       // focusView.clearFocus();

        mPasswordView = (EditText) findViewById(R.id.edtPass);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.btnEntrar);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mEmailView.setText("mateus_v");
        mPasswordView.setText("123456789");

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) ){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if(!isTextValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_text));
            focusView = mPasswordView;
            cancel = true;
        } else if(isTextSmall(password)){
            mPasswordView.setError(getString(R.string.error_small_text));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isTextValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_text));
            focusView = mEmailView;
            cancel = true;
        } else if (isTextSmall(email)) {
            mEmailView.setError(getString(R.string.error_small_text));
            focusView = mEmailView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.


            email = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

            showProgress(true);

        }
    }

    private boolean isTextValid(String text) {
        //TODO: Replace this with your own logic
        String caractere= "qwertyuiopasdfghjklçzxcvbnméáóíã"+
                "QWERTYUIOPASDFGHJKLÇZXCVBNMÁÓÍÉÃ-_0123456789";
        boolean existe;
        for(int i=0; i<text.length();i++){
            existe = false;
            for(int j=0; j<caractere.length();j++){
                if (text.charAt(i)==caractere.charAt(j)){ existe=true; }
            }
            if(!existe){return false;}
        }
        return true;
    }

    private boolean isTextSmall(String text) {
        //TODO: Replace this with your own logic

        return text.length() < 8;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        p= findViewById(R.id.imgFlat).getBottom()+55+(findViewById(R.id.chbLembrar).getBottom()+findViewById(R.id.txtEsqueci).getBottom())/2;
        switch (view.getId()){
            case R.id.txtCadastrar:
                startActivity(new Intent(getApplicationContext(), CadastrarActivity.class));
                break;
            case R.id.txtEsqueci:

                p= findViewById(R.id.imgFlat).getBottom()+55+(findViewById(R.id.chbLembrar).getBottom()+findViewById(R.id.txtEsqueci).getBottom())/2;
                Toast toast = Toast.makeText(getApplicationContext(), "Indisponível no momento!",
                    Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,p);
                toast.show();
                break;

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            //String luser = mEmailView.toString();
            //String lpass = mPasswordView.getText();
            hideKeyboard(getApplicationContext(),mEmailView);
            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/estudante/login";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast.makeText(EntrarActivity.this, "Dados de usuario incorreto...", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    retorna = true;
                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR

                                    uemail =jsonArray.getJSONObject(0).getString("nm_email");
                                    unome = jsonArray.getJSONObject(0).getString("nm_estudante");
                                    uimg = jsonArray.getJSONObject(0).getString("ds_img");
                                    ulogin = jsonArray.getJSONObject(0).getString("nm_login_estudante");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            //Toast.makeText(EntrarActivity.this, "Usuário e/ou senha inválidos", Toast.LENGTH_SHORT).show();
                            retorna = false;
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("nm_login_estudante", mEmail);
                    parametros.put("nm_senha", mPassword);
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(1500);

            } catch (InterruptedException e) {
                return retorna;
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (retorna) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("email",uemail).putExtra("img",uimg).putExtra("nome",unome).putExtra("login",ulogin));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválidos",
                        Toast.LENGTH_LONG);

                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,p);
                toast.show();
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

