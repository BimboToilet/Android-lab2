package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> items = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
            Intent intent = new Intent(getApplicationContext(), ShoplistEditActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ItemsStorage", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("items", null);

        if (set != null) {
            items = new ArrayList<>(set);
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, items);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(getApplicationContext(), ShoplistEditActivity.class);
            intent.putExtra("id", i);
            startActivity(intent);

        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {

            final int itemToDelete = i;
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.sure)
                    .setPositiveButton(R.string.agree, (dialogInterface, i1) -> {
                        items.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("ItemsStorage", Context.MODE_PRIVATE);
                        HashSet<String> set1 = new HashSet<>(MainActivity.items);
                        sharedPreferences1.edit().putStringSet("items", set1).apply();
                    }).setNegativeButton(R.string.notagree, null).show();
            return true;
        });
    }
}