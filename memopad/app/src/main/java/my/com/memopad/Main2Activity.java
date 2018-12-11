package my.com.memopad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button = findViewById(R.id.button2);
        editText = findViewById(R.id.editText4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream out = null;
                BufferedWriter writer = null;
                try {
                    out = openFileOutput("beiwanglu",Context.MODE_APPEND);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                    String date = format.format(new Date(System.currentTimeMillis()));
                    writer.write(editText.getText().toString()+"      "+date);
                    writer.newLine();
                    Intent i = new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(i);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        writer.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
