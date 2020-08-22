package com.sample.todoapp;


import com.sample.todoapp.models.Todo;
import com.sample.todoapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * An interface to call and interact with the RESTful API.
 */
public interface JsonPlaceHolderApi {

    @GET("users")
    Call<List<User>> getUsers();

    @POST("post-user/")
    Call<User> creatUser(@Body User user);

    @POST("post-todo/")
    Call<Todo> creatTodo(@Body Todo todo);
}
