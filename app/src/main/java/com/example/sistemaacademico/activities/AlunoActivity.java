package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

public class AlunoActivity extends AppCompatActivity {
    AlunoDAO dao;
    Aluno aluno;
    EditText edtID, edtNome, edtCpf, edtTelefone;
    Button btnExcluir, btnAtualizar, btnAtualizar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        iniciandoCoponentes();

        dao = new AlunoDAO(this);

        RecuperandoAluno();

    }
    private void iniciandoCoponentes(){
        edtID = findViewById(R.id.editId);
        edtNome = findViewById(R.id.editNome);
        edtCpf = findViewById(R.id.editCpf);
        edtTelefone = findViewById(R.id.editTelefone);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnAtualizar2 = findViewById(R.id.btnAtualizar2);

        edtNaoEditaveis();
    }


    private void RecuperandoAluno(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        if (id <= 0){
            Toast.makeText(this, "Erro ao Pegar o ID do USUARIO", Toast.LENGTH_SHORT).show();
        }else{
            aluno = dao.read(id);
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

    public void excluirAluno(View view){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluindo Aluno");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setMessage("Tem Certeza que deseja excluir o Aluno: "+ edtNome.getText().toString());
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.delete(aluno);
                Toast.makeText(AlunoActivity.this, "Usuario Excluido", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alerta.show();
    }



    public void atualizarAluno(View view){
        aluno.setNome(edtNome.getText().toString());
        aluno.setCpf(edtCpf.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());

        dao.update(aluno);
        Toast.makeText(this, "Aluno atualizado com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void prepAtualizar(View view){
        edtNome.setEnabled(true);
        edtCpf.setEnabled(true);
        edtTelefone.setEnabled(true);

        btnExcluir.setVisibility(View.GONE);
        btnAtualizar.setVisibility(View.GONE);
        btnAtualizar2.setVisibility(View.VISIBLE);
    }

    private void edtNaoEditaveis(){
        edtNome.setEnabled(false);
        edtCpf.setEnabled(false);
        edtTelefone.setEnabled(false);
        btnAtualizar2.setVisibility(View.GONE);
    }




}