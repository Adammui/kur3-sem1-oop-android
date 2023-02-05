package rudenia.fit.bstu.oop5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Info extends AppCompatActivity {
    public TextView InfoAll;
    private ArrayAdapter<User> adapter;
    private List<User> users;

    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        InfoAll = findViewById(R.id.editTextTextMultiLine3);


        users = new ArrayList<User>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {

            InfoAll.setText(arguments.get("info1").toString());

        }
    }
        public void info(View view){
            String name = InfoAll.getText().toString();
            User user = new User(name);
            users.add(user);
            adapter.notifyDataSetChanged();
            boolean result = JSONHelper.exportToJSON(this, users);
            if(result){
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
            }
            Dialog();
        }

    //TODO диалоговое окно
    public void Dialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Вы успешно изменили  блюдо").setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = b.create();
        ad.show();


    }
   /* public void open(View view){
        users = JSONHelper.importFromJSON(this);
        if(users!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }*/
    }
