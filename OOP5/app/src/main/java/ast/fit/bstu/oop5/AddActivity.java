package ast.fit.bstu.oop5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AddActivity extends AppCompatActivity {

    private final int Pick_image = 1;
    private ImageView imageView;
    private String path, pic;
    TextView textcalendar;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        path = super.getFilesDir() + "lab5-main.json";
        imageView = findViewById(R.id.imageView);
        textcalendar=findViewById(R.id.textViewcalendar);
        TextView item = findViewById(R.id.item);
        TextView comment = findViewById(R.id.comments);
        TextView fio = findViewById(R.id.fio);
        Spinner sp = findViewById(R.id.spinner);
        Button Done = findViewById(R.id.done);
        Button PickImage = findViewById(R.id.button);
        Button data =  findViewById(R.id.datapicker);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            finish();
        }
        else {
            Bundle arg = getIntent().getExtras();
            if (arg!=null) {
                if(arg.getString("mode").equals("normal")) {
                    Done.setVisibility(View.INVISIBLE);
                    PickImage.setVisibility(View.INVISIBLE);
                }
                item.setText(arg.getString("item"));
                imageView.setImageURI(Uri.parse(arg.getString("pic")));
                comment.setText(arg.getString("itemextra"));
                fio.setText(arg.getString("itemfio"));
                textcalendar.setText(arg.getString("date"));
                sp.setSelection(arg.getInt("place")); // todo

            }
            DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textcalendar.setText(dateAndTime.get(Calendar.DAY_OF_MONTH)+"."
                            +dateAndTime.get(Calendar.MONTH)+"."+dateAndTime.get(Calendar.YEAR));
                }
            };
            data.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    new DatePickerDialog(AddActivity.this, d,
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH))
                            .show();
                }
            });
            PickImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, Pick_image);
                }
            });
            Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView item = findViewById(R.id.item);
                    TextView comment = findViewById(R.id.comments);
                    TextView fio = findViewById(R.id.fio);
                    Spinner sp = findViewById(R.id.spinner);

                    if (arg!=null) {
                        if(arg.getString("mode").equals("edit"))
                        {
                            try {
                                File f=new File(path);
                                FileReader fr = new FileReader(f);
                                BufferedReader reader = new BufferedReader(fr);
                                String line = reader.readLine();
                                int i = 0;
                                while (line != null) {
                                    String[] temps = new String[0];
                                    temps = line.split("[;]");
                                    pic=temps[0];
                                    if(arg.getString("item").equals(temps[1]))
                                        break;
                                    line = reader.readLine();
                                    i += 1;
                                }
                                replace(f, i,pic + ";" + item.getText().toString() + ";"
                                        + comment.getText().toString() + ";" + fio.getText().toString()
                                        + ";" + sp.getSelectedItemPosition() + ";"+textcalendar.getText()+";");
                                Toast.makeText(AddActivity.this, "Изменено! ", Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        try { //записать в файл все как в прошлой лабе и с картинкой через точку с апятой
                            createSave(pic + ";" + item.getText().toString() + ";"
                                    + comment.getText().toString() + ";"+ fio.getText().toString()
                                    + ";" + sp.getSelectedItemPosition() + ";"+textcalendar.getText()+";", path);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        Toast.makeText(AddActivity.this, "Записано! ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d("ф", String.valueOf(imageReturnedIntent.getData()));
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        pic= imageReturnedIntent.getData().toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    ///
    public void createSave(String str,String path) throws IOException {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while(line!=null) {
                line = reader.readLine();
            }
            PrintWriter prWr = new PrintWriter(new BufferedWriter(new FileWriter( path, true)));
            prWr.println(str);
            prWr.close();
        } catch(IOException ex) {
            Log.d("",""+ex.getMessage());
        }
    }

    public void replace(final File file, final int lineIndex, String str) throws IOException{
        final List<String> lines = new LinkedList<>();
        final Scanner reader = new Scanner(new FileInputStream(file), "UTF-8");
        while(reader.hasNextLine())
            lines.add(reader.nextLine());
        reader.close();
        assert lineIndex >= 0 && lineIndex <= lines.size() - 1;
        lines.remove(lineIndex);
        lines.add(str);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for(final String line : lines)
            writer.write(line+"\n");
        writer.flush();
        writer.close();
    }
}