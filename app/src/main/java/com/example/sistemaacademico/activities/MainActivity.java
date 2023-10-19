package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtNome, edtCpf, edtTelefone, edtListar;
    private Button btnGravar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializando
        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtListar = findViewById(R.id.edtListar);
        btnGravar = findViewById(R.id.btnGravar);
    }
    
    public void Gravar(View view){
        Aluno a = new Aluno();
        a.setNome(edtNome.getText().toString());
        a.setCpf(edtCpf.getText().toString());
        a.setTelefone(edtTelefone.getText().toString());
        

        AlunoDAO dao = new AlunoDAO(this);
        long id = dao.insert(a);
        Toast.makeText(this, "Aluno inserido com o ID " + id, Toast.LENGTH_SHORT).show();
        listar();
    }

    private void listar() {
        List<Aluno> alunos = new ArrayList<Aluno>();
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        for (Aluno aluno : alunos){
            edtListar.append("ID : " + aluno.getId() + "\n");
            edtListar.append("Nome : " + aluno.getNome() + "\n");
            edtListar.append("CPF : " + aluno.getCpf() + "\n");
            edtListar.append("Telefone : " + aluno.getTelefone() + "\n");
        }


    }


}