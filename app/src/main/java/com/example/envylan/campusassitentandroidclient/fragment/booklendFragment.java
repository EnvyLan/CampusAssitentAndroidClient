package com.example.envylan.campusassitentandroidclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.envylan.campusassitentandroidclient.R;

;import org.apache.http.HttpEntity;
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
import java.io.UnsupportedEncodingException;

/* 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |)                         佛曰: 
                  O\  =  /O                              写字楼里写字间，写字间里程序员；  
               ____/`---'\____                           程序人员写程序，又拿程序换酒钱?
             .'  \\|     |//  `.                         酒醒只在网上坐，酒醉还来网下眠；    
            /  \\|||  :  |||//  \                        酒醉酒醒日复日，网上网下年复?
           /  _||||| -:- |||||-  \ 						  但愿老死电脑间，不愿鞠躬老板前；
           |   | \\\  -  /// |   |                       奔驰宝马贵?趣，公交自行程序员?
           | \_|  ''\---/''  |   | 					     别人笑我忒疯癫，我笑自己命太贱；
           \  .-\__  `-`  ___/-. /                       不见满街漂亮妹，哪个归得程序员？ 
         ___`. .'  /--.--\  `. . __ 
      ."" '<  `.___\_<|>_/___.'  >'"". 
     | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
     \  \ `-.   \_ __\ /__ _/   .-` /  / 
======`-.____`-.___\_____/___.-`____.-'====== 
                   `=---=' 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
                                 佛祖保佑       永无BUG 
               本工程已经经过佛祖开光处理，绝无bug
@author EnvyLan
@version 2016-3-5 下午6:49:59
 */

public class booklendFragment extends Fragment {

    private WebView mWebView;
    private String url;
    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    mWebView.loadUrl(url);
                    break;
                case 2:
                    Toast.makeText(getActivity(), "服务器错误，请稍后再试", Toast.LENGTH_LONG)
                            .show();
                    break;
                default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_lend, null);
        SharedPreferences pref = getActivity().getSharedPreferences("uniteAccount", Context.MODE_PRIVATE);
        String stuId = pref.getString("uniteStuId", "-1");
        String token = pref.getString("uniteToken", "-1");
        //url = "http://ms.zucc.edu.cn:8080/sms/opac/user/lendStatus.action?xc=3&sn=223B9A1BD48F7D670C57521A003A1BE401D1E0AB0CC6C4C843C442C345F3D32F64718B7D5FB4C4631017B3185E0DC9A510D326E9F49A0D984E6D96422081B572750D74CB32C089EA";
        getUrl(stuId, token);
        mWebView = (WebView) v.findViewById(R.id.book_lend_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if(token.equals("-1")){
            Toast.makeText(getActivity(), "请先更新您的统一账户", Toast.LENGTH_LONG)
                    .show();
        }
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

    public void getUrl(final String stuId, final String token){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpPost postRequest = new HttpPost("http://115.159.104.179/api/v1.0/getRecordURL");
                JSONObject myJson = new JSONObject();
                try {
                    myJson.put("stuId", stuId);
                    myJson.put("token", token);
                    HttpClient client = new DefaultHttpClient();
                    StringEntity entity = new StringEntity(myJson.toString(), "utf-8");
                    postRequest.setEntity(entity);
                    HttpResponse reponse = client.execute(postRequest);
                    HttpEntity reponseEntity = reponse.getEntity();
                    String mes = EntityUtils.toString(reponseEntity, "utf-8");
                    Log.d("mes", mes);
                    JSONObject js = new JSONObject(mes);
                    Log.d("js", js.get("status").toString());
                    Message message = new Message();
                    if (js.get("status").toString().equals("200")){
                        url = js.get("recordURL").toString();
                        message.what = 1;
                        mhandle.sendMessage(message);
                    }else {
                        message.what = 2;
                        mhandle.sendMessage(message);
                    }
                } catch (JSONException e) {
                    Message message = new Message();
                    message.what = 2;
                    mhandle.sendMessage(message);
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
}

