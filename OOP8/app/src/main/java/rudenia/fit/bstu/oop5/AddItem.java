 package rudenia.fit.bstu.oop5;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Spinner;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



 public class AddItem extends AppCompatActivity {
    String[] aducation = { "Салат", "Десерт", "Напитки","Горячие блюда" , "Закуски"};
    public EditText name,selected,ing,recept,time,cost;
    public Button delButton,saveButton,button2;

//TODO бд
DatabaseHelper sqlHelper;
     SQLiteDatabase db;
     Cursor userCursor;
     long userId=0;


    private ArrayAdapter<User> adapter;
    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        button2 = findViewById(R.id.button2);
        name = findViewById(R.id.editTextTextPersonName);
        ing =findViewById(R.id.editTextTextMultiLine);
        recept = findViewById(R.id.editTextTextMultiLine2);
        time =findViewById(R.id.editTextTextPersonName2);
        cost = findViewById(R.id.editTextNumber);

        users = new ArrayList<User>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);






        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            button2.setVisibility(View.GONE);
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            name.setText(userCursor.getString(1));

            ing.setText(userCursor.getString(3));
            recept.setText(userCursor.getString(4));
            time.setText(String.valueOf(userCursor.getInt(5)));
            cost.setText(String.valueOf(userCursor.getInt(6))+"$");
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);

        }






    }
     public void save(View view){

         ContentValues cv = new ContentValues();

         cv.put(DatabaseHelper.COLUMN_NAME, name.getText().toString());

         cv.put(DatabaseHelper.COLUMN_ING, ing.getText().toString());
         cv.put(DatabaseHelper.COLUMN_RECEPT, recept.getText().toString());
         cv.put(DatabaseHelper.COLUMN_TIME, Integer.parseInt(time.getText().toString()));
         cv.put(DatabaseHelper.COLUMN_COST, Integer.parseInt(cost.getText().toString())+"$");

         if (userId > 0) {
             db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(userId), null);
             Toast.makeText(this, "Блюдо обновлено", Toast.LENGTH_SHORT).show();
         } else {
             db.insert(DatabaseHelper.TABLE, null, cv);
             Toast.makeText(this, "Блюдо добавлено", Toast.LENGTH_SHORT).show();
         }

         goHome();
     }
     public void delete(View view){
         db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
         goHome();
     }
     private void goHome(){
         // закрываем подключение
         db.close();
         // переход к главной activity
         Intent intent = new Intent(this, MainActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
         startActivity(intent);
     }



    /*public void write(View view) {
        name1 = findViewById(R.id.editTextTextPersonName);
        ing =findViewById(R.id.editTextTextMultiLine);
        recept = findViewById(R.id.editTextTextMultiLine2);
        time =findViewById(R.id.editTextTextPersonName2);
        cost = findViewById(R.id.editTextNumber);
        Spinner spinner = findViewById(R.id.spinner);
        String Name = name1.getText().toString();
        String Ing = ing.getText().toString();
        String Recept = recept.getText().toString();
        String Time = time.getText().toString();
        String Cost = cost.getText().toString();


        String selected = spinner.getSelectedItem().toString();


        String myTxt = Name + "\n" + selected +"\n" + Ing + "\n" + Recept + "\n" + Time + "\n" + Cost + "\n"    ;
        User user = new User(myTxt);
        users.add(user);
        adapter.notifyDataSetChanged();
        name1.setText("");
        ing.setText("");
        recept.setText("");
        time.setText("");
        cost.setText("");
        Toast toast = Toast.makeText(this, "Блюдо добавлено", Toast.LENGTH_LONG);
        toast.show();
        boolean result = JSONHelper.exportToJSON(this, users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }

    }
*/

     public void auto(View view){
         name = findViewById(R.id.editTextTextPersonName);
         ing =findViewById(R.id.editTextTextMultiLine);
         recept = findViewById(R.id.editTextTextMultiLine2);
         time =findViewById(R.id.editTextTextPersonName2);
         cost = findViewById(R.id.editTextNumber);
         name.setText("Цезарь");
         ing.setText("Помидоры,Курица,Сыр,Салат Айсберг,Масло,Чеснок,Соль,Перец");
         recept.setText(" Пробить блендером до полной однородности..."
                 );
         time.setText("15");
         cost.setText("10");
     }
}