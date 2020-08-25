package com.sample.todoapp;


import com.sample.todoapp.models.Todo;
import com.sample.todoapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * An interface to call and interact with the RESTful API.
 */
public interface JsonPlaceHolderApi {

    @GET("users/{id}/")
    Call<User> getUser(@Path("id") int userId);

    @GET("users/{username}&{password}/")
    Call<User> getUser(@Path("username") String username, @Path("password") String password);

    @GET("users/{id}/todos")
    Call<List<Todo>> getTodos(@Path("id") int userId);

    @GET("users/")
    Call<List<User>> getUsers();

    @POST("users/")
    Call<User> creatUser(@Body User user);

    @POST("users/{userid}/todos")
    Call<Todo> creatTodo(@Path("userid") int userID,@Body Todo todo);

    @DELETE("users/{userid}/todos/{todoid}")
    Call<Void> deleteTodo(@Path("userid") int userId, @Path("todoId") int todoId);

}
