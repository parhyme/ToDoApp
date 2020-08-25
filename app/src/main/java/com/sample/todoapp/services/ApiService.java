package com.sample.todoapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sample.todoapp.JsonPlaceHolderApi;
import com.sample.todoapp.MainActivity;
import com.sample.todoapp.RegisterActivity;
import com.sample.todoapp.SignInActivity;
import com.sample.todoapp.models.Todo;
import com.sample.todoapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A service used to send the mobile information
 * to the RESTful API
 */
public class ApiService extends Service {

//    public static String CONTEXT = "context";
    public static String TASK_STRING="";
    private String IMEINumber;
    private String buildModel;
    private String buildVersion;
    private int batteryLevel;
    private String id;
    boolean loginVerification = false;
    private List<Todo> todos = new ArrayList<Todo>();

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private User user;

    Call<User> call;
    Context context = this;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getBaseContext(), "Service Created", Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        retIntent = new Intent(I);
        String username;
        String password;
        String text;
        int userId;

        if(intent.getStringExtra(TASK_STRING)!=null) {
            TASK_STRING = intent.getStringExtra(TASK_STRING);
        }


        switch (TASK_STRING){
            case "login":
                username = intent.getStringExtra(SignInActivity.USERNAME);
                password = intent.getStringExtra(SignInActivity.PASSWORD);
                getUserByUsername(username, password);

                break;
            case "register":
                username = intent.getStringExtra(RegisterActivity.USERNAME);
                password = intent.getStringExtra(RegisterActivity.PASSWORD);
                createUser(new User(username, password));

                break;
            case "createtodo":
                text = intent.getStringExtra(MainActivity.TEXT);
                userId = intent.getIntExtra(MainActivity.USER_ID,2);
                createTodo(userId, new Todo(text, userId));

                break;
            case "deletetodo":

                break;
            case "gettodos":
                userId = intent.getIntExtra(MainActivity.USER_ID,0);
                getTodos(userId);

                break;
            default:
                break;
        }
//        IMEINumber= intent.getStringExtra(MainActivity.ANDROID_ID);
//        batteryLevel = intent.getIntExtra(MainActivity.BATTERY_LEVEL,0);
//        buildModel = intent.getStringExtra(MainActivity.MODEL_NAME);
//        buildVersion = intent.getStringExtra(MainActivity.ANDROID_VERSION);


//        user = new User();
//        call = jsonPlaceHolderApi.creatUser(user);

//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                User mfResponse = response.body();
//
//                if(response.code()==201 && mfResponse != null) {
//                    Toast.makeText(getBaseContext(), "Information was sent Successfully.(ANDROID ID: " +
//                            mfResponse.getAndroidId() , Toast.LENGTH_LONG).show();
//                    stopSelf(startId);
//                }
//                else {
//                    Toast.makeText(getBaseContext(), "There was a problem sending info", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(getBaseContext(),"SEND FAILED "+ t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

    public class mBinder extends Binder{
        public ApiService getApiService(){
            return ApiService.this;
        }
    }

    public List<Todo> getTodosForActivity() {
        return todos;
    }

    private void getTodos(int userId){
        Call<List<Todo>> call = jsonPlaceHolderApi.getTodos(userId);

        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<Todo> todosResponse = response.body();

                if(todosResponse!=null) {
//                    todos = todosResponse;
//                    for(int i=0; todosResponse.size()>=i;i++) {
//                        for (Todo t : todosResponse) {
//                            todos.add(t);
//                            prctodos[i] = t;
//                            Toast.makeText(context, t.getText(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    int ServiceReturnIntent = 3123;
                    Intent retInt = new Intent("return");
                    ArrayList<Todo> todoArrayList = new ArrayList<>(todosResponse);
                    Bundle prctodos = new Bundle();
                    prctodos.putParcelableArrayList("todos",todoArrayList);
                    retInt.putExtra("todos",prctodos);
                    sendBroadcast(retInt);
                }else {
                    Toast.makeText(context, "ARRAY EMPTY",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(context,"SERVER FAILED: "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getUsers(){
        Call<List<User>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<User> users = response.body();


                if(users!=null) {
                    for(User u: users){
                        Toast.makeText(context, u.getUsername(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getBaseContext(),"SERVER FAILED: "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserById(int Id){
        Call<User> call = jsonPlaceHolderApi.getUser(Id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                User user = response.body();

                if(user!=null){
                    Toast.makeText(getBaseContext(), "VERIFIED", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "NOT VERIFIED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(),"SERVER FAILED: "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserByUsername(String username,String password){
        Call<User> call = jsonPlaceHolderApi.getUser(username,password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                User user = response.body();

                if (user != null) {
                    if(user.getUsername()!=null) {
                        if(TASK_STRING.equals("login")){
                            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                            preferences.edit().putInt(MainActivity.USER_ID,user.getUserid()).apply();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Log.i("haha", "user id :" + user.getUserid());
                        }
                    }else {
                        Toast.makeText(context, "USER DOESN'T EXIST", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context,"SERVER FAILED: "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createUser(User user){
        Call<User> call = jsonPlaceHolderApi.creatUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getBaseContext(), "USER CREATED", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), "FAILED" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createTodo(int userId, Todo todo){
        Call<Todo> call = jsonPlaceHolderApi.creatTodo(userId, todo);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getBaseContext(), "TODO CREATED", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });
    }

    private void deleteTodo(int userId, int todoId){
        Call<Void> call = jsonPlaceHolderApi.deleteTodo(userId, todoId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public boolean isLoginVerification() {
        return loginVerification;
    }
}
