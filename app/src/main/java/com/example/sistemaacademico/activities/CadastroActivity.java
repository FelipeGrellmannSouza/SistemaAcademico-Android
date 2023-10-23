package com.example.sistemaacademico.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemaacademico.R;
import com.example.sistemaacademico.dao.AlunoDAO;
import com.example.sistemaacademico.model.Aluno;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtNome, edtCpf, edtTelefone;
    private String ultimoCaractereDigitado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.editNome);
        edtCpf = findViewById(R.id.editCpf);
        edtTelefone = findViewById(R.id.editTelefone);

        mascaraTelefone();
        mascaraCpf();
    }

    //Usa o insert da classe AlunoDAO para adicionar um aluno ao banco de dados
    private void SalvarAluno(){
        Aluno a = new Aluno();
        a.setNome(edtNome.getText().toString());
        a.setCpf(edtCpf.getText().toString());
        a.setTelefone(edtTelefone.getText().toString());

        AlunoDAO dao = new AlunoDAO(this);
        long id = dao.insert(a);
        Toast.makeText(this, "Aluno inserido com o ID " + id, Toast.LENGTH_SHORT).show();
        home();
    }

    //Verifica se os dados são validos e chama o método SalvarAluno
    private void ValidarDados(){
        Integer carateresTelefone = edtTelefone.getText().toString().length();
        Integer caracteresCpf = edtCpf.getText().toString().length();
        String nome = edtNome.getText().toString();

        //Validação dos dados
        if (!nome.isEmpty()){
            if (caracteresCpf == 14){
                if (carateresTelefone == 13){
                    SalvarAluno();
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

    //Método do boão
    public void Gravar(View view){
        ValidarDados();
    }

    //Chama a MainActivity e fecha a tela atual
    private void home(){
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
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