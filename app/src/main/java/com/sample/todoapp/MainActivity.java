package com.sample.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;
import com.sample.todoapp.models.Todo;
import com.sample.todoapp.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  implements OnRecyclerItemClickListener, ServiceConnection {

    public static final String TEXT = "text";
    public static final String USER_ID = "userId";
    int userId;
    List<Todo> todos;
    SimpleAdapter adapter;
    RecyclerView rv;
    EditText todoAddText;
    Button addButton;

    BroadcastRv broadcastRv;
    ApiService service;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.todo_list);
        todoAddText = findViewById(R.id.todoAddText);
        addButton = findViewById(R.id.AddButton);

        SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        userId = preferences.getInt(USER_ID,0);
        Log.i("haha", "user id is :" + userId);

        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.putExtra(TEXT, todoAddText.getText().toString());
        serviceIntent.putExtra(USER_ID, preferences.getInt(USER_ID,2));
        serviceIntent.putExtra(ApiService.TASK_STRING, "gettodos");
        startService(serviceIntent);

        todos = new ArrayList<>();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adapter = new SimpleAdapter(this,this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        adapter.setItems(todos);


    }

    @Override
    public void onItemClick(int position) {
        Todo todo = adapter.getItem(position);
        Toast.makeText(this, todo.getText(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ApiService.mBinder b = (ApiService.mBinder) iBinder;
        service = b.getApiService();
        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        service= null;
    }
}