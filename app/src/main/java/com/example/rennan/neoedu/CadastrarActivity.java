package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rennan on 20/11/2016.
 * Uma tela de login que oferece login via e-mail / senha.
 */
public class CadastrarActivity extends AppCompatActivity implements OnClickListener {

    /**
     * ID para identidade solicitação de permissão READ_CONTACTS.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Uma loja de autenticação fictícia contendo nomes de usuário e senhas conhecidos.
     * TODO: remover após a conexão a um sistema de autenticação real.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Acompanhe a tarefa de login para garantir que podemos cancelá-la, se solicitado.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mPasswordView, mPassword2View, mEmailView, mUserView, mNomeView;
    private View mProgressView, mLoginFormView;
    private ImageView flat;
    private Button mEmailSignInButton;
    String uemail;
    String unome;
    String uimg;
    String ulogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        // Configurar o login form.
        mEmailView = (EditText) findViewById(R.id.edtEmail);
        mUserView = (EditText) findViewById(R.id.edtUsuario);
        mNomeView = (EditText) findViewById(R.id.edtNome);
        mPasswordView = (EditText) findViewById(R.id.edtPassword);
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
        mPassword2View = (EditText) findViewById(R.id.edtPassword2);
        mPassword2View.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.btnCadastrar);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.registrar_form);
        mProgressView = findViewById(R.id.login_progress);
        flat = (ImageView) findViewById(R.id.imgFlat);

        mNomeView.setText("Rennan Moreira Pinto");
        mUserView.setText("rennanmp");
        mEmailView.setText("rennan@gmail.com");
        mPasswordView.setText("12345678");
        mPassword2View.setText("12345678");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnVoltar) {
            finish();
        }
    }


    /**
     * Tentativas de entrar ou registrar a conta especificada pelo formulário de login.
     * Se houver erros de formulário (e-mail inválido, campos em falta, etc.), o
     * Erros são apresentados e nenhuma tentativa de login real é feita.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Redefinir erros.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPassword2View.setError(null);
        mNomeView.setError(null);
        mUserView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String nome = mNomeView.getText().toString();
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPassword2View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Verifique se há uma senha válida, se o usuário digitar uma.
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError("Este campo é obrigatório!");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_text));
            focusView = mPasswordView;
            cancel = true;
        }
        if (isPasswordIden(password,password2)){
            mPasswordView.setError("As senhas não coincidem");
            focusView = mPasswordView;
            cancel = true;
        }

        //Verifique se há um endereço de e-mail válido.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_text));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            flat.setPadding(100,0,100,100);

            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, nome, user);
            mAuthTask.execute((Void) null);

            //Intent next = new Intent(this, HomeActivity.class);
            //startActivity(next);

        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPasswordIden(String password,String password2) {
        //TODO: Replace this with your own logic
        return password == password2;
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
            mProgressView.animate().setDuration(shortAnimTime*5).alpha(
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public void startNextActivity(View view){
        Intent next = new Intent(this, EntrarActivity.class);
        startActivity(next);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mNome;
        private final String mUsuario;

        UserLoginTask(String email, String password, String nome, String usuario) {
            mEmail = email;
            mPassword = password;
            mNome = nome;
            mUsuario = usuario;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            hideKeyboard(getApplicationContext(),mEmailView);
            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/estudante";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast.makeText(CadastrarActivity.this, "Esses dados já existem", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR

                                    uemail =jsonArray.getJSONObject(0).getString("nm_email");
                                    unome = jsonArray.getJSONObject(0).getString("nm_estudante");
                                    uimg = jsonArray.getJSONObject(0).getString("ds_img");
                                    ulogin = jsonArray.getJSONObject(0).getString("nm_login_estudante");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //showProgress(false);
                                Toast toast = Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!",
                                        Toast.LENGTH_SHORT);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                toast.show();
                                finish();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Usuário ou email ja cadastrado",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,420);
                            toast.show();
                            //showProgress(false);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("nm_login_estudante", mUsuario);
                    parametros.put("nm_estudante", mNome);
                    parametros.put("nm_email", mEmail);
                    parametros.put("nm_senha", mPassword);
                    parametros.put("ds_img", "nada");
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(1500);

            } catch (InterruptedException e) {
                return false;
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            mNomeView.requestFocus();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Created by Rennan on 28/11/2016.
     */

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
