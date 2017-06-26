package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class RanqActivity extends AppCompatActivity implements View.OnClickListener{

    //private PegarTudo pt = null;
    private ListView listaP;
    private View mProgressView;
//
//    ArrayAdapter<String> adapter;
//    List<Turma> t;
//    ArrayList<String> itemList;
//
//    ArrayAdapter<String> ada;
//    ArrayList<String> itemTurma;

    ListView listG,listaM1,listaM2,listaM3;
//
//    String[][] nome = new String[4][20];
//    int[][] media = new int[4][20];
//    int[][] id = new int[4][20];
//
//    int[] ind = new int[20];
    int[] indG;
    String[] nomeG;
    int[] mediaG;
    int[] idG;

    int[] ind1;
    String[] nome1;
    int[] media1;
    int[] id1;

    int[] ind2;
    String[] nome2;
    int[] media2;
    int[] id2;

    int[] ind3;
    String[] nome3;
    int[] media3;
    int[] id3;

    int m;
    int tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Ranking");
        TextView t = (TextView) findViewById(R.id.txtTitR);
        t.setText(getIntent().getStringExtra("name"));
        tid = getIntent().getExtras().getInt("tid");

        mProgressView = findViewById(R.id.pesquisa_progress);
        listaP = (ListView) findViewById(R.id.lvGeral);
        listaM1 = (ListView) findViewById(R.id.lvM1);
        listaM2 = (ListView) findViewById(R.id.lvM2);
        listaM3 = (ListView) findViewById(R.id.lvM3);


        showProgress(true);
        ListarGeral();
        ListarMol1();
        ListarMol2();
        ListarMol3();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnG){
            findViewById(R.id.btnG).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
            findViewById(R.id.btnM1).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM2).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM3).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.lvGeral).setVisibility(View.VISIBLE);
            findViewById(R.id.lvM1).setVisibility(View.GONE);
            findViewById(R.id.lvM2).setVisibility(View.GONE);
            findViewById(R.id.lvM3).setVisibility(View.GONE);
        } else if (view.getId() == R.id.btnM1){
            findViewById(R.id.btnG).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM1).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
            findViewById(R.id.btnM2).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM3).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.lvGeral).setVisibility(View.GONE);
            findViewById(R.id.lvM1).setVisibility(View.VISIBLE);
            findViewById(R.id.lvM2).setVisibility(View.GONE);
            findViewById(R.id.lvM3).setVisibility(View.GONE);
        } else if (view.getId() == R.id.btnM2){
            findViewById(R.id.btnG).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM1).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM2).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
            findViewById(R.id.btnM3).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.lvGeral).setVisibility(View.GONE);
            findViewById(R.id.lvM1).setVisibility(View.GONE);
            findViewById(R.id.lvM2).setVisibility(View.VISIBLE);
            findViewById(R.id.lvM3).setVisibility(View.GONE);
        } else if (view.getId() == R.id.btnM3){
            findViewById(R.id.btnG).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM1).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM2).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
            findViewById(R.id.btnM3).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
            findViewById(R.id.lvGeral).setVisibility(View.GONE);
            findViewById(R.id.lvM1).setVisibility(View.GONE);
            findViewById(R.id.lvM2).setVisibility(View.GONE);
            findViewById(R.id.lvM3).setVisibility(View.VISIBLE);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listaP.setVisibility(show ? View.GONE : View.VISIBLE);
            listaP.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listaP.setVisibility(show ? View.GONE : View.VISIBLE);
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
            listaP.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void ListarMol1(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/desempenho/turma/"+tid+"/modulo/1";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            nome1 =new String[jsonArray.length()];
                            id1 = new int[jsonArray.length()];
                            media1  = new int[jsonArray.length()];
                            ind1  = new int[jsonArray.length()];
                            for(int i = 0; i<20;i++){
                                if(i < jsonArray.length()) {
                                    ind1[i] = i + 1;
                                    id1[i] = jsonArray.getJSONObject(i).getInt("id");
                                    media1[i] = (int) Double.parseDouble(jsonArray.getJSONObject(i).getString("mediaModulo"));
                                    nome1[i] = jsonArray.getJSONObject(i).getString("nm_estudante");
                                }
                            }
                            CustomList2 adapter2 = new
                                    CustomList2(RanqActivity.this, ind1,nome1,media1);
                            listaM1=(ListView)findViewById(R.id.lvM1);
                            listaM1.setAdapter(adapter2);
                            listaM1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long ids) {
                                    //Acessar(+ position);
                                    Toast.makeText(RanqActivity.this, "Voce Clicou em " +id1[+ position], Toast.LENGTH_SHORT).show();

                                }
                            });
                            showProgress(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(RanqActivity.this, "Erro na conexão", Toast.LENGTH_SHORT).show();

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

    }
    public void ListarMol2(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/desempenho/turma/"+tid+"/modulo/2";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            nome2 =new String[jsonArray.length()];
                            id2 = new int[jsonArray.length()];
                            media2  = new int[jsonArray.length()];
                            ind2  = new int[jsonArray.length()];
                            for(int i = 0; i<20;i++){
                                if(i < jsonArray.length()) {
                                    ind2[i] = i + 1;
                                    id2[i] = jsonArray.getJSONObject(i).getInt("id");
                                    media2[i] = (int) Double.parseDouble(jsonArray.getJSONObject(i).getString("mediaModulo"));
                                    nome2[i] = jsonArray.getJSONObject(i).getString("nm_estudante");
                                }
                            }
                            CustomList2 adapter3 = new
                                    CustomList2(RanqActivity.this, ind2,nome2,media2);
                            listaM2=(ListView)findViewById(R.id.lvM2);
                            listaM2.setAdapter(adapter3);
                            listaM2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long ids) {
                                    //Acessar(+ position);
                                    Toast.makeText(RanqActivity.this, "Voce Clicou em " +id2[+ position], Toast.LENGTH_SHORT).show();

                                }
                            });
                            showProgress(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(RanqActivity.this, "Erro na conexão", Toast.LENGTH_SHORT).show();

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

    }
    public void ListarMol3(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/desempenho/turma/"+tid+"/modulo/3";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            nome3 =new String[jsonArray.length()];
                            id3 = new int[jsonArray.length()];
                            media3  = new int[jsonArray.length()];
                            ind3  = new int[jsonArray.length()];
                            for(int i = 0; i<20;i++){
                                if(i < jsonArray.length()) {
                                    ind3[i] = i + 1;
                                    id3[i] = jsonArray.getJSONObject(i).getInt("id");
                                    media3[i] = (int) Double.parseDouble(jsonArray.getJSONObject(i).getString("mediaModulo"));
                                    nome3[i] = jsonArray.getJSONObject(i).getString("nm_estudante");
                                }
                            }
                            CustomList2 adapter4 = new
                                    CustomList2(RanqActivity.this, ind3,nome3,media3);
                            listaM3=(ListView)findViewById(R.id.lvM3);
                            listaM3.setAdapter(adapter4);
                            listaM3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long ids) {
                                    //Acessar(+ position);
                                    Toast.makeText(RanqActivity.this, "Voce Clicou em " +id3[+ position], Toast.LENGTH_SHORT).show();

                                }
                            });

                            showProgress(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(RanqActivity.this, "Erro na conexão", Toast.LENGTH_SHORT).show();

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

    }
    public void ListarGeral(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/desempenho/app/turma/"+tid;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(RanqActivity.this, " " +tid, Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = new JSONArray(response);
                            nomeG =new String[jsonArray.length()];
                            idG = new int[jsonArray.length()];
                            mediaG  = new int[jsonArray.length()];
                            indG  = new int[jsonArray.length()];

                            for(int i = 0; i<jsonArray.length();i++){
                                    indG[i] = i+1;
                                    idG[i] = jsonArray.getJSONObject(i).getInt("id");
                                    mediaG[i] = (int) Double.parseDouble(jsonArray.getJSONObject(i).getString("mediaModulo"));;
                                    nomeG[i] = jsonArray.getJSONObject(i).getString("nm_estudante");
                            }

                            CustomList2 adapter = new
                                    CustomList2(RanqActivity.this, indG,nomeG,mediaG);
                            listG=(ListView)findViewById(R.id.lvGeral);
                            listG.setAdapter(adapter);
                            listG.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long ids) {
                                    //Acessar(+ position);
                                    Toast.makeText(RanqActivity.this, "Voce Clicou em " +idG[+ position], Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showProgress(false);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(RanqActivity.this, "Erro na conexão", Toast.LENGTH_SHORT).show();

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

    }

    public void onBackPressed() {
        if (getParent() == null) {
            setResult(HomeActivity.RESULT_OK);
        } else {
            getParent().setResult(HomeActivity.RESULT_OK);
        }
        finish();
    }

//    public void Acessar(final int pos){
//        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/turmaestudante/turma/"+id[pos]+"/estudante/"+id_es;
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context
//
//        StringRequest request = new StringRequest(Request.Method.GET,url,
//                new Response.Listener<String>(){
//                    @Override
//                    public void onResponse(String response){ //Quando esta OK
//                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
//
//                        JSONArray js = null;
//                        try {
//                            js = new JSONArray(response);
//                            startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("prof",nome[pos]).putExtra("ds",ds[pos]).putExtra("tid",id[pos]).putExtra("uid",id_es).putExtra("name",disc[pos]).putExtra("did",js.getJSONObject(0).getString("disciplinaId")));
//                            Toast.makeText(RanqActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error){ // Deu Merda
//                        startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("prof",nome[pos]).putExtra("ds",ds[pos]).putExtra("did","0").putExtra("tid",id[pos]).putExtra("uid",id_es).putExtra("name",disc[pos]));
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                // map = Chave valor
//                Map<String, String> parametros = new HashMap<>();
//
//                return parametros;
//
//            }
//        };
//        queue.add(request);
//
//    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
