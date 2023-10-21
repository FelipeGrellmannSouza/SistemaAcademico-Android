package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Cadastro extends AppCompatActivity {


    private EditText edtNome, edtCpf, edtTelefone, edtListar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
    }

    public void Gravar(View view){
        Aluno a = new Aluno();
        a.setNome(edtNome.getText().toString());
        a.setCpf(edtCpf.getText().toString());
        a.setTelefone(edtTelefone.getText().toString());

        AlunoDAO dao = new AlunoDAO(this);
        long id = dao.insert(a);
        Toast.makeText(this, "Aluno inserido com o ID " + id, Toast.LENGTH_SHORT).show();
        home();
    }

    private void home(){
        Intent intent = new Intent(Cadastro.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}