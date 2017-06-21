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

        //LinearLayout lil = (LinearLayout) findViewById(R.id.lilAlg);

        this.setTitle("Início");
        findViewById(R.id.nsvIni).setVisibility(View.VISIBLE);
        findViewById(R.id.nsvApli).setVisibility(View.GONE);
        findViewById(R.id.nsvUsu).setVisibility(View.GONE);

        TextView txt;
        EditText edt;
        Bundle bun = getIntent().getExtras();
        edt = (EditText) findViewById(R.id.edtNome);
        edt.setText(bun.getString("nome"));
        edt = (EditText) findViewById(R.id.edtEmail);
        edt.setText(bun.getString("email"));
        edt = (EditText) findViewById(R.id.edtUsuario);
        edt.setText(bun.getString("login"));

        txt = (TextView) findViewById(R.id.txtUser);
        txt.setText("Olá usuário "+ bun.getString("login"));
        txt = (TextView) findViewById(R.id.txtEmail);
        txt.setText(bun.getString("email"));
        txt = (TextView) findViewById(R.id.txtNome);
        txt.setText(bun.getString("nome"));


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
        TextView txt;
        Bundle bun = getIntent().getExtras();
        txt = (TextView) findViewById(R.id.txtEmailNav);
        txt.setText(bun.getString("email"));
        txt = (TextView) findViewById(R.id.txtNomeNav);
        txt.setText(bun.getString("nome"));

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
                startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("name", "Banco de Dados"));

            return false;
        } else if (id == R.id.nav_prog){
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();
            return false;
        } else if (id == R.id.nav_rede){
            Toast.makeText(getApplicationContext(), "Não disponível", Toast.LENGTH_SHORT).show();
            return false;
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnBanco) {
            startActivity(new Intent(getApplicationContext(), InterActivity.class).putExtra("name", "Banco de Dados"));
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

