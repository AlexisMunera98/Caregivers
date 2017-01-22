package com.example.gabriel.caregivers.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.caregivers.DataBase.DataBaseManager;
import com.example.gabriel.caregivers.Fragments.FragmentEnfermedad;
import com.example.gabriel.caregivers.Fragments.FragmentInforme;
import com.example.gabriel.caregivers.Fragments.FragmentPaciente;
import com.example.gabriel.caregivers.Fragments.FragmentPerfil;
import com.example.gabriel.caregivers.Fragments.FragmentRecordatorio;
import com.example.gabriel.caregivers.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView edtNomCuidador;
    private CircleImageView imgPerfilNav;
    private Fragment[] fragments;
    private SearchView searchView;
    private View viewFiltro;
    private Menu menu;
    private DataBaseManager dbManager;
    private String cedula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Intent intento = getIntent();
        cedula = intento.getStringExtra(loginActivity.CEDULA);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        importarComponentes(navigationView);
        declararAccionComponentes();
        fragments = new Fragment[5];
        dbManager = new DataBaseManager(getApplicationContext());
    }


    private void declararAccionComponentes() {
        imgPerfilNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPath = dbManager.getPathFoto(cedula);
                if (!(mPath.isEmpty())) {
                    ((FragmentPerfil) fragments[0]).abrirImagenConGaleria(mPath);
                } else {
                    Toast.makeText(getApplicationContext(), "No has elegido una imagen de perfil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void importarComponentes(NavigationView navigationView) {
        imgPerfilNav = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imgPerfilNav);
        edtNomCuidador = (TextView) navigationView.getHeaderView(0).findViewById(R.id.edtNomCuidador);

    }

    public void actualizarImagen(String path) {
        if (path.isEmpty()) {
            imgPerfilNav.setImageResource(R.drawable.ic_perfil);
            return;
        }
        Bitmap btm = BitmapFactory.decodeFile(path);
        imgPerfilNav.setImageBitmap(btm);
    }

    public void actualizarImagen(Bitmap btmp) {
        if (btmp != null) {
            imgPerfilNav.setImageBitmap(btmp);
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
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }


    private void inflarBuscador() {
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.filtro, menu);
        MenuItem itemSearch = menu.findItem(R.id.search);
        viewFiltro = findViewById(R.id.filtro);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    ((FragmentPaciente) fragments[1]).actualizarLista(newText);

                }
                ((FragmentPaciente) fragments[1]).actualizarLista(newText);
                return false;
            }
        });
        viewFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialogoFiltro();
            }
        });
    }

    private void crearDialogoFiltro() {
        final CharSequence[] option = {"Mis pacientes", "Todos los pacientes"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar por:");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Mis pacientes") {
                    Toast.makeText(getApplicationContext(), "Mis pacientes", Toast.LENGTH_SHORT).show();
                } else if (option[which] == "Todos los pacientes") {
                    Toast.makeText(getApplicationContext(), "Todos los pacientes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.filtro) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //POR CORREGIR PUTS EN EL BUNDLE
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        if (id == R.id.nav_perfil) {
            if (fragments[0] == null) {
                Bundle arg = new Bundle();
                arg.putString("Cedula", "1234");
                fragment = FragmentPerfil.newInstance(arg);
                fragments[0] = fragment;
            }
            esconderBuscador();
            ft.replace(R.id.content_main, fragments[0]);
            ft.commit();

        } else if (id == R.id.nav_pacientes) {
            if (fragments[1] == null) {
                Bundle arg = new Bundle();
                arg.putString("datos", "Paciente ñlkj");
                fragment = FragmentPaciente.newInstance(arg);
                fragments[1] = fragment;
                inflarBuscador();
            }
            searchView.setVisibility(View.VISIBLE);
            viewFiltro.setVisibility(View.VISIBLE);

            ft.replace(R.id.content_main, fragments[1]);
            ft.commit();


        } else if (id == R.id.nav_recordatorios) {
            if (fragments[2] == null) {
                Bundle arg = new Bundle();
                arg.putString("datos", "Recodatorio qwer");
                fragment = FragmentRecordatorio.newInstance(arg);
                fragments[2] = fragment;
            }
            esconderBuscador();
            ft.replace(R.id.content_main, fragments[2]);
            ft.commit();


        } else if (id == R.id.nav_enfermedad) {
            if (fragments[3] == null) {
                Bundle arg = new Bundle();
                arg.putString("datos", "Enfermedad poiu");
                fragment = FragmentEnfermedad.newInstance(arg);
                fragments[3] = fragment;
            }
            esconderBuscador();
            ft.replace(R.id.content_main, fragments[3]);
            ft.commit();

        } else if (id == R.id.nav_informes) {
            if (fragments[4] == null) {
                Bundle arg = new Bundle();
                arg.putString("datos", "Informe mjghd");
                fragment = FragmentInforme.newInstance(arg);
                fragments[4] = fragment;
            }
            esconderBuscador();

            ft.replace(R.id.content_main, fragments[4]);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void esconderBuscador() {
        if (searchView != null) {
            searchView.setVisibility(View.INVISIBLE);
            viewFiltro.setVisibility(View.INVISIBLE);
        }

    }

    public String getCedula() {
        return cedula;
    }

    public DataBaseManager getDbManager() {
        return dbManager;

    }


}
