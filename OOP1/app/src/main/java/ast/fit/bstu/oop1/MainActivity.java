package ast.fit.bstu.oop1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extracted();

        for (int ic = 0; ic < 10; ic++) {
            Log.d("MainActivity", "Counter=" + ic) ;
        }
    }

    private void extracted() {
        textFunction tf= new textFunction();
        TextView nt= findViewById(R.id.newtest);
        nt.setText(tf.getValue());
    }

    //TODO сделать ввод чего-нибудь
    private int io=0;
    public void change(View view) {
        TextView i = findViewById(R.id.but);
        if(io%2==0)
            i.setText("BYE WORLD!");
        else i.setText("HELLO WORLD!");
        io+=1;
    }

    
}