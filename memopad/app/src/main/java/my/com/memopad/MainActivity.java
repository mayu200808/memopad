package my.com.memopad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private SearchView searchView;
    private Button button;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button3);
        button.setOnClickListener(new OnClickListener());
        init();
        listView.setTextFilterEnabled(true);
        //搜索框搜索
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }

        });
        //长按删除事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = arrayAdapter.getItem(position);
                arrayAdapter.remove(index);
                List<String> stringList = new ArrayList<>();
                FileInputStream input = null;
                BufferedReader reader = null;

                try {
                    input = openFileInput("beiwanglu");
                    reader = new BufferedReader(new InputStreamReader(input));
                    String line = null;
                    while ((line = reader.readLine()) != null){
                        stringList.add(line);
                    }
                    for (String str : stringList){
                        if (index.equals(str)){
                            stringList.remove(str);
                        }
                    }
                    again(stringList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        reader.close();
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    //重新写入数据
    private void again(List<String> list){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("beiwanglu",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            for (String str : list){
                writer.write(str);
                writer.newLine();
            }
        } catch (Exception e) {
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

    //初始化加载数据
    private void init(){
        FileOutputStream out = null;
        FileInputStream input = null;
        BufferedReader reader = null;
        try {
            out = openFileOutput("beiwanglu",Context.MODE_APPEND);
            input = openFileInput("beiwanglu");
            reader = new BufferedReader(new InputStreamReader(input));
            List<String> list = new ArrayList<String>();
            String line = null;
            while ( (line = reader.readLine()) != null){
                list.add(line);
            }
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(arrayAdapter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //新建按钮单击事件
   public class OnClickListener implements View.OnClickListener{
       @Override
       public void onClick(View v) {
           Intent i = new Intent(MainActivity.this,Main2Activity.class);
           startActivity(i);
       }
   }
}
