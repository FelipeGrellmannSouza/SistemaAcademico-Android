package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btnGravar, btnCadastrar;

    private ListView lvListar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvListar = findViewById(R.id.lvListar);
        toolbarConfig();


        listarLV();
    }

    private void listarLV() {
        List<Aluno> alunos = new ArrayList<Aluno>();
        List<String> alunosListView = new ArrayList<>();

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        for (Aluno aluno : alunos){
            alunosListView.add(aluno.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunosListView);
        lvListar.setAdapter(adapter);
    }

    private void toolbarConfig(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //método para mecher no menu
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);


        //Definindo o Buscar
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //Verifica a mudança no texto do SearchView
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("Digitou "+ s);
                return false;
            }
        });
        return true;
    }

    public void procurarAluno(String nome){
        AlunoDAO alunoDAO = new AlunoDAO(this);
    }

    public void Cadastrar(MenuItem item){
        Intent intent = new Intent(MainActivity.this, Cadastro.class);
        startActivity(intent);
    }

}