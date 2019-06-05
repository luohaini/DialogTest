package com.example.artmir;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText edituser;
    private  EditText editpass;
    private TextView misspass;
    private TextView register;
    private  String url="http://172.20.10.5:5000/demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



    }

    public void loginonclick(View view){
        Intent intent = new Intent(MainActivity.this, CompositeActivity.class);
        startActivity(intent);
    }

    public void registeronclick(View view){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        new Thread(){
            @Override
            public void run()
            {
                SendMessage(url,"111","222");
            }
        }.start();
        startActivity(intent);
    }
    /*
    private void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        { ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT); }
        else { openAlbum(); }
    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
    if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK)
    { //获取图片的
         uri uri= data.getData();
         //图片的绝对路径
        image_path = ImageUtils.getRealPathFromUri(this, uri);
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
        photo.setImageBitmap(bitmap);
        file = new File(image_path); //选取完图片后调用上传方法，将图片路径放入参数中
         sendStudentInfoToServer(file); }
    super.onActivityResult(requestCode, resultCode, data);
}

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE); }

    public void messpassdialog(View view){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View v =  getLayoutInflater().inflate(R.layout.passdialoglayout,null);
        builder.setView(v);

        builder.show();
    }*/
    private void SendMessage(String url, final String userName, String passWord) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", userName);
        formBuilder.add("password", passWord);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });
            }
        });

    }
}
