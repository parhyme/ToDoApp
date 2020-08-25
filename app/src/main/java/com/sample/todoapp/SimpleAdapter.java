package com.sample.todoapp;

import android.content.Context;
import android.view.ViewGroup;

import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;
import com.sample.todoapp.models.Todo;

public class SimpleAdapter extends GenericRecyclerViewAdapter<Todo, OnRecyclerItemClickListener, TodoViewHolder> {

    public SimpleAdapter(Context context, OnRecyclerItemClickListener listener) {
        super(context, listener);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoViewHolder(inflate(R.layout.todo_item, parent), getListener());
    }
}