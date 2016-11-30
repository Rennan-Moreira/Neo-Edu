package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.rennan.neoedu.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
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
    private EditText mPasswordView, mEmailView, mUserView;
    private View mProgressView, mLoginFormView;
    private RadioButton rdbEst, rdbPro;
    private ImageView flat;
    private Boolean rdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        // Configurar o login form.
        mEmailView = (EditText) findViewById(R.id.edtEmail);
        mUserView = (EditText) findViewById(R.id.edtUser);
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

        Button mEmailSignInButton = (Button) findViewById(R.id.btnCadastrar);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.registrar_form);
        mProgressView = findViewById(R.id.login_progress);
        flat = (ImageView) findViewById(R.id.imgFlat);
        rdbEst = (RadioButton) findViewById(R.id.rdbEstudante);
        rdbPro = (RadioButton) findViewById(R.id.rdbProfessor);
        rdb = true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rdbEstudante) {
            rdb = true;
            rdbEst.setChecked(true);
            rdbPro.setChecked(false);
        } else {
            rdb = false;
            rdbEst.setChecked(false);
            rdbPro.setChecked(true);
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

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Verifique se há uma senha válida, se o usuário digitar uma.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_text));
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

            Intent next = new Intent(this, HomeActivity.class);
            startActivity(next);

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
        Intent next = new Intent(this, HomeActivity.class);
        startActivity(next);

    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mUser;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mUser = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(800);
            } catch (InterruptedException e) {
                return false;
            }

            Controle c = new Controle();

            for(int i=0;i<c.getLengthAlunos();i++){
                if(c.getEmailAluno(i).equals(mEmail) && c.getUsuarioAluno(i).equals(mUser)){
                    return true;
                }
            }

            for(int i=0;i<c.getLengthProfessores();i++){
                if(c.getEmailProfessor(i).equals(mEmail) && c.getUsuarioProfessor(i).equals(mUser)){
                    return true;
                }
            }

            if(rdb) {

                c.addAluno(new Aluno("RennanMP96","minhasenha","Rennan",new Date(),"Email",0));
            }else{

                c.addProfessor(new Professor("RennanPROF","minhasenha","Rennan",new Date(),"Email",0));
            }





            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
