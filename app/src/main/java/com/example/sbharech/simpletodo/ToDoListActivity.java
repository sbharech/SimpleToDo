package com.example.sbharech.simpletodo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sbharech.simpletodo.R;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ToDoListActivity extends ActionBarActivity {
    private ArrayList<String> todoItemList;
    private ListView itemsView;
    private Button addButton;
    private EditText newItem;
    private ArrayAdapter<String> todoItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        itemsView = (ListView)findViewById(R.id.lvItems);
        addButton = (Button)findViewById(R.id.btAdd);
        newItem = (EditText)findViewById(R.id.tvNewItem);

        readItemsFromFile();

        todoItemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0, todoItemList);
        itemsView.setAdapter(todoItemAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemStr = newItem.getText().toString();
                if (!newItemStr.isEmpty()) {
                    todoItemList.add(newItemStr);
                    todoItemAdapter.notifyDataSetChanged();
                    newItem.setText("");
                    writeItemsFromFile();
                }
            }
        });

        itemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItemList.remove(position);
                todoItemAdapter.notifyDataSetChanged();
                writeItemsFromFile();
                return true;
            }
        });
    }

    private void readItemsFromFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            todoItemList = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            todoItemList = new ArrayList<String>();
        }
    }

    private void writeItemsFromFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, todoItemList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
