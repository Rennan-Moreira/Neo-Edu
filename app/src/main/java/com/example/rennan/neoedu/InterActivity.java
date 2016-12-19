package com.example.rennan.neoedu;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class InterActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(getIntent().getStringExtra("name"));
        if(getIntent().getStringExtra("name").equals("Banco de Dados")){
            findViewById(R.id.btnBasico).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
            TextView t = (TextView) findViewById(R.id.txtB);
            t.setTextColor(ContextCompat.getColorStateList(this,R.color.colorPrimaryDark));
            t.setEnabled(true);
            ProgressBar p = (ProgressBar) findViewById(R.id.pbBasico);
            p.setProgress(100);
        }
        TextView txt = (TextView) findViewById(R.id.txtBasico);
        txt.setText("Conceitos básicos para iniciantes em "+getIntent().getStringExtra("name").toLowerCase());
        txt = (TextView) findViewById(R.id.txtInter);
        txt.setText("Técnicas e conhecimento consideravel em "+getIntent().getStringExtra("name").toLowerCase());
        txt = (TextView) findViewById(R.id.txtAvancado);
        txt.setText("Alto domínio e técnicas avançadas em "+getIntent().getStringExtra("name").toLowerCase());

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

        if(view.getId() == R.id.btnBasico && getIntent().getStringExtra("name").equals("Banco de Dados")) {

            startActivity(new Intent(getApplicationContext(), Questionario.class).putExtra("name",getIntent().getStringExtra("name")+ " - Básico"));
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Não disponível",
                    Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, findViewById(R.id.lilPrin).getBottom()-60);
            toast.show();
        }
    }
}
