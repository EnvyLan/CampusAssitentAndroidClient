package com.example.envylan.campusassitentandroidclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.envylan.campusassitentandroidclient.R;


/* 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |)                         佛曰: 
                  O\  =  /O                              写字楼里写字间，写字间里程序员；  
               ____/`---'\____                           程序人员写程序，又拿程序换酒钱；
             .'  \\|     |//  `.                         酒醒只在网上坐，酒醉还来网下眠；    
            /  \\|||  :  |||//  \                        酒醉酒醒日复日，网上网下年复；
           /  _||||| -:- |||||-  \ 						 但愿老死电脑间，不愿鞠躬老板前；
           |   | \\\  -  /// |   |                       奔驰宝马贵趣，公交自行程序员；
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
@version 2016-3-5 下午6:50:11
 */

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("tag", "here");
        System.out.print("herez");
        return inflater.inflate(R.layout.settings_layout, null);
    }
}
