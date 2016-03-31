package br.edu.ifsul.bruno.trabalho_3_5n1_pdm;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banco.Apoiadora;

/**
 * Created by Bruno on 29/03/2016.
 */
public class Listagem extends ListActivity implements AdapterView.OnItemClickListener {

    // isto é uma activity feita manualmente

    private Apoiadora apoiadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // cria objeto
        apoiadora = new Apoiadora(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // cria adaptador para visualização => adapterView
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, consultar());
        // mostrar
        setListAdapter(adaptador);
        // tornar listagm sensível ao toque
        ListView listagem = getListView();
        // este cara é quem ouve o click
        listagem.setOnItemClickListener(this);
    }

    // método para consultar o banco
    private List<String> consultar () {
        // cria uma lista para segurar result
        List<String> resultado = new ArrayList<String>();
        // instancia do banco
        SQLiteDatabase banco = apoiadora.getReadableDatabase();
        // query executada
        String sql = "SELECT * FROM carrinhos ORDER BY nome;";
        // cria cursor para guardar o result
        Cursor cursor = banco.rawQuery(sql, null);
        // percorrer cursor e montar lista
        for ( cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext() ) {
            resultado.add(cursor.getString(1));
        }
        // fechar o banco e o cursor
        cursor.close();
        banco.close();
        return resultado;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // criar um textview para guardar
        final TextView itemClicado = (TextView) view;
        // criar um dialog para escolha de ação
        AlertDialog.Builder montadorDoDialog = new AlertDialog.Builder(this);
        montadorDoDialog.setTitle("Faça sua escolha: ");
        // Lista de opções
        final String[] opcoesMenu = new String[]{ "Update", "Delete" };
        montadorDoDialog.setItems(opcoesMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String opcao = opcoesMenu[which].toString();

                if (opcao.equals("Update")) {
                    // atualizar
                    Toast.makeText(Listagem.this, "atualizar", Toast.LENGTH_SHORT).show();
                }
                else {
                    // deletar
                    deletarEsteRegistro(itemClicado.getText().toString());
                }
            }
        });
        // aparecer diálogo
        AlertDialog acao = montadorDoDialog.create();
        acao.show();
    }

    private void deletarEsteRegistro (String s) {
        SQLiteDatabase banco = apoiadora.getWritableDatabase();
        // deletar registro
        int res = banco.delete("carrinhos", "nome = ?", new String[]{s});
        if (res > 0) {
            Toast.makeText(Listagem.this, "Registro removido", Toast.LENGTH_SHORT).show();
            this.onResume();
        }
        else {
            Toast.makeText(Listagem.this, "O Registro não pode ser  removido", Toast.LENGTH_SHORT).show();
        }
    }

}
