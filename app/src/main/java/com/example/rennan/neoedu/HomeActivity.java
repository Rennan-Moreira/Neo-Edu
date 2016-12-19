package com.example.rennan.neoedu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ActionMenuView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rennan on 22/11/2016.
 */

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar3);
        pb.setProgress(100);

        this.setTitle("Início");
        if (getIntent().getStringExtra("user").equals("professor")) {
            LinearLayout lil;
            TextView txt;
            EditText edt;

            edt = (EditText) findViewById(R.id.edtNome);
            edt.setText("José da Silva");
            edt = (EditText) findViewById(R.id.edtEmail);
            edt.setText("jose.prof@hotmail.com");
            edt = (EditText) findViewById(R.id.edtData);
            edt.setText("01/01/1965");
            edt = (EditText) findViewById(R.id.edtUsuario);
            edt.setText("Professor01");

            txt = (TextView) findViewById(R.id.txtUser);
            txt.setText("Olá professor José");
            txt = (TextView) findViewById(R.id.txtEmail);
            txt.setText("jose.prof@hotmail.com");
            txt = (TextView) findViewById(R.id.txtNome);
            txt.setText("José da Silva");
            txt = (TextView) findViewById(R.id.txtData);
            txt.setText("01/01/1965");

            lil = (LinearLayout) findViewById(R.id.lilAlg);
            lil.setVisibility(View.GONE);
            lil = (LinearLayout) findViewById(R.id.lilPro);
            lil.setVisibility(View.GONE);
            lil = (LinearLayout) findViewById(R.id.lilRed);
            lil.setVisibility(View.GONE);

            Button btn= (Button) findViewById(R.id.btnBanco);
            btn.setText("Ver");

            pb.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        if (getIntent().getStringExtra("user").equals("professor")) {
            TextView txt;

            txt = (TextView) findViewById(R.id.txtEmailNav);
            txt.setText("jose.prof@hotmail.com");
            txt = (TextView) findViewById(R.id.txtNomeNav);
            txt.setText("José da Silva");


        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_banco) {

            startActivity(new Intent(getApplicationContext(), Questionario.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
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
        } else if (id == R.id.nav_MedAce) {
            return false;
        } else if (id == R.id.nav_algo){
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();
            return false;
        } else if (id == R.id.nav_banco){
            if (getIntent().getStringExtra("user").equals("professor")) {
                startActivity(new Intent(getApplicationContext(), InterActivityProf.class));
            }else{
                startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("name", "Banco de Dados"));
            }
            return false;
        } else if (id == R.id.nav_prog){
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();
            return false;
        } else if (id == R.id.nav_rede){
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();
            return false;
        } else if (id == R.id.nav_usua) {
            this.setTitle("Usuário");
            findViewById(R.id.nsvIni).setVisibility(View.GONE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_apli) {
            this.setTitle("Aplicativo");
            findViewById(R.id.nsvIni).setVisibility(View.GONE);
            findViewById(R.id.nsvApli).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnBanco) {
            if (getIntent().getStringExtra("user").equals("professor")) {
                startActivity(new Intent(getApplicationContext(), InterActivityProf.class));
            }else{
                startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("name", "Banco de Dados"));
            }
        } else if (id == R.id.btnVoltar ||id == R.id.btnVoltar2){
            this.setTitle("Início");
            findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
            findViewById(R.id.nsvApli).setVisibility(View.GONE);
            findViewById(R.id.nsvUsu).setVisibility(View.GONE);
        } else {
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();

        }
    }
}

