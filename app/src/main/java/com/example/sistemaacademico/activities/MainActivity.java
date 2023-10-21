package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvListar;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvListar = findViewById(R.id.lvListar);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        toolbarConfig();

        //mostrarLista
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
        lvListar.setAdapter(adapter);

    }
    //método para mecher no menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarAlunos(s);
                return false;
            }
        });


        return true;
    }

    private void procurarAlunos(String nome) {
        alunosFiltrados.clear();
        for (Aluno a : alunos){
            if (a.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(a);
            }
        }
        lvListar.invalidateViews();
    }

    private void toolbarConfig(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void Cadastrar(MenuItem item){
        Intent intent = new Intent(MainActivity.this, Cadastro.class);
        startActivity(intent);
    }
    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        lvListar.invalidateViews();
    }

}