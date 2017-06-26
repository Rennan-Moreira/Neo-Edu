package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rennan on 22/11/2016.
 */

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private UserLoginTask altD = null;
    private UserLoginTask2 conS = null;
    private UserLoginTask3 escD = null;
    String eNome;
    String eUser;
    String eEmail;
    String passAnt;
    String passNov;
    String passNov2;
    EditText mPassAnt;
    EditText mPassNov;
    EditText mPassNov2;
    EditText mNome;
    EditText mUser;
    EditText mEmail;
    private View mProgressView;
    String uemail;
    String unome;
    String uimg;
    String ulogin;
    int uid;
    int ope =0;
    boolean corr = false;
    String[] nmTurma = new String[10];
    String[] nmProf = new String[10];
    String[] dsTurma = new String[10];
    int[] idTurma =new int[10];
    int RESULT_I =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        invalidateOptionsMenu();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //LinearLayout lil = (LinearLayout) findViewById(R.id.lilAlg);
        mPassAnt = (EditText) findViewById(R.id.edtPasswordAnt);
        mPassNov = (EditText) findViewById(R.id.edtPasswordNov);
        mPassNov2 = (EditText) findViewById(R.id.edtPasswordNov2);
        mNome = (EditText) findViewById(R.id.edtNomeAlt);
        mEmail = (EditText) findViewById(R.id.edtEmailAlt);
        mUser = (EditText) findViewById(R.id.edtUsuarioAlt);

        this.setTitle("Início");
        findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
        findViewById(R.id.nsvApli).setVisibility(View.GONE);
        findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        findViewById(R.id.nsvUsuAlt).setVisibility(View.GONE);
        findViewById(R.id.nsvUsuSen).setVisibility(View.GONE);

        mProgressView = findViewById(R.id.login_progress);

        Bundle bun = getIntent().getExtras();
        uid = bun.getInt("id");
        uemail = bun.getString("email");
        unome = bun.getString("nome");
        ulogin = bun.getString("login");
        setarVar();
        Buscar();
    }

    public void setarVarM(){
        TextView txt;
        txt = (TextView) findViewById(R.id.txtEmailNav);
        txt.setText(uemail);
        txt = (TextView) findViewById(R.id.txtNomeNav);
        txt.setText(unome);
    }

    public void setarVar(){
        TextView txt;
        EditText edt;
        edt = (EditText) findViewById(R.id.edtNome);
        edt.setText(unome);
        edt = (EditText) findViewById(R.id.edtEmail);
        edt.setText(uemail);
        edt = (EditText) findViewById(R.id.edtUsuario);
        edt.setText(ulogin);

        mNome.setText(unome);
        mEmail.setText(uemail);
        mUser.setText(ulogin);

        txt = (TextView) findViewById(R.id.txtUser);
        txt.setText("Olá usuário "+ ulogin);
        txt = (TextView) findViewById(R.id.txtEmail);
        txt.setText(uemail);
        txt = (TextView) findViewById(R.id.txtNome);
        txt.setText(unome);
    }

    @Override
    public void onBackPressed() {

        if(findViewById(R.id.nsvUsuAlt).getVisibility() == View.VISIBLE || findViewById(R.id.nsvUsuSen).getVisibility() == View.VISIBLE) {
            this.setTitle("Configurações do Usuário");
            findViewById(R.id.nsvUsuAlt).setVisibility(View.GONE);
            findViewById(R.id.nsvUsuSen).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.VISIBLE);
        }else if(findViewById(R.id.nsvIni).getVisibility() == View.VISIBLE){
            AlertDialog alert;
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Deseja realmente sair da conta?");
            b.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                }
            });

            b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alert = b.create();
            alert.show();
        }else{
            this.setTitle("Início");
            findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        }




