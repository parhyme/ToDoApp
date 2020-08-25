package com.sample.todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sample.todoapp.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class BroadcastRv extends BroadcastReceiver {
    List<Todo> todos;

    @Override
    public void onReceive(Context context, Intent intent) {
        todos = new ArrayList<>();
        Bundle valueFromService = intent.getBundleExtra("todos");
        if (valueFromService != null) {
            todos = valueFromService.getParcelableArrayList("todos");
        }
        if (todos != null) {
            Log.w("haha", todos.get(0).getText());
        }
    }
}
