package com.example.crazynest.activitytest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    int kub_cl;//текущий уровень кубраи
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //zapusk();
    }
    public void Go_Kub(View view) {
        Intent intent = new Intent(MainActivity.this, Kubrai.class);
        startActivity(intent);
    }
    public void Go_Gib(View view) {
        Intent intent = new Intent(MainActivity.this, Gibridy.class);
        startActivity(intent);
    }
    public void Go_Stat(View view) {
        Intent intent = new Intent(MainActivity.this, Stat.class);
        startActivity(intent);
    }
    public void Go_Ras(View view) {
        Intent intent = new Intent(MainActivity.this, Raschlenenka.class);
        startActivity(intent);
    }
   /* public void zapusk(){
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        kub_cl = pref.getInt("kubrai_current_level", 0);
        saved();
    }
    public void saved() {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);//main- имя файла, MODE-PRIVATE режим доступа
        SharedPreferences.Editor editPref = pref.edit();//вроде как открываю для редаектирования
        editPref.putInt("kubrai_current_level", kub_cl);//сохраняю значение по ключу
        editPref.commit();//обязательный оператор, чтобы сохранить данные
    }*/
}
