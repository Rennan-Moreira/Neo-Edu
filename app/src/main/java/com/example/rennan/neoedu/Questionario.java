package com.example.rennan.neoedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Questionario extends AppCompatActivity implements View.OnClickListener {
    Button LA,LB,LC,LD;
    TextView txtPergunta,txtAcertos,txtQuestao,txtTitulo,txtEnunciado,txtCorreto,txtResolucao;
    int ContaPergunta=-1,Acertos;
    int contOk =0;
    int sel[] = new int[10];

    boolean continua = true;

    LinearLayout res;

    private View mProgressView;
    private View lilT;
    private View lilB;

    int uid;
    int did;
    int mol;
    int tid;

    private PegarPergunta2 pp = null;
    private PegarAlternativa2 pa = null;

    private EnviarResposta er = null;

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

        public int getId() {
            return id;
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

        public void setId(int id){
            this.id = id;
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

        Bundle bun = getIntent().getExtras();
        uid = bun.getInt("uid");
        did = bun.getInt("did");
        mol = bun.getInt("mol");
        tid = bun.getInt("tid");

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
        btnEnabled();
        this.setTitle(getIntent().getStringExtra("name"));
        //pp = new PegarPergunta2();
        //pp.execute((Void) null);
        PegarPergunta();

    }

    public void btnEnabled(){
        if(LA.isEnabled()){
            LA.setEnabled(false);
            LB.setEnabled(false);
            LC.setEnabled(false);
            LD.setEnabled(false);
        }else{
            LA.setEnabled(true);
            LB.setEnabled(true);
            LC.setEnabled(true);
            LD.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        btnEnabled();
        int tempo = 2500;
        int vl =0;
        //TrocaPergunta();
        if (continua && ContaPergunta < 10) {
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

            if(Resultado.equals("Correto")){
                sel[ContaPergunta] = R.color.right;
            }else{
                sel[ContaPergunta] = R.color.wrong;
            }

            continua = false;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {TrocaPergunta();continua = true;}
            }, tempo);
        }else{
            switch (view.getId()) {
                case R.id.btnQ1:
                    vl=0;
                    break;
                case R.id.btnQ2:
                    vl=1;
                    break;
                case R.id.btnQ3:
                    vl=2;
                    break;
                case R.id.btnQ4:
                    vl=3;
                    break;
                case R.id.btnQ5:
                    vl=4;
                    break;
                case R.id.btnQ6:
                    vl=5;
                    break;
                case R.id.btnQ7:
                    vl=6;
                    break;
                case R.id.btnQ8:
                    vl=7;
                    break;
                case R.id.btnQ9:
                    vl=8;
                    break;
                case R.id.btnQ10:
                    vl=9;
                    break;
            }

            txtTitulo.setText("Resolução - "+(vl+1)+"ª Questão");
            txtTitulo.setTextColor(ContextCompat.getColorStateList(this, sel[vl]));
            txtEnunciado.setText("Enunciado: '"+perguntas.get(vl).getEnunciado()+"'");
            txtResolucao.setText(perguntas.get(vl).getResolucao());
            switch (perguntas.get(vl).getCerto()){
                case 1:
                    txtCorreto.setText(perguntas.get(vl).getLA());
                    break;
                case 2:
                    txtCorreto.setText(perguntas.get(vl).getLB());
                    break;
                case 3:
                    txtCorreto.setText(perguntas.get(vl).getLC());
                    break;
                case 4:
                    txtCorreto.setText(perguntas.get(vl).getLD());
                    break;
            }

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
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {Fechar();}
//            }, 4000);
        }
        btnEnabled();
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
        if (ContaPergunta < 10) {
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
        }else {
            Fechar();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return false;
    }

    public void PegarPergunta(){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/questionario/disciplina/"+did+"/modulo/"+mol;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        if(response.equals("0")) {
                            Toast.makeText(Questionario.this, "Esses dados já existem - "+did+" - "+mol, Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(Questionario.this, "Esses dados já existem - "+did+" - "+mol, Toast.LENGTH_SHORT).show();
                            try {
                                //JSONArray jsonArray = new JSONArray(response);

                                JSONArray jsonArray = new JSONArray(response);

                                // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                for (int i=0;i< 10;i++) {
                                    perguntas.add(new Pergunta(jsonArray.getJSONObject(i).getInt("id"),jsonArray.getJSONObject(i).getString("ds_questao"),jsonArray.getJSONObject(i).getString("ds_resolucao")));
                                    PegarAlternativa(jsonArray.getJSONObject(i).getInt("id"),i);
                                    pa = new PegarAlternativa2(jsonArray.getJSONObject(i).getInt("id"),i);
                                    //pa.execute((Void) null);
                                }

                                //Toast.makeText(Questionario.this, "OK3", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){ // Deu Merda
                        Toast.makeText(Questionario.this, "Esses dados já existem - "+did+" - "+mol, Toast.LENGTH_SHORT).show();

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
    }

    public class PegarPergunta2 extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/questionario/disciplina/"+did+"/modulo/"+mol;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast.makeText(Questionario.this, "Esses dados já existem", Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast toast = Toast.makeText(getApplicationContext(), "Teste"+i,
//                                        Toast.LENGTH_SHORT);
//
//                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
//                                toast.show();
                                try {

                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                    for (int i=0;i< jsonArray.length();i++) {
                                        perguntas.add(new Pergunta(jsonArray.getJSONObject(i).getInt("id"),jsonArray.getJSONObject(i).getString("ds_questao"),jsonArray.getJSONObject(i).getString("ds_resolucao")));
                                        PegarAlternativa(jsonArray.getJSONObject(i).getInt("id"),i);
                                        //pa = new PegarAlternativa2(jsonArray.getJSONObject(i).getInt("id"),i);
                                        //pa.execute((Void) null);
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
                Thread.sleep(100);
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

    public void PegarAlternativa(int ID , final int Con){
        String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/alternativa/"+ID;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){ //Quando esta OK
                        if(response.equals("0")) {
                            Toast.makeText(Questionario.this, "Esses dados já existem", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "ID = "+Con,
                                    Toast.LENGTH_SHORT);
                            contOk ++;
                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            //toast.show();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                perguntas.get(Con).setLA(jsonArray.getJSONObject(0).getString("ds_alternativa"));
                                perguntas.get(Con).setLB(jsonArray.getJSONObject(1).getString("ds_alternativa"));
                                perguntas.get(Con).setLC(jsonArray.getJSONObject(2).getString("ds_alternativa"));
                                perguntas.get(Con).setLD(jsonArray.getJSONObject(3).getString("ds_alternativa"));

                                for (int i=0; i< jsonArray.length();i++){
                                    if (jsonArray.getJSONObject(i).getInt("ic_certa") == 1){
                                        perguntas.get(Con).setCerto(i+1);
                                    }
                                }

                                if (contOk == 10){
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


    }

    public class PegarAlternativa2 extends AsyncTask<Void, Void, Boolean> {

        private final int ID;
        private final int con;

        PegarAlternativa2(int idr, int conr) {
            ID = idr;
            con = conr;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(300);
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

    public class EnviarResposta extends AsyncTask<Void, Void, Boolean> {
        //{"turma_id": "3", "estudante_id": "54" ,"questao_id": "4","ic_resposta": "1"}
        private final String mT;
        private final String mE;
        private final String mQ;
        private final String mR;

        EnviarResposta(int T, int E, int Q, int R) {
            mT = ""+T;
            mE = ""+E;
            mQ = ""+Q;
            mR = ""+R;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/resposta";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            if(response.equals("0")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválidos",
                                        Toast.LENGTH_SHORT);

                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                                toast.show();
                            } else {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    // OBTENEMOS LOS DATOS QUE DEVUELVE EL SERVIDOR
                                    //showProgress(false);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "ERRO",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("turma_id", mT);
                    parametros.put("estudante_id", mE);
                    parametros.put("questao_id", mQ);
                    parametros.put("ic_resposta", mR);
                    return parametros;
                }
            };
            queue.add(request);

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            er = null;
        }

        @Override
        protected void onCancelled() {
            er = null;
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
            lilB.setVisibility(show ? View.GONE : View.VISIBLE);
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

    public int resp(int i){
        if(sel[i]==R.color.right){
            return 1;
        }else{
            return 0;
        }
    }

    public void resolucao(){

        for(int i =0;i<10;i++) {
            er = new EnviarResposta(tid, uid, perguntas.get(i).getId(), resp(i));
            er.execute((Void) null);
        }

        this.setTitle("Resolução");
        res.setVisibility(View.VISIBLE);
        findViewById(R.id.lilResolucao).setVisibility(View.VISIBLE);
        findViewById(R.id.lilTopo).setVisibility(View.GONE);
        findViewById(R.id.txtPergunta).setVisibility(View.GONE);
        findViewById(R.id.proQue).setVisibility(View.GONE);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtEnunciado = (TextView) findViewById(R.id.txtEnunciado);
        txtCorreto = (TextView) findViewById(R.id.txtCorreto);
        txtResolucao = (TextView) findViewById(R.id.txtResolucao);

        findViewById(R.id.btnQ1).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[0]));
        findViewById(R.id.btnQ2).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[1]));
        findViewById(R.id.btnQ3).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[2]));
        findViewById(R.id.btnQ4).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[3]));
        findViewById(R.id.btnQ5).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[4]));
        findViewById(R.id.btnQ6).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[5]));
        findViewById(R.id.btnQ7).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[6]));
        findViewById(R.id.btnQ8).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[7]));
        findViewById(R.id.btnQ9).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[8]));
        findViewById(R.id.btnQ10).setBackgroundTintList(ContextCompat.getColorStateList(this, sel[9]));

        txtTitulo.setText("Resolução - 1ª Questão");
        txtTitulo.setTextColor(ContextCompat.getColorStateList(this, sel[0]));
        txtEnunciado.setText("Enunciado: '"+perguntas.get(0).getEnunciado()+"'");
        txtResolucao.setText(perguntas.get(0).getResolucao());
        switch (perguntas.get(0).getCerto()){
            case 1:
                txtCorreto.setText(perguntas.get(0).getLA());
                break;
            case 2:
                txtCorreto.setText(perguntas.get(0).getLB());
                break;
            case 3:
                txtCorreto.setText(perguntas.get(0).getLC());
                break;
            case 4:
                txtCorreto.setText(perguntas.get(0).getLD());
                break;
        }
    }

}

