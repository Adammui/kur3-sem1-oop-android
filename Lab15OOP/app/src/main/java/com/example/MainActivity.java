package com.example;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> titleList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void start(View view)

    {
        ListView l= findViewById(R.id.list_view);
        l.setVisibility(View.VISIBLE);
        mListView = findViewById(R.id.list_view);

        titleList.clear();
        new WebTask().execute("https://www.belstu.by/news.html?list=20&filter=1", ".TitleRef");
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.text_item, titleList);
    }

    public class WebTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg) {
            try
            {
                Document doc = Jsoup.connect(arg[0]).get();
                Elements titleContent = doc.select(arg[1]);

                for (Element contents: titleContent) {
                    titleList.add(contents.text());
                    //todo: тут осталось добавить передачу какой элемент был выбран и вторую страничку с просмотром. потом еще геокодер и мб картинки. а и сортировки на кнопочки
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            mListView.setAdapter(adapter);
        }
    }
    public void stop(View view)

    {
        ListView l= findViewById(R.id.list_view);
        l.setVisibility(View.INVISIBLE);
    }
}