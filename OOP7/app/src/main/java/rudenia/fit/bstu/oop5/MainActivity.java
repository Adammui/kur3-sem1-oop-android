package rudenia.fit.bstu.oop5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_COST;
import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_ING;
import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_NAME;
import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_RECEPT;
import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_SELECTION;
import static rudenia.fit.bstu.oop5.DatabaseHelper.COLUMN_TIME;
import static rudenia.fit.bstu.oop5.DatabaseHelper.TABLE;

public class MainActivity extends AppCompatActivity  {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    private ArrayAdapter<User> adapter;
    private List<User> users;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList =  findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddItem.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, COLUMN_COST};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
    }


    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this,AddItem.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings :
                Toast.makeText(this, "Made by Pavel Rudenia |  @29.06.2021", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add :
                Intent intent = new Intent(this, AddItem.class);
                startActivity(intent);
            case R.id.update :
               // super.onCreate(Bundle.EMPTY);


        }

        
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  }