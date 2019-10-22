package com.assignment.miniweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import util.MyInterface;

public class ChooseDefaultCityActivity extends AppCompatActivity {
    private List<String> city_name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_default_city);
        initCityList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ChooseDefaultCityActivity.this,android.R.layout.simple_list_item_1,city_name
        );
        ListView cityListView = findViewById(R.id.city_list);
        cityListView.setAdapter(adapter);
        Button chooseCity = findViewById(R.id.choose_city);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("returnedCityName",city_name.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initCityList() {
        city_name.add("北京");
        city_name.add("上海");
        city_name.add("天津");
        city_name.add("重庆");
        city_name.add("南京");
        city_name.add("武汉");
        city_name.add("广州");
        city_name.add("长沙");
        city_name.add("拉萨");
        city_name.add("西宁");
        city_name.add("南宁");
        city_name.add("石家庄");
        city_name.add("合肥");
        city_name.add("成都");
        city_name.add("太原");
    }
}
