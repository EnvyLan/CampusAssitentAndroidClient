package com.example.envylan.campusassitentandroidclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.envylan.campusassitentandroidclient.R;


/* 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |)                         佛曰: 
                  O\  =  /O                              写字楼里写字间，写字间里程序员；  
               ____/`---'\____                           程序人员写程序，又拿程序换酒钱�?   
             .'  \\|     |//  `.                         酒醒只在网上坐，酒醉还来网下眠；    
            /  \\|||  :  |||//  \                        酒醉酒醒日复日，网上网下年复�?
           /  _||||| -:- |||||-  \ 						 但愿老死电脑间，不愿鞠躬老板前；
           |   | \\\  -  /// |   |                       奔驰宝马贵�?趣，公交自行程序员�?
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
@version 2016-3-5 下午6:50:05
 */

public class bookSearchFragment extends Fragment {
    private WebView mWebView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_search, null);
        mWebView = (WebView) v.findViewById(R.id.book_search_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("http://m.5read.com/5613?backurl=http%3A%2F%2Fmc.m.5read.com%2Fweixin%2FuserBind1_viewForwordCookie.jspx%3FrealUrl%3Dhttp%253A%252F%252Fms.zucc.edu.cn%253A8080%252Fsms%252Fopac%252Fsearch%252FshowiphoneSearch.action%26ubId%3D23620");
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK  && mWebView.canGoBack()){
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

}
