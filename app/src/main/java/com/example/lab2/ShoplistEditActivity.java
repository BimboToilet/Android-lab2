package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;

public class ShoplistEditActivity extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist_edit);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", -1);
        if (id != -1) {
            editText.setText(MainActivity.items.get(id));
        } else {
            MainActivity.items.add("");
            id = MainActivity.items.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.items.set(id, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ItemsStorage", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.items);
                sharedPreferences.edit().putStringSet("items", set).apply();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}