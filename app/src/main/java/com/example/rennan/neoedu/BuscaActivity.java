package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuscaActivity extends AppCompatActivity implements View.OnClickListener{

    private PegarTudo pt = null;
    private ListView listaP;
    private View mProgressView;
//
//    ArrayAdapter<String> adapter;
//    List<Turma> t;
//    ArrayList<String> itemList;
//
//    ArrayAdapter<String> ada;
//    ArrayList<String> itemTurma;

    ListView list;

    String[] disc;
    String[] nome;
    int[] id;
    int id_es;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Pesquisar Turma");

        id_es= getIntent().getExtras().getInt("id_es");
        mProgressView = findViewById(R.id.pesquisa_progress);
        listaP = (ListView) findViewById(R.id.lvListaP);

//        Turma items[] = new Turma[3];
//        items[0].disciplina = "Banco de dados";
//        items[0].nome = "Chiara";
//        items[1].disciplina = "Orientação a objeto";
//        items[1].nome = "Vizine";
//        items[2].disciplina = "Engenharia de Software";
//        items[2].nome = "Mauricio";
//
//        itemTurma = new ArrayList<String>();
//        ada=new ArrayAdapter<String>(this, R.layout.list_item,itemTurma,);
//        listaP.setAdapter(ada);

//        String[] items={"Apple","Banana","Clementine"};
//        itemList = new ArrayList<String>(Arrays.asList(items));
//        adapter=new ArrayAdapter<String>(this, R.layout.list_item,R.id.txtTi,itemList);
//        listaP.setAdapter(adapter);

        hideKeyboard(getApplicationContext(),findViewById(R.id.edtBuscaP));
        showProgress(true);
        Listar();
        pt = new PegarTudo();
        pt.execute((Void) null);
    }
    public void onBackPressed(){
        finish();
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

    }

    public class PegarTudo extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(100);
                return true;
            } catch (InterruptedException e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pt = null;
        }

        @Override
        protected void onCancelled() {
            pt = null;
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

    public void Listar(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/turma";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        //Toast.makeText(BuscaActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            disc = new String[jsonArray.length()];
                            nome = new String[jsonArray.length()];
                            id = new int[jsonArray.length()];

                            for(int i = 0; i<jsonArray.length();i++){
                                id[i] = jsonArray.getJSONObject(i).getInt("id");
                                disc[i] = jsonArray.getJSONObject(i).getString("nm_disciplina");
                                nome[i] = jsonArray.getJSONObject(i).getString("nm_professor");
                            }

                            CustomList adapter = new
                                    CustomList(BuscaActivity.this, disc, nome);
                            list=(ListView)findViewById(R.id.lvListaP);
                            list.setAdapter(adapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    Toast.makeText(BuscaActivity.this, "Voce Clicou em " +disc[+ position], Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(BuscaActivity.this, "Erro na conexão", Toast.LENGTH_SHORT).show();

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


        showProgress(false);
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
