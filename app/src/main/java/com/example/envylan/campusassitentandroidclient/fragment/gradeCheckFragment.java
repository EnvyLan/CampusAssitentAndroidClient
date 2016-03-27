package com.example.envylan.campusassitentandroidclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.envylan.campusassitentandroidclient.R;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;


/* 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |)                         佛曰: 
                  O\  =  /O                              写字楼里写字间，写字间里程序员；  
               ____/`---'\____                           程序人员写程序，又拿程序换酒钱�?   
             .'  \\|     |//  `.                         酒醒只在网上坐，酒醉还来网下眠；    
            /  \\|||  :  |||//  \                        酒醉酒醒日复日，网上网下年复�?
           /  _||||| -:- |||||-  \ 						  但愿老死电脑间，不愿鞠躬老板前；
           |   | \\\  -  /// |   |                       奔驰宝马贵�?趣，公交自行程序员�?
           | \_|  ''\---/''  |   | 					           别人笑我忒疯癫，我笑自己命太贱；
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
@version 2016-3-5 下午6:49:54
 */

public class gradeCheckFragment extends Fragment {
    private WebView mWebview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grade_check, null);
        mWebview = (WebView) v.findViewById(R.id.gradeList_webview);
        SharedPreferences pref = getActivity().getSharedPreferences("jwxt", Context.MODE_PRIVATE);
        String stuId = pref.getString("jwxtStuId", "31207311");
        String token = pref.getString("jwxtToken", "token");
        String url = "http://192.168.1.102:5000/api/v1.0/getGradeList";
        final JSONObject postDate = new JSONObject();
        try {
            postDate.put("stuId", stuId);
            postDate.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.postUrl(url, EncodingUtils.getBytes(postDate.toString(), "BASE64"));
                return true;
            }
        });
        mWebview.postUrl(url, EncodingUtils.getBytes(postDate.toString(), "BASE64"));
        return v;
    }
}
