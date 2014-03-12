package codepath.apps.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

    public static final String EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME";
    public static final String EXTRA_ITEM_IDX = "EXTRA_ITEM_IDX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText t = (EditText) findViewById(R.id.etEditItem);
        t.setText(getIntent().getStringExtra(EXTRA_ITEM_NAME));
        t.setSelection(t.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    public void editItem(View v) {
        String newItemName = ((EditText) findViewById(R.id.etEditItem)).getText().toString();
        Intent resultData = new Intent();
        resultData.putExtra(EXTRA_ITEM_NAME, newItemName);
        resultData.putExtra(EXTRA_ITEM_IDX, getIntent().getIntExtra(EXTRA_ITEM_IDX, 0));
        setResult(RESULT_OK, resultData);
        finish();
    }

}
