package ast.fit.bstu.oop2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SeekBar bar= findViewById(R.id.seekBar);
        TextView t= findViewById(R.id.texthint);
        if (bar.getProgress()==0) t.setText("Сидячий образ жизни, малая физическая активность");
        if (bar.getProgress()==1) t.setText("Умеренная активность (легкие физические нагрузки либо занятия 1-3 раз в неделю)");
        if (bar.getProgress()==2) t.setText("Средняя активность (занятия 3-5 раз в неделю)");
        if (bar.getProgress()==3) t.setText("Активные тренировки (интенсивные нагрузки, занятия 6-7 раз в неделю)");
        if (bar.getProgress()==4) t.setText("Спортсмены, выполняющие сходные нагрузки (6-7 раз в неделю)");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    public void count(View view) {

        int pol = 0;
        double AMR = 0.0;
        double result;
        int a = 52;
        int r;
        //TextView res= findViewById(R.id.textres);
        //TextView poltext= findViewById(R.id.textView);
        SeekBar bar = findViewById(R.id.seekBar);
        EditText weight = findViewById(R.id.editweightr);
        EditText height = findViewById(R.id.editheight);
        EditText age = findViewById(R.id.birth);
        RadioButton M = findViewById(R.id.radioM);
        RadioButton F = findViewById(R.id.radioF);

        if (bar.getProgress() == 0) AMR = 1.2;
        if (bar.getProgress() == 1) AMR = 1.375;
        if (bar.getProgress() == 2) AMR = 1.55;
        if (bar.getProgress() == 3) AMR = 1.725;
        if (bar.getProgress() == 4) AMR = 1.9;

        try {
            if (F.isChecked()) {
                pol = 1;
                result = (655.0955 + (9.5634 * Integer.parseInt(weight.getText().toString())) + (1.8496 * Integer.parseInt(height.getText().toString())) - (4.6756 * Integer.parseInt(age.getText().toString()))) * AMR;
                String result1 = String.format("%.0f", result);
                r = (int) result / a;
                // message
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Результат");
                builder.setMessage("Ваша дневная норма : " + result1 + " ккал в день. Это около " + r + " яблок!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            if (M.isChecked()) {
                pol = 1;
                result = (66.4730 + (13.7516 * Integer.parseInt(weight.getText().toString())) + (5.0033 * Integer.parseInt(height.getText().toString())) - (6.7550 * Integer.parseInt(age.getText().toString()))) * AMR;
                String result1 = String.format("%.0f", result);
                r = (int) result / a;
                // message
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Результат");
                builder.setMessage("Ваша дневная норма : " + result1 + " ккал в день. Это около " + r + " яблок!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            if (pol == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Введенные данные некорректны. Укажите пол", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        catch(Exception e)
            {
                if( age.getText().toString().isEmpty() ){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введенные данные некорректны. Введите возраст" +
                                    "" +
                                    "", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if(weight.getText().toString().isEmpty())
                {Toast toast = Toast.makeText(getApplicationContext(),
                        "Введенные данные некорректны. Введите вес.", Toast.LENGTH_SHORT);
                    toast.show();}
                if(weight.getText().toString().isEmpty())
                {Toast toast = Toast.makeText(getApplicationContext(),
                        "Введенные данные некорректны. Введите рост.", Toast.LENGTH_SHORT);
                    toast.show();}
            }

    }
}