 package rudenia.fit.bstu.oop5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.android.material.internal.ContextUtils.getActivity;

 public class AddItem extends AppCompatActivity {
    String[] aducation = { "Салат", "Десерт", "Напитки","Горячие блюда" , "Закуски"};
    public EditText name1,selected,ing,recept,time,cost;
    public  ImageButton image;
    static final int GALLERY_REQUEST = 1;


    private ArrayAdapter<User> adapter;
    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        users = new ArrayList<User>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);


        /*ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });*/



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aducation);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);



    }
    private Object getImageUri(Context applicationContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(AddItem.this.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        /*Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageButton);*/

       /* switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }*/

    }
    public void write(View view) {
        name1 = findViewById(R.id.editTextTextPersonName);
        ing =findViewById(R.id.editTextTextMultiLine);
        recept = findViewById(R.id.editTextTextMultiLine2);
        time =findViewById(R.id.editTextTextPersonName2);
        cost = findViewById(R.id.editTextNumber);
        /*image= findViewById(R.id.imageButton);*/
        Spinner spinner = findViewById(R.id.spinner);
        String Name = name1.getText().toString();
        String Ing = ing.getText().toString();
        String Recept = recept.getText().toString();
        String Time = time.getText().toString();
        String Cost = cost.getText().toString();
/*        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();*/
       

       /* image.dishPictureString = String.valueOf((Uri) getImageUri(AddItem.this, bitmap));
*/
        String selected = spinner.getSelectedItem().toString();
        /*String myTxt = Name + "\n" + selected +"\n" + Ing + "\n" + Recept + "\n" + Time + "\n" + Cost + "\n" + bitmap   ;  */

        String myTxt = Name + "\n" + selected +"\n" + Ing + "\n" + Recept + "\n" + Time + "\n" + Cost + "\n"    ;
        User user = new User(myTxt);
        users.add(user);
        adapter.notifyDataSetChanged();
        name1.setText("");
        ing.setText("");
        recept.setText("");
        time.setText("");
        cost.setText("");
        Dialog();
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
     //TODO диалоговое окно
     public void Dialog(){
         AlertDialog.Builder b = new AlertDialog.Builder(this);
         b.setTitle("Вы успешно создали блюдо").setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

             }
         });
         AlertDialog ad = b.create();
         ad.show();


     }

     public void auto(View view){
         name1 = findViewById(R.id.editTextTextPersonName);
         ing =findViewById(R.id.editTextTextMultiLine);
         recept = findViewById(R.id.editTextTextMultiLine2);
         time =findViewById(R.id.editTextTextPersonName2);
         cost = findViewById(R.id.editTextNumber);
         name1.setText("Цезарь");
         ing.setText("Помидоры,Курица,Сыр,Салат Айсберг,Масло,Чеснок,Соль,Перец");
         recept.setText(" Пробить блендером до полной однородности..."
                 );
         time.setText("15 мин.");
         cost.setText("10");
     }
}