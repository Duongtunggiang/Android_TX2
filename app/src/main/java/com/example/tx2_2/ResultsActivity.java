package com.example.tx2_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private ListView listViewRegis;
    private Button buttonBack, buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_layout);

        listViewRegis = findViewById(R.id.listViewResults);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSend = findViewById(R.id.buttonSend);

        // Lấy danh sách đăng ký từ intent
        ArrayList<String> forumList = getIntent().getStringArrayListExtra("forumList");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, forumList);
        listViewRegis.setAdapter(adapter);

        buttonBack.setOnClickListener(v -> finish());

        buttonSend.setOnClickListener(v ->{
            // Gửi thông báo đăng ký đến ứng dụng khác
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Đăng ký thành công!");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ktpm2@gmail.com"});
            intent.putExtra(Intent.EXTRA_TEXT, String.join("\n", forumList));
            startActivity(intent);
        });
    }
}
