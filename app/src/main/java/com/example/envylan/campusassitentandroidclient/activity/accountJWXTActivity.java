package com.example.envylan.campusassitentandroidclient.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.envylan.campusassitentandroidclient.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
public class accountJWXTActivity extends Activity {

    private Toolbar mToolbar;
    private Button mButton;
    private EditText mIdEt;
    private EditText mPwdEt;
    private MaterialDialog meterialDialog;
    private ImageView imageView;
    private Bitmap bitmap = null;
    private static final int UPDATE_VIEW = 1;
    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_VIEW:
                    imageView.setImageBitmap(bitmap);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail);
        findViewId();
        mToolbar.setTitle("hello");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIdEt.getText().equals("") || mPwdEt.getText().equals("")) {
                    Toast.makeText(accountJWXTActivity.this, "学号和密码不能为空", Toast.LENGTH_LONG)
                            .show();
                } else {
                    showMeterialDialog();
                }
            }
        });
   }

    private void showMeterialDialog() {
        meterialDialog = new MaterialDialog(accountJWXTActivity.this );
        View view = LayoutInflater.from(accountJWXTActivity.this)
                .inflate(R.layout.dialog_item, null);
        imageView = (ImageView) view.findViewById(R.id.checkcode_im);
        getCheckCode();
        final EditText et = (EditText) view.findViewById(R.id.checkcode_text);
        meterialDialog.setCanceledOnTouchOutside(true)
                .setView(view)
                .setPositiveButton("Send", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (et.getText().equals("")) {
                            Toast.makeText(accountJWXTActivity.this, "验证码不能为空", Toast.LENGTH_LONG)
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
                HttpPost postRequest = new HttpPost("http://192.168.1.104:5000/api/v1.0/verify_user");
                JSONObject myJson = new JSONObject();
                try {
                    myJson.put("stuId", stuId);
                    myJson.put("Pwd", pwd);
                    myJson.put("yzm", checkCode);
                    myJson.put("token","");
                    StringEntity entity = new StringEntity(myJson.toString(), "utf-8");
                    HttpClient client = new DefaultHttpClient();
                    postRequest.setEntity(entity);
                    HttpResponse reponse = client.execute(postRequest);
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

    }
}