//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setarVarM();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatemen
        return super.onOptionsItemSelected(item);
    }

    public void novoD(int i){
        switch (i){
            case 0:
                findViewById(R.id.lil1).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt1)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD1)).setText(dsTurma[i]);
                break;
            case 1:
                findViewById(R.id.lil2).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt2)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD2)).setText(dsTurma[i]);
                break;
            case 2:
                findViewById(R.id.lil3).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt3)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD3)).setText(dsTurma[i]);
                break;
            case 3:
                findViewById(R.id.lil4).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt4)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD4)).setText(dsTurma[i]);
                break;
            case 4:
                findViewById(R.id.lil5).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt5)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD5)).setText(dsTurma[i]);
                break;
            case 5:
                findViewById(R.id.lil6).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt6)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD6)).setText(dsTurma[i]);
                break;
            case 6:
                findViewById(R.id.lil7).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt7)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD7)).setText(dsTurma[i]);
                break;
            case 7:
                findViewById(R.id.lil8).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt8)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD8)).setText(dsTurma[i]);
                break;
            case 8:
                findViewById(R.id.lil9).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt9)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD9)).setText(dsTurma[i]);
                break;
            case 9:
                findViewById(R.id.lil10).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt10)).setText(nmTurma[i]);
                ((TextView) findViewById(R.id.txtD10)).setText(dsTurma[i]);
                break;
        }

    }

    public void Buscar(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/turmaestudante/"+uid;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        try {
                            JSONArray js = new JSONArray(response);
                            for(int i= 0;i<js.length();i++){
                                nmTurma[i]= js.getJSONObject(i).getString("nm_disciplina");
                                nmProf[i]= js.getJSONObject(i).getString("nm_professor");
                                idTurma[i]= js.getJSONObject(i).getInt("turma_id");
                                dsTurma[i]= js.getJSONObject(i).getString("ds_disciplina");
                                novoD(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda

                        //Toast.makeText(HomeActivity.this, "ERR", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // map = Chave valor
                Map<String, String> parametros = new HashMap<>();

                return parametros;

            }
        };
        queue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Buscar();
    }
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        Buscar();
        super.onResume();
    }

    public void Acessar(final int i){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/turmaestudante/turma/"+idTurma[i]+"/estudante/"+uid;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray js = new JSONArray(response);
                            startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("prof",nmProf[i]).putExtra("ds",dsTurma[i]).putExtra("tid",idTurma[i]).putExtra("uid",uid).putExtra("name",nmTurma[i]).putExtra("did",js.getJSONObject(0).getInt("disciplinaId")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("prof",nmProf[i]).putExtra("ds",dsTurma[i]).putExtra("tid",idTurma[i]).putExtra("uid",uid).putExtra("name",nmTurma[i]).putExtra("did",0));

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // map = Chave valor
                Map<String, String> parametros = new HashMap<>();

                return parametros;

            }
        };
        queue.add(request);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_ini) {
            this.setTitle("Início");
            findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);

        } else if (id == R.id.nav_usua) {
            this.setTitle("Configurações do Usuário");
            findViewById(R.id.nsvIni).setVisibility(View.GONE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_apli) {
            this.setTitle("Configurações do Aplicativo");
            findViewById(R.id.nsvIni).setVisibility(View.GONE);
            findViewById(R.id.nsvApli).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        } else if (id == R.id.nav_turma) {
            startActivity(new Intent(this, BuscaActivity.class).putExtra("id_es",uid));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        hideKeyboard(getApplicationContext(), mNome);
        int id = view.getId();

        if (id == R.id.btn1) {
            Acessar(0);
        } else if (id ==R.id.btn2){
            Acessar(1);
        } else if (id ==R.id.btn3){
            Acessar(2);
        } else if (id ==R.id.btn4){
            Acessar(3);
        } else if (id ==R.id.btn5){
            Acessar(4);
        } else if (id ==R.id.btn6){
            Acessar(5);
        } else if (id ==R.id.btn7){
            Acessar(6);
        } else if (id ==R.id.btn8){
            Acessar(7);
        } else if (id ==R.id.btn9){
            Acessar(8);
        } else if (id ==R.id.btn10){
            Acessar(9);
        } else if (id == R.id.btnEditarPerfil){
            findViewById(R.id.nsvUsuAlt).setVisibility(View.VISIBLE);
            setTitle("Alterar Dados do Usuário");
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        } else if (id == R.id.btnEditarSenha){
            findViewById(R.id.nsvUsuSen).setVisibility(View.VISIBLE);
            setTitle("Alterar Senha do Usuário");
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        } else  if (id == R.id.btnAlterarDados){
            verificaDados();
        } else  if (id == R.id.btnAlterarSenha){
            verificaSenha();
        } else  if (id == R.id.btnExcluirConta){
            excluiConta();
        } else if (id == R.id.btnVoltarUsu ||id == R.id.btnVoltarApl){
            this.setTitle("Início");
            findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        } else if (id == R.id.btnVoltarAlt || id == R.id.btnVoltarSen) {
            this.setTitle("Configurações do Usuário");
            findViewById(R.id.nsvUsu).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvUsuAlt).setVisibility(View.GONE);
            findViewById(R.id.nsvUsuSen).setVisibility(View.GONE);
        } else if (id == R.id.pic){
                Buscar();
        } else {
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();

        }
    }

    public void excluiConta(){
        AlertDialog alert;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Deseja realmente excluir esta conta?");
        b.setMessage("Os dados não poderão ser restaurados posteriormente!");
        b.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (escD != null) {
                    return;
                }
                escD = new UserLoginTask3();
                escD.execute((Void) null);
            }
        });

        b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert = b.create();
        alert.show();
    }

    private boolean isTextValid(String text) {
        //TODO: Replace this with your own logic
        String caractere= "qwertyuiopasdfghjklçzxcvbnméáóíã"+
                "QWERTYUIOPASDFGHJKLÇZXCVBNMÁÓÍÉÃ-_@. 0123456789";
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

    public void verificaSenha(){
        passAnt = mPassAnt.getText().toString();
        passNov = mPassNov.getText().toString();
        passNov2 = mPassNov2.getText().toString();

        View focusView = null;
        corr = false;

        if (!passNov2.equals(passNov)){
            mPassNov.setError("As novas senhas não coincidem!");
            focusView = mPassNov;
        }

        if (passAnt.equals(passNov)){
            mPassNov.setError("A nova senha não pode ser igual a anterior!");
            focusView = mPassNov;
        }
        if (TextUtils.isEmpty(passNov2) ){
            mPassNov2.setError(getString(R.string.error_field_required));
            focusView = mPassNov2;
        } else if(!isTextValid(passNov2)){
            mPassNov2.setError(getString(R.string.error_invalid_text));
            focusView = mPassNov2;
        } else if(isTextSmall(passNov2)){
            mPassNov2.setError(getString(R.string.error_small_text));
            focusView = mPassNov2;
        }

        if (TextUtils.isEmpty(passNov) ){
            mPassNov.setError(getString(R.string.error_field_required));
            focusView = mPassNov;
        } else if(!isTextValid(passNov)){
            mPassNov.setError(getString(R.string.error_invalid_text));
            focusView = mPassNov;
        } else if(isTextSmall(passNov)){
            mPassNov.setError(getString(R.string.error_small_text));
            focusView = mPassNov;
        }

        if (TextUtils.isEmpty(passAnt) ){
            mPassAnt.setError(getString(R.string.error_field_required));
            focusView = mPassAnt;
        } else if(!isTextValid(passAnt)){
            mPassAnt.setError(getString(R.string.error_invalid_text));
            focusView = mPassAnt;
        } else if(isTextSmall(passAnt)){
            mPassAnt.setError(getString(R.string.error_small_text));
            focusView = mPassAnt;
        }


        if (focusView != null){
            focusView.requestFocus();
        }else {
            showProgress(true);
            findViewById(R.id.nsvUsuSen).setVisibility(View.GONE);
            if (conS != null) {
                return;
            }
            conS = new UserLoginTask2(mUser.getText().toString(),passAnt);
            conS.execute((Void) null);

            //Toast.makeText(getApplicationContext(), "M1", Toast.LENGTH_SHORT).show();
        }
    }

    public void verificaDados(){
        eNome = mNome.getText().toString();
        eUser = mUser.getText().toString();
        eEmail = mEmail.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(eNome) ){
            mNome.setError(getString(R.string.error_field_required));
            focusView = mNome;
        } else if(!isTextValid(eNome)){
            mNome.setError(getString(R.string.error_invalid_text));
            focusView = mNome;
        } else if(isTextSmall(eNome)){
            mNome.setError(getString(R.string.error_small_text));
            focusView = mNome;
        }

        if (TextUtils.isEmpty(eEmail) ){
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
        } else if(!isTextValid(eEmail)){
            mEmail.setError(getString(R.string.error_invalid_text));
            focusView = mEmail;
        } else if(isTextSmall(eEmail)){
            mEmail.setError(getString(R.string.error_small_text));
            focusView = mEmail;
        }

        if (TextUtils.isEmpty(eUser) ){
            mUser.setError(getString(R.string.error_field_required));
            focusView = mUser;
        } else if(!isTextValid(eUser)){
            mUser.setError(getString(R.string.error_invalid_text));
            focusView = mUser;
        } else if(isTextSmall(eUser)){
            mUser.setError(getString(R.string.error_small_text));
            focusView = mUser;
        }

        if (focusView != null){
            focusView.requestFocus();
        }else {
            showProgress(true);
            findViewById(R.id.nsvUsuAlt).setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            this.setTitle("Configurações do Usuário");
            ope = 0;
            if (altD != null) {
                return;
            }
            altD = new UserLoginTask();
            altD.execute((Void) null);
        }
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/estudante/"+uid;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.PUT,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválidos",
                                        Toast.LENGTH_LONG);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                toast.show();
                            } else {
                                try {
                                    JSONObject jsonArray = new JSONObject(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR

                                    uemail = jsonArray.getString("nm_email");
                                    unome = jsonArray.getString("nm_estudante");
                                    uimg = jsonArray.getString("ds_img");
                                    ulogin = jsonArray.getString("nm_login_estudante");
                                    uid = jsonArray.getInt("id");

                                    setarVar();
                                    setarVarM();
                                    findViewById(R.id.nsvUsu).setVisibility(View.VISIBLE);
                                    showProgress(false);

                                    setTitle("Configurações do Usuário");
                                    Toast.makeText(getApplicationContext(), "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Falha na conexão",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();
                            showProgress(false);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    if(ope == 0) {
                        parametros.put("nm_login_estudante", eUser);
                        parametros.put("nm_email", eEmail);
                        parametros.put("nm_estudante", eNome);
                    }else if(ope == 1){
                        parametros.put("nm_senha", passNov);
                    }else{

                    }
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            altD = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            altD = null;
            showProgress(false);
        }
    }

    public class UserLoginTask2 extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask2(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/estudante/login";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválidos",
                                        Toast.LENGTH_LONG);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                toast.show();
                            } else {
                                ope = 1;
                                if (altD != null) {
                                    return;
                                }
                                altD = new UserLoginTask();
                                altD.execute((Void) null);
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "A senha antiga nao está correta!",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();

                            showProgress(false);

                            findViewById(R.id.nsvUsuSen).setVisibility(View.VISIBLE);
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
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                return false;
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            conS = null;
            showProgress(false);

        }

        @Override
        protected void onCancelled() {
            conS = null;
            showProgress(false);
        }
    }

    public class UserLoginTask3 extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/estudante/"+uid;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.DELETE,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválidos",
                                        Toast.LENGTH_LONG);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                toast.show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Conta excluida", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Falha na conexão",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();
                            showProgress(false);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            altD = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            altD = null;
            showProgress(false);
        }
    }

}

