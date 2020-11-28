package com.example.crazynest.activitytest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Stat extends AppCompatActivity {
    int kub_cl, kub_f, gib_cl, gib_f, ras_cl, ras_f;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        load();
        pokaz();
    }

    public void load() {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        kub_cl = pref.getInt("kubrai_current_level", 0);
        kub_f = pref.getInt("kubrai_false", 0);
        gib_cl = pref.getInt("gibridy_current_level", 0);
        gib_f = pref.getInt("gibridy_false", 0);
        ras_cl = pref.getInt("raschlenenka_current_level",0);
        ras_f= pref.getInt("raschlenenka_false",0);
    }
    public void pokaz(){
        String level = "Пройдено уровней:\n   Кубраи - "+kub_cl+".\n   Гибриды - "+gib_cl+"\n   Расчлененка - "+ras_cl+"\n";
        String error= "Допущено ошибок в уровнях:\n   Кубраи - "+kub_f+".\n   Гибриды - "+gib_f+"\n   Расчлененка - "+ras_f+"\n";
        String s=level+error;
        textView = (TextView) findViewById(R.id.kub_stat);//переопределяю поле
        //textView.setText("Пройдено уровней:\n Кубраи- "+kub_cl+".\nГибриды- "+1+".\n");//вывожу в первое поле
        textView.setText(s);
    }
    public void clear_stat(View view){
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);//main- имя файла, MODE-PRIVATE режим доступа
        SharedPreferences.Editor editPref = pref.edit();//вроде как открываю для редаектирования
        editPref.putInt("kubrai_current_level", 0);//сохраняю значение по ключу
        editPref.putInt("kubrai_false", 0);
        editPref.putInt("gibridy_current_level", 0);//сохраняю значение по ключу
        editPref.putInt("gibridy_false", 0);
        editPref.putInt("raschlenenka_current_level", 0);
        editPref.putInt("raschlenenka_false", 0);
        editPref.commit();//обязательный оператор, чтобы сохранить данные
        Toast msg = Toast.makeText(getApplicationContext(),
                "Данные успешно удалены!", Toast.LENGTH_SHORT);
        msg.show();
        load();
        pokaz();
    }

}