package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private static final int EDIT_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems = (ListView) findViewById(R.id.lvItems);

        items = new ArrayList<String>();
        readItems();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    public void addTodoItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        saveItems();
    }

    private void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetInvalidated();
                saveItems();
                return true;
            }
        });

        final Context c = this;
        lvItems.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> aView, View item, int pos, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(c, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_ITEM_NAME, itemsAdapter.getItem(pos));
                intent.putExtra(EditActivity.EXTRA_ITEM_IDX, pos);
                startActivityForResult(intent, EDIT_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQ_CODE && resultCode == RESULT_OK) {
            int idx = data.getIntExtra(EditActivity.EXTRA_ITEM_IDX, 0);
            String newName = data.getStringExtra(EditActivity.EXTRA_ITEM_NAME);
            items.set(idx, newName);
            itemsAdapter.notifyDataSetInvalidated();
            saveItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    private void saveItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
