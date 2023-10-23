package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String ultimoCaractereDigitado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        iniciandoCoponentes();
        dao = new AlunoDAO(this);
        RecuperandoAluno();

        mascaraTelefone();
        mascaraCpf();
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

    private void validarDados(){
        Integer carateresTelefone = edtTelefone.getText().toString().length();
        Integer caracteresCpf = edtCpf.getText().toString().length();
        String nome = edtNome.getText().toString();

        //Validação dos dados
        if (!nome.isEmpty()){
            if (caracteresCpf == 14){
                if (carateresTelefone == 13){
                    //Atualiza o aluno
                    aluno.setNome(edtNome.getText().toString());
                    aluno.setCpf(edtCpf.getText().toString());
                    aluno.setTelefone(edtTelefone.getText().toString());

                    dao.update(aluno);
                    Toast.makeText(this, "Aluno atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this, "Digite um Telefone Valido ", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Digite um CPF Valido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Digite um Nome", Toast.LENGTH_SHORT).show();
        }
    }


    public void atualizarAluno(View view){
        validarDados();
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


    //mascara para a formatação do texto do Telefone. Deixa no padrão (11 11111-1111)
    private void mascaraTelefone(){
        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoEdtTelefone = edtTelefone.getText().toString().length();
                if(tamanhoEdtTelefone > 1 ){
                    ultimoCaractereDigitado = edtTelefone.getText().toString().substring(tamanhoEdtTelefone-1);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoEdtTelefone = edtTelefone.getText().toString().length();
                if(tamanhoEdtTelefone == 2){
                    if(!ultimoCaractereDigitado.equals(" ")){
                        edtTelefone.append(" ");
                    }else{
                        edtTelefone.getText().delete(tamanhoEdtTelefone - 1, tamanhoEdtTelefone);
                    }
                }else if (tamanhoEdtTelefone == 8){
                    if(!ultimoCaractereDigitado.equals("-")){
                        edtTelefone.append("-");
                    }else{
                        edtTelefone.getText().delete(tamanhoEdtTelefone - 1, tamanhoEdtTelefone);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    //mascara para a formatação do texto do cpf. Deixa no padrão (111.111.111-11)
    private void mascaraCpf(){
        edtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoEdtCpf = edtCpf.getText().toString().length();
                if(tamanhoEdtCpf > 1 ){
                    ultimoCaractereDigitado = edtCpf.getText().toString().substring(tamanhoEdtCpf-1);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoEdtCpf = edtCpf.getText().toString().length();
                if(tamanhoEdtCpf == 3){
                    if(!ultimoCaractereDigitado.equals(".")){
                        edtCpf.append(".");
                    }else{
                        edtCpf.getText().delete(tamanhoEdtCpf - 1, tamanhoEdtCpf);
                    }
                }else if (tamanhoEdtCpf == 7){
                    if(!ultimoCaractereDigitado.equals(".")){
                        edtCpf.append(".");
                    }else{
                        edtCpf.getText().delete(tamanhoEdtCpf - 1, tamanhoEdtCpf);
                    }
                } else if (tamanhoEdtCpf == 11) {
                    if(!ultimoCaractereDigitado.equals("-")){
                        edtCpf.append("-");
                    }else{
                        edtCpf.getText().delete(tamanhoEdtCpf - 1, tamanhoEdtCpf);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }




}