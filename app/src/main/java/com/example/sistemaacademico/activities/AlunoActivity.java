package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

public class AlunoActivity extends AppCompatActivity {
    AlunoDAO dao;
    EditText edtID, edtNome, edtCpf, edtTelefone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        edtID = findViewById(R.id.editId);
        edtNome = findViewById(R.id.editNome);
        edtCpf = findViewById(R.id.editCpf);
        edtTelefone = findViewById(R.id.editTelefone);

        dao = new AlunoDAO(this);

        RecuperandoAluno();
    }

    public void RecuperandoAluno(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        if (id <= 0){
            Toast.makeText(this, "Erro ao Pegar o ID do USUARIO", Toast.LENGTH_SHORT).show();
        }else{
            Aluno aluno = dao.read(id);

            if (aluno != null){
                edtID.setText(String.valueOf(aluno.getId()));
                edtNome.setText(aluno.getNome());
                edtCpf.setText(aluno.getCpf());
                edtTelefone.setText(aluno.getTelefone());
            }else{
                Toast.makeText(this, "Aluno não encontrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}