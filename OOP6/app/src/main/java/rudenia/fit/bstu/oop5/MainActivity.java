package rudenia.fit.bstu.oop5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements fragment_list.OnFragmentSendDataListener {

    private ArrayAdapter<User> adapter;
    private List<User> users;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onSendData(String selectedItem) {
        fragment_detail fragment = (fragment_detail) getSupportFragmentManager() ///
                .findFragmentById(R.id.detailFragment);
        if (fragment != null)
            fragment.setSelectedItem(selectedItem);
    }

    public void onClick(View view) {
        int activePosition = 0; // первый элемент списка
        listView.performItemClick(listView.getAdapter().
                getView(activePosition, null, null), activePosition, listView.getAdapter().
                getItemId(activePosition));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings :
                Toast.makeText(this, "Lab 6", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add :
                Intent intent = new Intent(this, AddItem.class);
                startActivity(intent);


            case R.id.update :
                listView = (ListView) findViewById(R.id.countriesList);
                users = JSONHelper.importFromJSON(this);
                if(users!=null){
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
                    listView.setAdapter(adapter);
                    Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
                }
                return true;

        }

        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  }