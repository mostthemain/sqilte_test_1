package ru.codevia.sqlite_test_1;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        db = this.openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);

        try {

            db.execSQL("CREATE TABLE IF NOT EXISTS MyTable (Field1 VARCHAR);");

            db.execSQL("DELETE FROM MyTable;");


        }catch (SQLException exc){

            Log.e("D","SQLite initialization",exc);

        }


        Log.d("JM",getPragma());

        testWriting();

        setPragma("OFF");

        Log.d("JM",getPragma());

        testWriting();

        Log.d("JM",getPragma());

    }


    private String getPragma(){

        String data = "";

        try{

            Cursor src = db.rawQuery("PRAGMA journal_mode;", null);

            src.moveToFirst();

            data = src.getString(0);

        }catch (Exception exc){

//            Log.e("D","SQLite initialization",exc);

        }

        return data;

    }


    private void setPragma(String mode){

        try{

            db.execSQL("PRAGMA journal_mode="+mode.toUpperCase()+";");

        }catch (SQLException exc){

//            Log.e("D","SQLite initialization",exc);

        }

    }


    public void testWriting(){

        Log.d("D","testWriting");

        try {

            long start = System.currentTimeMillis();

            for (int i = 0; i < 1000; i++) {

                db.execSQL("INSERT INTO MyTable (Field1) values ('"+genValue()+"');");

            }

            Log.d("test",""+(System.currentTimeMillis() - start)/1000.0+"s");

            db.execSQL("DELETE FROM MyTable;");

        }catch (SQLException exc){

            Log.e("D","SQLite writing",exc);

        }

    }


    public void togglePragma(boolean set){

        Log.d("D","togglePragma: "+set);

        try {

//            db.execSQL("INSERT INTO MyTable (Field1) values ('"+genValue()+"');");
            db.execSQL("INSERT INTO MyTable (Field1) values ('sjhfsdfjlsdfjhsdlfjhdkjflsdjfghldskfgjhsldfgjhsdlfjgh');");

        }catch(SQLException exc){

            Log.e("D","SQLite writing",exc);

        }

    }

    final static private String[] ABC = {
        "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    private String genValue(){

        String res = "";

        for(int i=0;i<40;i++){
            res+=ABC[(int)Math.round(Math.random()*(ABC.length-1))];
        }

        return  res;

    }



}
