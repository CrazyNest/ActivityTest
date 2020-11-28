package com.example.crazynest.activitytest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Kubrai extends AppCompatActivity {

    String test="test";
    TextView textView;
    int kub_cl;//кубраи текущий уровень
    int kub_f;//кубраи неверный ответ (false)
    int kub_maxlvl=10;//максимальный уровень в кубраях
    //Переменная для работы с БД
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kubrai);
        mDBHelper= new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        load();
        zapolnenie();
    }
    @Override
    protected void onResume()
    {
       super.onResume();
        load();
        zapolnenie();
    }
    public void load() {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        kub_cl = pref.getInt("kubrai_current_level", 0);
        kub_f=pref.getInt("kubrai_false", 0);
    }
    public void saved() {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);//main- имя файла, MODE-PRIVATE режим доступа
        SharedPreferences.Editor editPref = pref.edit();//вроде как открываю для редаектирования
        editPref.putInt("kubrai_current_level", kub_cl);//сохраняю значение по ключу
        editPref.putInt("kubrai_false", kub_f);
        editPref.commit();//обязательный оператор, чтобы сохранить данные
    }

    public void zapolnenie()//первоначальная подгрузка уровня
    {
        String ZadanieText = "";
        String LevelText="Кубраи. Уровень ";
        load();
        Cursor cursor = mDb.rawQuery("SELECT * FROM kub", null);//записываю в переменную cursor типа cursor все данные из таблицы
        if (kub_cl!=kub_maxlvl) {
            cursor.moveToPosition(kub_cl);//перехожу на необходимую строку
            LevelText += cursor.getString(0);//записываю в строку номер уровня
            LevelText += "/"+kub_maxlvl+".";
            ZadanieText += cursor.getString(1);//записываю в строку задание
            cursor.close();//закрываю курсор
            textView = (TextView) findViewById(R.id.Level);//переопределяю поле
            textView.setText(LevelText);//вывожу в первое поле
            textView = (TextView) findViewById(R.id.Zadanie);//переопределяю поле
            textView.setText(ZadanieText);//вывожу во второе поле
        }
        else {
            AlertDialog.Builder winners = new AlertDialog.Builder(this);
            winners.setTitle("Поздравляем!");
            winners.setMessage("Вы успешно прошли все кубраи./n");
            winners.setCancelable(false);
            winners.setNegativeButton("В меню",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Kubrai.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = winners.create();
            alert.show();
        }
    }
    public void Proverka(View view)
    {
        //запись правильного ответа
        String Prav_Otvet="";
        Cursor cursor = mDb.rawQuery("SELECT * FROM kub", null);//записываю в переменную cursor типа cursor все данные из таблицы
        load();
        cursor.moveToPosition(kub_cl);//перехожу на первую строку
        Prav_Otvet += cursor.getString(2);//записываю в стр определенный столбец выбранной строки

        EditText text = (EditText)findViewById(R.id.Otvet);
        String Uzer_Otvet="";
        Uzer_Otvet += text.getText().toString();
        if (Uzer_Otvet.compareTo(Prav_Otvet) == 0)
        {
            call_allert(0);
            kub_cl++;
            saved();
            clear();
            zapolnenie();
        }
        else {
            call_allert(1);
            kub_f++;
            saved();
            clear();
        }

    }
    public int call_allert(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyles);
        builder.setTitle("");
        if (i==0){
            builder.setMessage("Задание выполнено!");}
        else {
            builder.setMessage("Задание не выполнено=("); }
        builder.setCancelable(false);
        builder.setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        return 0;
    }
    public void clear()
    {
        textView = (TextView) findViewById(R.id.Otvet);//переопределяю поле
        textView.setText("");//вывожу в первое поле
    }
    public void about(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.DialogStyles);
        builder1.setTitle("Справка");
        builder1.setMessage("Кубрая - вид шарады, когда слово нужно разгадывать по частям. Загаданное слово разбивается на части (например: шарада = шар+ада = куб+рая), после чего каждое слово заменяется на антонимы, ассоциации, родственные по смыслу или просто то же слово с переставленными в обратном порядке буквами. Кроме этого в задании может указываться к какой области загаданное слово относится. Ваша задача найти правильный ответ.\n\nПримеры:\n1. шарада = шар+ада = куб+рая\n2. список = спи+сок = вставай+компот\n" +
                "\n");
        builder1.setCancelable(false);
        builder1.setNegativeButton("Понятно",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }
    public void go_main(View view)
    {
        Intent intent = new Intent(Kubrai.this, MainActivity.class);
        startActivity(intent);
    }
}
