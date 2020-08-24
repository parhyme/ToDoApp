package com.sample.todoapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sample.todoapp.JsonPlaceHolderApi;
import com.sample.todoapp.SignInActivity;
import com.sample.todoapp.models.Todo;
import com.sample.todoapp.models.User;

import java.util.UUID;

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

    public static String TASK_STRING="";
    private String IMEINumber;
    private String buildModel;
    private String buildVersion;
    private int batteryLevel;
    private String id;
    boolean loginVerification = false;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private User user;

    Call<User> call;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TASK_STRING= intent.getStringExtra(TASK_STRING);

        switch (TASK_STRING){
            case "login":
                String username = intent.getStringExtra(SignInActivity.USERNAME);
                String password = intent.getStringExtra(SignInActivity.PASSWORD);
                getUserByUsername(username, password);

                break;
            case "register":

                break;
            case "createtodo":

                break;
            case "deletetodo":

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

    private void getUserById(int Id){
        Call<User> call = jsonPlaceHolderApi.getUser(Id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                User user = response.body();

                if(user!=null) {
                    loginVerification = true;
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
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }

                User user = response.body();

                if(user!=null) {
                    loginVerification = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(),"SERVER FAILED: "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createUser(User user){
        Call<User> call = jsonPlaceHolderApi.creatUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void createTodo(int userId, Todo todo){
        Call<Todo> call = jsonPlaceHolderApi.creatTodo(userId, todo);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {

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
