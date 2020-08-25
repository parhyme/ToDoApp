package com.sample.todoapp;

import android.view.View;
import android.widget.TextView;

import com.leodroidcoder.genericadapter.BaseViewHolder;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;
import com.sample.todoapp.models.Todo;

public class TodoViewHolder extends BaseViewHolder<Todo, OnRecyclerItemClickListener> {

    private TextView nameTv;

    public TodoViewHolder(View itemView, OnRecyclerItemClickListener listener) {
        super(itemView, listener);
        // initialize view and set click listener
        nameTv = itemView.findViewById(R.id.todo_text);
        if (listener != null) {
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }
    }

    @Override
    public void onBind(Todo item) {
        // bind data to the views
        nameTv.setText(item.getText());

    }

}