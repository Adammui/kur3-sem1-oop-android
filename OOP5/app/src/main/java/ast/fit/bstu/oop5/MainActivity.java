package ast.fit.bstu.oop5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private String path;
    ArrayList<String> Items = null;
    ArrayList<String> Pics=null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path=super.getFilesDir()+"lab5-main.json";
        listView = findViewById(R.id.list);
        registerForContextMenu(listView);

        /*
        //final ArrayAdapter<String> adapter;
        //adapter = new ArrayAdapter<>(this, R.layout.list_item, Items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("item", strText);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String[] temps = new String[0];
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                try {
                    File file = new File(path);
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    String line = reader.readLine();
                    int i = 0;
                    while (line != null) {
                        temps = line.split("[;]");
                        if(info.position==i) {
                            Toast.makeText(this, i+"position", Toast.LENGTH_LONG).show();
                            break;
                        }
                        line = reader.readLine();
                        i += 1;
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, info.position+"edit", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("mode", "edit");
                intent.putExtra("item", temps[1]);
                intent.putExtra("itemextra", temps[2]);
                intent.putExtra("itemfio", temps[3]);
                intent.putExtra("pic", temps[0]);
                intent.putExtra("place", temps[4]);
                intent.putExtra("date", temps[5]);
                startActivity(intent);
                //editItem(info.position); // метод, выполняющий действие при редактировании пункта меню
                return true;
            case R.id.del:
                File file = new File(path);
                AlertDialog.Builder b= new AlertDialog.Builder(this);
                b.setTitle("Точно удалить?" ).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            delet(file, info.position);
                            Log.d("LOG","Удалено");
                            onStart();
                        }
                        catch(IOException e) {
                            Log.d("LOG","Ошибка"+e.getMessage());
                        }
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                } );
                AlertDialog ad= b.create();
                ad.show();
                Toast.makeText(this, info.position+"delete", Toast.LENGTH_LONG).show();
                //deleteItem(info.position); //метод, выполняющий действие при удалении пункта меню
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Items = new ArrayList<>();
        Pics = new ArrayList<>();
        MyAdapter adapt = new MyAdapter(this, Items, Pics);
        listView.setAdapter(adapt);

        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                String[] temps = new String[0];
                temps = line.split("[;]");
                Pics.add(i, temps[0] );
                Items.add(i, temps[1]);
                line = reader.readLine();
                i += 1;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(MainActivity.this, Items.toString(), Toast.LENGTH_LONG).show();
        Log.d("","start");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            case R.id.sort:
                onStart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void delet(final File file, final int lineIndex) throws IOException{
        final List<String> lines = new LinkedList<>();
        final Scanner reader = new Scanner(new FileInputStream(file), "UTF-8");
        while(reader.hasNextLine())
            lines.add(reader.nextLine());
        reader.close();
        if (lineIndex < 0 || lineIndex >= lines.size()) throw new AssertionError();
        lines.remove(lineIndex);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for(final String line : lines)
            writer.write(line+"\n");
        writer.flush();
        writer.close();
    }
}