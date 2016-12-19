package com.example.rennan.neoedu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
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
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Questionario extends AppCompatActivity implements View.OnClickListener {
        Button LA,LB,LC,LD,LE,LR;
        TextView txtPergunta,txtAcertos,txtQuestao;
        int ContaPergunta=-1,Acertos;
        boolean continua = true;
        Controle c = new Controle();
        int tipo = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_questionario);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            LA = (Button) findViewById(R.id.btnLetraA);
            LB = (Button) findViewById(R.id.btnLetraB);
            LC = (Button) findViewById(R.id.btnLetraC);
            LD = (Button) findViewById(R.id.btnLetraD);
            LE = (Button) findViewById(R.id.btnLetraE);

            LA.setOnClickListener(this);
            LB.setOnClickListener(this);
            LC.setOnClickListener(this);
            LD.setOnClickListener(this);
            LE.setOnClickListener(this);

            txtPergunta = (TextView) findViewById(R.id.txtPergunta);
            txtAcertos = (TextView) findViewById(R.id.txtAcertos);
            txtQuestao = (TextView) findViewById(R.id.txtQuestao);
            TrocaPergunta();
            this.setTitle(getIntent().getStringExtra("name"));


        }

        @Override
        public void onClick(View view) {
            int tempo = 2000;
            if (continua) {
                int Cor = R.color.wrong;
                String Resultado = "Errado";
                //TrocaCor();
                switch (view.getId()) {
                    case R.id.btnLetraA:
                        if (c.getCerto(ContaPergunta)==1) {
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
                        if (c.getCerto(ContaPergunta)==2) {
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
                        if (c.getCerto(ContaPergunta)==3) {
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
                        if (c.getCerto(ContaPergunta)==4) {
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

                    case R.id.btnLetraE:
                        if (c.getCerto(ContaPergunta)==5) {
                            Cor = R.color.right;
                            Resultado = "Correto";
                            Acertos++;
                            tempo=1000;
                        } else {
                            MostrarCerto();
                        }

                        LE.setText(Resultado);
                        LE.setBackgroundTintList(ContextCompat.getColorStateList(this, Cor));
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
        LE.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.confirm));

    }
    public void TrocaPergunta(){
        switch(getIntent().getStringExtra("name")){
            case "Algoritmo" :
                tipo = 10;
                break;
            case "Programação" :
                tipo = 20;
                break;
            case "Redes" :
                tipo = 30;
                break;
        }
        ContaPergunta++;
        txtQuestao.setText("Questões "+ContaPergunta+"/10");
        txtAcertos.setText("Acertos " + Acertos + "/10");
        TrocaCor();
        String[] s;
        try{
            s = c.getPergunta(ContaPergunta+tipo);
            txtPergunta.setText(s[0]);
            LA.setText(s[1]);
            LB.setText(s[2]);
            LC.setText(s[3]);
            LD.setText(s[4]);
            LE.setText(s[5]);
        }catch (Exception e){
            findViewById(R.id.lilBotao).setVisibility(View.GONE);
            findViewById(R.id.lilTopo).setVisibility(View.GONE);
            txtPergunta.setTextColor(getColor(R.color.right));
            txtPergunta.setTextSize(60);
            if(tipo >= 10) {
                txtPergunta.setText("Ainda não está disponível!");
            }else if(Acertos == 10) {
                txtPergunta.setText("Você acertou tudo, parabéns!");
            }else if(Acertos >= 6){
                txtPergunta.setText("Voce acertou "+ Acertos +" questões, continue assim!");
            }else if(Acertos == 0){
                txtPergunta.setText("Voce errou tudo, estude mais!");
            }else{
                txtPergunta.setText("Voce só acertou "+ Acertos +" questões, tente melhorar!");
                txtPergunta.setTextColor(getColor(R.color.wrong));
            }
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
        switch (c.getCerto(ContaPergunta)){
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
            case 5:
                LE.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.right));
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
}

