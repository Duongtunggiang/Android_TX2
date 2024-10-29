package com.example.tx2_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerForum;
    private EditText editTextMessage;
    private TextView textViewRegistrationCount;
    private CheckBox checkBoxGame;
    private CheckBox checkBoxWeb;
    private Button buttonViewResults;
    private int registrationCount = 0;
    private ArrayList<String> forumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinnerForum = findViewById(R.id.spinnerForum);
        editTextMessage = findViewById(R.id.editTextMessage);
        textViewRegistrationCount = findViewById(R.id.textViewRegistrationCount);
        checkBoxGame = findViewById(R.id.checkBoxGame);
        checkBoxWeb = findViewById(R.id.checkBoxWeb);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        buttonViewResults = findViewById(R.id.buttonViewResults);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.forum_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForum.setAdapter(adapter);

        buttonViewResults.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            intent.putExtra("forumList", forumList);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_action, menu);
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

            if (itemId == R.id.menu_add){
                addForum();
                return true;
            }
            else if (itemId == R.id.menu_save){
                saveForum();
                return true;
            }
            else {
                return super.onOptionsItemSelected(item);
            }
    }

    private void addForum() {
        String formus = spinnerForum.getSelectedItem().toString();
        String message = editTextMessage.getText().toString();
        String intersets = "";

        if (checkBoxGame.isChecked()) {
            intersets += "Game ";
        }
        if (checkBoxWeb.isChecked()) {
            intersets += "Web ";
        }
        if (formus.isEmpty() || message.isEmpty() || intersets.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            registrationCount++;
            textViewRegistrationCount.setText("Số lần đăng ký: " + registrationCount);
            forumList.add(formus + " - " + intersets + " - " + message);
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        }
        clearForm();
    }
    private void clearForm() {
        spinnerForum.setSelection(0);
        editTextMessage.setText("");
        checkBoxGame.setChecked(false);
        checkBoxWeb.setChecked(false);
    }
    private void saveForum() {
        try(FileOutputStream fos = openFileOutput("forum.txt", MODE_PRIVATE)) {
            for(String forum : forumList) {
                fos.write((forum + "\n").getBytes());
            }
            Toast.makeText(this,"Lưu trữ thành công", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "Lỗi lưu trữ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}