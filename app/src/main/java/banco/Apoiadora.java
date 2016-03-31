package banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bruno on 29/03/2016.
 */
public class Apoiadora extends SQLiteOpenHelper {

    private static final String nomeBancoDados = "cadastro.db";
    private static final    int versao = 1;

    public Apoiadora(Context context) {
        super(context, nomeBancoDados, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // banco não existe
        String sql = "CREATE TABLE carrinhos (_id INTEGER PRIMARY KEY, nome TEXT, local TEXT, sabor TEXT);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
