package com.example.rennan.neoedu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class InterActivity extends AppCompatActivity implements View.OnClickListener{

    int did;
    int uid;
    int tid;
    int ic;
    EditText cd;
    TextView D,P,Ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("Turma");
//            findViewById(R.id.btnBasico).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.VerdeEdu));
//            TextView t = (TextView) findViewById(R.id.txtB);
//            t.setTextColor(ContextCompat.getColorStateList(this,R.color.colorPrimaryDark));
//            t.setEnabled(true);
        Bundle bun = getIntent().getExtras();
        uid = bun.getInt("uid");
        tid = bun.getInt("tid");
        did = bun.getInt("did");
        ic = bun.getInt("ic");
        Toast.makeText(this, ""+did, Toast.LENGTH_SHORT).show();
        D = (TextView)findViewById(R.id.txtNmDisc);
        P = (TextView) findViewById(R.id.txtNmProf);
        Ds = (TextView) findViewById(R.id.txtDsDesc);
        D.setText(getIntent().getStringExtra("name"));
        P.setText(getIntent().getStringExtra("prof"));
        Ds.setText(getIntent().getStringExtra("ds"));
        cd = (EditText)findViewById(R.id.edtCodAc);
        if(ic == 0){
            findViewById(R.id.scrQuestao).setVisibility(View.GONE);
            findViewById(R.id.lilInscreve).setVisibility(View.VISIBLE);
        }


        TextView txt = (TextView) findViewById(R.id.txtBasico);
        txt.setText("Conceitos básicos para iniciantes em "+getIntent().getStringExtra("name").toLowerCase());
        txt = (TextView) findViewById(R.id.txtInter);
        txt.setText("Bom domínio e técnicas medianas em "+getIntent().getStringExtra("name").toLowerCase());
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

        if(view.getId() == R.id.btnBasico ){//&& getIntent().getStringExtra("name").equals("Banco de dados")) {
            startActivity(new Intent(getApplicationContext(), Questionario.class).putExtra("name",getIntent().getStringExtra("name")+ " - Básico").putExtra("mol",1).putExtra("tid",tid).putExtra("did",did).putExtra("uid",uid));
        } else if (view.getId() == R.id.btnInter){//&& getIntent().getStringExtra("name").equals("Banco de dados")) {
            startActivity(new Intent(getApplicationContext(), Questionario.class).putExtra("name",getIntent().getStringExtra("name")+ " - Básico").putExtra("mol",2).putExtra("tid",tid).putExtra("did",did).putExtra("uid",uid));
        } else if (view.getId() == R.id.btnAvancado ){//&& getIntent().getStringExtra("name").equals("Banco de dados")) {
            startActivity(new Intent(getApplicationContext(), Questionario.class).putExtra("name",getIntent().getStringExtra("name")+ " - Básico").putExtra("mol",3).putExtra("tid",tid).putExtra("did",did).putExtra("uid",uid));
        } else if (view.getId() == R.id.btnR ){//&& getIntent().getStringExtra("name").equals("Banco de dados")) {
            startActivity(new Intent(getApplicationContext(), RanqActivity.class).putExtra("name",getIntent().getStringExtra("name")).putExtra("tid",tid));
        } else if (view.getId() == R.id.btnInscreve){
            view.setVisibility(View.GONE);
            findViewById(R.id.tilA).setVisibility(View.VISIBLE);
            findViewById(R.id.btnInsc).setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btnInsc){
            hideKeyboard(getApplicationContext(),cd);
            String url = "https://neoedu-laravel-api-mateusvilione.c9users.io/turmaestudante";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); // this = context

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){ //Quando esta OK
                            Toast toast = Toast.makeText(getApplicationContext(), "Inscrito com sucesso",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();
                            findViewById(R.id.lilInscreve).setVisibility(View.GONE);
                            findViewById(R.id.scrQuestao).setVisibility(View.VISIBLE);

                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){ // Deu Merda
                            Toast toast = Toast.makeText(getApplicationContext(), "Erro",
                                    Toast.LENGTH_SHORT);

                            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,1290);
                            toast.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map = Chave valor
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("turma_id", ""+tid);
                    parametros.put("estudante_id", ""+uid);
                    parametros.put("cd_acesso", "nada");
                    return parametros;
                }
            };
            queue.add(request);

        }
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
