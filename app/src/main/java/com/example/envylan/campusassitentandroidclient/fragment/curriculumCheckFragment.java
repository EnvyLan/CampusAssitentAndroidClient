package com.example.envylan.campusassitentandroidclient.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.envylan.campusassitentandroidclient.R;
import com.example.envylan.campusassitentandroidclient.models.ClassInfo;
import com.example.envylan.campusassitentandroidclient.views.ScheduleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/* 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |)                         佛曰: 
                  O\  =  /O                              写字楼里写字间，写字间里程序员；  
               ____/`---'\____                           程序人员写程序，又拿程序换酒钱?
             .'  \\|     |//  `.                         酒醒只在网上坐，酒醉还来网下眠；    
            /  \\|||  :  |||//  \                        酒醉酒醒日复日，网上网下年复?
           /  _||||| -:- |||||-  \ 						 但愿老死电脑间，不愿鞠躬老板前；
           |   | \\\  -  /// |   |                       奔驰宝马贵趣，公交自行程序员
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
@version 2016-3-5 下午6:49:47
 */

public class curriculumCheckFragment extends Fragment {
    private List<ClassInfo> classList = new ArrayList<>();
    private ScheduleView scheduleView;
    private String TAG = "课表查询";
    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage is execute");
            scheduleView.setClassList(classList);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.curriculum_check, null);
        scheduleView = (ScheduleView) v.findViewById(R.id.schedu);
        try {
            ObjectInputStream in = new ObjectInputStream(getActivity().openFileInput("classList.dat"));
            classList = (List<ClassInfo>) in.readObject();
            scheduleView.setClassList(classList);
            in.close();
        }catch (FileNotFoundException e){
            checkToken();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return v;
    }

    public void checkToken(){
        SharedPreferences pref = getActivity().getSharedPreferences("jwxt", MODE_PRIVATE);
        String token = pref.getString("jwxtToken", "token");
        if (token.equals("-1")){
            Toast.makeText(getActivity(), "请更新教务系统账户", Toast.LENGTH_LONG)
                    .show();
        }else {
            getCurriculum(token, pref.getString("jwxtStuId", "-1"));
        }
    }

    private void getCurriculum(final String token, final String jwxtStuId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpPost postRequest = new HttpPost("http://115.159.104.179/api/v1.0/getCurriculum");
                JSONObject json = new JSONObject();
                try {
                    json.put("xnd","2013-2014");
                    json.put("xqd","2");
                    json.put("token", token);
                    json.put("stuId", jwxtStuId);
                    StringEntity entity = new StringEntity(json.toString(), "utf-8");
                    HttpClient client = new DefaultHttpClient();
                    postRequest.setEntity(entity);
                    HttpResponse reponse = client.execute(postRequest);
                    HttpEntity entity1 = reponse.getEntity();
                    String msg = EntityUtils.toString(entity1, "utf-8");
                    JSONObject o = new JSONObject(msg);
                    if(o.get("status").toString().equals("100")){
                        Message message = new Message();
                        classList = new Gson().fromJson(o.getString("list"), new TypeToken< List<ClassInfo> >(){}.getType());
                        mhandler.sendMessage(message);
//                      ObjectOutputStream out = new ObjectOutputStream(getActivity().openFileOutput("classList.dat", MODE_PRIVATE));
//                      out.writeObject(classList);
//                      out.close();
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
}
