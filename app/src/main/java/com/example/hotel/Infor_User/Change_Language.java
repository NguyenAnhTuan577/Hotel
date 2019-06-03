package com.example.hotel.Infor_User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hotel.R;

import java.util.ArrayList;

public class Change_Language extends AppCompatActivity {
    ListView listViewLanguage;
    ArrayList<String> arrLanguage;
    TextView txtBackLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__language);

        listViewLanguage=(ListView) findViewById(R.id.listViewLanguage);
        txtBackLanguage=(TextView) findViewById(R.id.txtBackLanguage);

        txtBackLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        InitArrayLanguage();
        ArrayAdapter adapter=new ArrayAdapter(Change_Language.this,R.layout.support_simple_spinner_dropdown_item,arrLanguage);
        listViewLanguage.setAdapter(adapter);
    }

    public void InitArrayLanguage()
    {
        arrLanguage=new ArrayList<>();
        arrLanguage.add("Bengali");
        arrLanguage.add("Bhojpuri");
        arrLanguage.add("English");
        arrLanguage.add("German");
        arrLanguage.add("Hausa");
        arrLanguage.add("Italian");
        arrLanguage.add("Japanese");
        arrLanguage.add("Korean");
        arrLanguage.add("Mandarin Chinese");
        arrLanguage.add("Persian");
        arrLanguage.add("Portuguese");
        arrLanguage.add("Thai");
        arrLanguage.add("Vietnamese");
        arrLanguage.add("Yue Chinese ");
        arrLanguage.add("Western Punjabi");
    }
}
