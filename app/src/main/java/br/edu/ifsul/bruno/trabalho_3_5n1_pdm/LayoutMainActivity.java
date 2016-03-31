package br.edu.ifsul.bruno.trabalho_3_5n1_pdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import banco.Apoiadora;

public class LayoutMainActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtLocal;
    private EditText edtSabor;
    private Apoiadora apoiadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtLocal = (EditText) findViewById(R.id.edtLocal);
        edtSabor = (EditText) findViewById(R.id.edtSabor);

        // instanciar uma instancia da classe apoiadora
        apoiadora = new Apoiadora(this);

    }

    public void limparCampos(View view) {
        edtNome.setText("");
        edtLocal.setText("");
        edtSabor.setText("");
    }

    public void inserirDados(View view) {

        String nome = edtNome.getText().toString();
        String local = edtLocal.getText().toString();
        String sabor = edtSabor.getText().toString();

        // conecta em modo de escrita
        SQLiteDatabase banco = apoiadora.getWritableDatabase();

        // facilitar inserção de dados
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("local", local);
        valores.put("sabor", sabor);

        long resultado = banco.insert("carrinhos", "_id", valores);

        // testar inserção
        if (resultado != -1) {
            Toast.makeText(LayoutMainActivity.this, "A inserção foi feita com sucesso!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(LayoutMainActivity.this, "Falha na inserção!", Toast.LENGTH_SHORT).show();
        }

        banco.close();
        limparCampos(null);

    }

    public void abreListagem(View view) {
        startActivity(new Intent(this, Listagem.class));
    }
}
