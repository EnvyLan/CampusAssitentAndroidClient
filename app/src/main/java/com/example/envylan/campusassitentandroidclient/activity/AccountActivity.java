package com.example.envylan.campusassitentandroidclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.envylan.campusassitentandroidclient.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by EnvyLan on 2016/3/7 0007.
 */
public class AccountActivity extends Activity {

    private Toolbar mToolbar;
    private Button mButton;
    private EditText mIdEt;
    private EditText mPwdEt;
    private MaterialDialog meterialDialog;
    private ImageView imageView;
    private Bitmap bitmap = null;
    private static final int UPDATE_VIEW = 1;
    private final String TAG = "TAG";
    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_VIEW:
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    Toast.makeText(AccountActivity.this, "更新账号成功", Toast.LENGTH_LONG)
                                .show();
                    break;
                case 3:
                    Toast.makeText(AccountActivity.this, "登录失败，账号密码错误，或者验证码错误", Toast.LENGTH_LONG)
                            .show();
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("accountacticity", "账号管理acticity ");
        setContentView(R.layout.account_detail);
        Intent intent = getIntent();
        findViewId();
        final String typeIntent = intent.getStringExtra("type");
        mToolbar.setTitle(typeIntent);
        //mToolbar.setTitleTextColor();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIdEt.getText().toString().equals("") || mPwdEt.getText().toString().equals("")) {
                    Toast.makeText(AccountActivity.this, "学号和密码不能为空", Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (typeIntent.equals("教务系统")) {
                        showMeterialDialog();
                    } else {
                        updateUniteAccount();
                    }
                }
            }
        });
   }

    //更新统一账户
    private void updateUniteAccount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpPost postRequest = new HttpPost("http://192.168.1.106:5000/api/v1.0/virfy_unite_user");
                JSONObject myJson = new JSONObject();
                try {
                    myJson.put("stuId", mIdEt.getText().toString());
                    myJson.put("Pwd", mPwdEt.getText().toString());
                    StringEntity entity = new StringEntity(myJson.toString(), "utf-8");
                    HttpClient client = new DefaultHttpClient();
                    postRequest.setEntity(entity);
                    HttpResponse reponse = client.execute(postRequest);
                    HttpEntity reponseEntity = reponse.getEntity();
                    String mes = EntityUtils.toString(reponseEntity, "utf-8");
                    JSONObject js = new JSONObject(mes);
                    Log.d("mes",mes);
                    Log.d("js", js.get("status").toString());
                    Message message = new Message();
                    if(js.get("status").toString().equals("200")){
                        updateLocalUniteAccount(myJson.get("stuId").toString(), myJson.getString("Pwd"), js.get("recordURL").toString(), js.get("token").toString());
                        message.what = 2;
                        mhandle.sendMessage(message);
                    }else {
                        message.what = 3;
                        mhandle.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void updateLocalUniteAccount(String Pwd, String stuId, String recordURL, String token){
        Log.d(TAG, "updateLocalUniteAccount 开始写入");
        SharedPreferences.Editor editor = getSharedPreferences("uniteAccount", MODE_PRIVATE).edit();
        editor.putString("uniteStuId", stuId);
        editor.putString("unitePwd", Pwd);
        editor.putString("recordURL", recordURL);
        editor.putString("uniteToken", token);
        editor.commit();
        Log.d(TAG, "updateLocalUniteAccount 完成写入");
    }

    private void showMeterialDialog() {
        meterialDialog = new MaterialDialog(AccountActivity.this );
        View view = LayoutInflater.from(AccountActivity.this)
                .inflate(R.layout.dialog_item, null);
        imageView = (ImageView) view.findViewById(R.id.checkcode_im);
        getCheckCode();
        final EditText et = (EditText) view.findViewById(R.id.checkcode_text);
        meterialDialog.setCanceledOnTouchOutside(true)
                .setView(view)
                .setPositiveButton("Send", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (et.getText().toString().equals("")) {
                            Toast.makeText(AccountActivity.this, "验证码不能为空", Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            sendStuIdAndPwd(mIdEt.getText().toString(), mPwdEt.getText().toString(), et.getText().toString());
                            meterialDialog.dismiss();
                        }
                    }
                }).show();
    }

    private void sendStuIdAndPwd(final String stuId, final String pwd, final String checkCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //URL url = new URL("http://122.235.99.168/api/v1.0/verify_user");
                HttpPost postRequest = new HttpPost("http://192.168.1.106:5000/api/v1.0/verify_user");
                JSONObject myJson = new JSONObject();
                try {
                    myJson.put("stuId", stuId);
                    myJson.put("Pwd", pwd);
                    myJson.put("yzm", checkCode);
                    myJson.put("token", "");
                    StringEntity entity = new StringEntity(myJson.toString(), "utf-8");
                    HttpClient client = new DefaultHttpClient();
                    postRequest.setEntity(entity);
                    HttpResponse reponse = client.execute(postRequest);
                    HttpEntity entity1 = reponse.getEntity();
                    String msg = EntityUtils.toString(entity1, "utf-8");
                    JSONObject reponseJson = new JSONObject(msg);
                    Message message = new Message();
                    Log.d("reponsejson", msg);
                    if(reponseJson.get("status").toString().equals("100")){
                        updateLocalJWXTAccount(myJson.get("stuId").toString(), myJson.get("Pwd").toString(), reponseJson.get("token").toString(), reponseJson.get("xnd").toString());

                        message.what = 2;
                        mhandle.sendMessage(message);
                    }else{
                        message.what = 3;
                        mhandle.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void updateLocalJWXTAccount(String stuId, String pwd, String token, String xnd) {
        SharedPreferences.Editor editor = getSharedPreferences("jwxt", MODE_PRIVATE).edit();
        editor.putString("jwxtStuId", stuId);
        editor.putString("jwxtPwd", pwd);
        editor.putString("jwxtToken", token);
        editor.putString("xnd", xnd);
        editor.commit();
    }

    private void getCheckCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                returnCheckCodeImage();
                Message message = new Message();
                message.what = UPDATE_VIEW;
                mhandle.sendMessage(message);
            }
        }).start();
    }


    public void returnCheckCodeImage(){
        URL myUrl = null;
        try {
            myUrl =  new URL("http://124.160.104.166/%28d4ccblqij00gaav1om2xai55%29/CheckCode.aspx");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void findViewId(){
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mButton = (Button) findViewById(R.id.btnUpdate);
        mIdEt = (EditText) findViewById(R.id.stuId);
        mPwdEt = (EditText) findViewById(R.id.pwd);
        mIdEt.setText("31207311");
        mPwdEt.setText("hello123");

    }
}
