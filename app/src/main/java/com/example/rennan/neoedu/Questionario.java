package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Questionario extends AppCompatActivity implements View.OnClickListener {
    Button LA,LB,LC,LD;
    TextView txtPergunta,txtAcertos,txtQuestao;
    int ContaPergunta=-1,Acertos;
    boolean continua = true;
    Controle c = new Controle();
    int tipo = 0;
    int contOk =0;
    LinearLayout res;
    private View mProgressView;

    private View lilT;
    private View lilB;

    private PegarPergunta pp = null;

    private PegarAlternativa pa = null;

    public class Pergunta {
        private int id;
        private String enunciado;
        private String LA;
        private String LB;
        private String LC;
        private String LD;
        private int certo;
        private String resolucao;

        public Pergunta (int id, String enunciado, String resolucao){
            this.id = id;
            this.enunciado = enunciado;
            this.resolucao = resolucao;
        }

        public String getEnunciado() {
            return enunciado;
        }

        public String getResolucao() {
            return resolucao;
        }

        public String getLA() {
            return LA;
        }

        public String getLB() {
            return LB;
        }

        public String getLC() {
            return LC;
        }

        public String getLD() {
            return LD;
        }

        public int getCerto() {
            return certo;
        }

        public void setLA(String la){
            this.LA = la;
        }

        public void setLB(String lb){
            this.LB = lb;
        }

        public void setLC(String lc){
            this.LC = lc;
        }

        public void setLD(String ld){
            this.LD = ld;
        }

        public void setCerto(int certo){
            this.certo = certo;
        }
    }

    public ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        res = (LinearLayout) findViewById(R.id.lilResolucao);
        mProgressView = findViewById(R.id.proQue);
        lilT = findViewById(R.id.lilTopo);
        lilB = findViewById(R.id.lilBotao);

        //showProgress(true);

        LA = (Button) findViewById(R.id.btnLetraA);
        LB = (Button) findViewById(R.id.btnLetraB);
        LC = (Button) findViewById(R.id.btnLetraC);
        LD = (Button) findViewById(R.id.btnLetraD);

        LA.setOnClickListener(this);
        LB.setOnClickListener(this);
        LC.setOnClickListener(this);
        LD.setOnClickListener(this);

        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        txtAcertos = (TextView) findViewById(R.id.txtAcertos);
        txtQuestao = (TextView) findViewById(R.id.txtQuestao);

        this.setTitle(getIntent().getStringExtra("name"));
        pp = new PegarPergunta();
        pp.execute((Void) null);
    }

    @Override
    public void onClick(View view) {
        int tempo = 2000;

        //TrocaPergunta();
        if (continua) {
            int Cor = R.color.wrong;
            String Resultado = "Errado";
            //TrocaCor();
            switch (view.getId()) {
                case R.id.btnLetraA:
                    if (perguntas.get(ContaPergunta).getCerto()==1) {
                        Cor = R.color.right;
                        Resultado = "Correto";
                        Acertos++;
                        tempo=1000;
                    } else {
                        MostrarCerto();
                    }

                    LA.setText(Resultado);
                    LA.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, Cor)));
                    break;

                case R.id.btnLetraB:
                    if (perguntas.get(ContaPergunta).getCerto()==2) {
                        Cor = R.color.right;
                        Resultado = "Correto";
                        Acertos++;
                        tempo=1000;
                    } else {
                        MostrarCerto();
                    }

                    LB.setText(Resultado);
                    LB.setBackgroundTintList(ContextCompat.getColorStateList(this, Cor));
                    break;

                case R.id.btnLetraC:
                    if (perguntas.get(ContaPergunta).getCerto()==3) {
                        Cor = R.color.right;
                        Resultado = "Correto";
                        Acertos++;
                        tempo=1000;
                    } else {
                        MostrarCerto();
                    }

                    LC.setText(Resultado);
                    LC.setBackgroundTintList(ContextCompat.getColorStateList(this, Cor));
                    break;

                case R.id.btnLetraD:
                    if (perguntas.get(ContaPergunta).getCerto()==4) {
                        Cor = R.color.right;
                        Resultado = "Correto";
                        Acertos++;
                        tempo=1000;
                    } else {
                        MostrarCerto();
                    }

                    LD.setText(Resultado);
                    LD.setBackgroundTintList(ContextCompat.getColorStateList(this, Cor));
                    break;
            }

            continua = false;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {TrocaPergunta();continua = true;}
            }, tempo);
        }
    }

    public void TrocaCor(){
        LA.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
        LB.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
        LC.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));
        LD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));

    }

    public void TrocaPergunta(){

        ContaPergunta++;
        txtQuestao.setText("Questões "+ContaPergunta+"/10");
        txtAcertos.setText("Acertos " + Acertos + "/10");

        TrocaCor();
        try{
            txtPergunta.setText(perguntas.get(ContaPergunta).getEnunciado());
            LA.setText(perguntas.get(ContaPergunta).getLA());
            LB.setText(perguntas.get(ContaPergunta).getLB());
            LC.setText(perguntas.get(ContaPergunta).getLC());
            LD.setText(perguntas.get(ContaPergunta).getLD());
        }catch (Exception e){
            findViewById(R.id.lilBotao).setVisibility(View.GONE);
            findViewById(R.id.lilTopo).setVisibility(View.GONE);
            resolucao();
//            txtPergunta.setTextColor(getColor(R.color.right));
//            txtPergunta.setTextSize(60);
//            if(tipo >= 10) {
//                txtPergunta.setText("Ainda não está disponível!");
//            }else if(Acertos == 10) {
//                txtPergunta.setText("Você acertou tudo, parabéns!");
//            }else if(Acertos >= 6){
//                txtPergunta.setText("Voce acertou "+ Acertos +" questões, continue assim!");
//            }else if(Acertos == 0){
//                txtPergunta.setText("Voce errou tudo, estude mais!");
//            }else{
//                txtPergunta.setText("Voce só acertou "+ Acertos +" questões, tente melhorar!");
//                txtPergunta.setTextColor(getColor(R.color.wrong));
//            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {Fechar();}
            }, 4000);
        }
    }

    public void Fechar(){
        this.finish();
    }

    public void MostrarCerto() {
        switch (perguntas.get(ContaPergunta).getCerto()){
            case 1:
                LA.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.right));
                break;
            case 2:
                LB.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.right));
                break;
            case 3:
                LC.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.right));
                break;
            case 4:
                LD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.right));
                break;
        }
    }

    public void onBackPressed(){
        AlertDialog alert;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Deseja realmente sair?");
        b.setMessage("Você perderá seu progresso neste questionário");
        b.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Continuando ...",
                        Toast.LENGTH_SHORT).show();
            }
        });

        alert = b.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return false;
    }

    public class PegarPergunta extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/questionario/disciplina/4/modulo/1";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast.makeText(Questionario.this, "Esses dados já existem", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Teste1!",
                                        Toast.LENGTH_SHORT);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                //toast.show();
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                    for (int i=0;i< jsonArray.length();i++) {
                                        perguntas.add(new Pergunta(jsonArray.getJSONObject(i).getInt("id"),jsonArray.getJSONObject(i).getString("ds_questao"),jsonArray.getJSONObject(i).getString("ds_resolucao")));
                                        pa = new PegarAlternativa(jsonArray.getJSONObject(i).getInt("id"),i);
                                        pa.execute((Void) null);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Erro na conexão!",
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
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(1500);
                return true;
            } catch (InterruptedException e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pp = null;
        }

        @Override
        protected void onCancelled() {
            pp = null;
        }
    }

    public class PegarAlternativa extends AsyncTask<Void, Void, Boolean> {

        private final int ID;
        private final int con;

        PegarAlternativa(int idr, int conr) {
            ID = idr;
            con = conr;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/alternativa/"+ID;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast.makeText(Questionario.this, "Esses dados já existem", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "ID = "+con,
                                        Toast.LENGTH_SHORT);
                                contOk ++;
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                //toast.show();
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                    perguntas.get(con).setLA(jsonArray.getJSONObject(0).getString("ds_alternativa"));
                                    perguntas.get(con).setLB(jsonArray.getJSONObject(1).getString("ds_alternativa"));
                                    perguntas.get(con).setLC(jsonArray.getJSONObject(2).getString("ds_alternativa"));
                                    perguntas.get(con).setLD(jsonArray.getJSONObject(3).getString("ds_alternativa"));

                                    for (int i=0; i< jsonArray.length();i++){
                                        if (jsonArray.getJSONObject(i).getInt("ic_certa") == 1){
                                            perguntas.get(con).setCerto(i+1);
                                        }
                                    }
                                    if (contOk == 1){

                                        TrocaPergunta();
                                        showProgress(false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Erro na conexão",
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
                    return parametros;
                }
            };
            queue.add(request);

            try {
                Thread.sleep(1500);
                return true;
            } catch (InterruptedException e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pa = null;
        }

        @Override
        protected void onCancelled() {
            pa = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            lilT.setVisibility(show ? View.GONE : View.VISIBLE);
            lilT.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lilT.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            lilB.setVisibility(show ? View.GONE : View.VISIBLE);
            lilB.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lilB.setVisibility(show ? View.GONE : View.VISIBLE);
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
            lilT.setVisibility(show ? View.GONE : View.VISIBLE);
            lilB.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void resolucao(){

        this.setTitle("Resolução");
        res.setVisibility(View.VISIBLE);
        findViewById(R.id.lilResolucao).setVisibility(View.VISIBLE);
        findViewById(R.id.lilTopo).setVisibility(View.GONE);
        findViewById(R.id.txtPergunta).setVisibility(View.GONE);
        findViewById(R.id.proQue).setVisibility(View.GONE);
    }
}

