package com.example.envylan.campusassitentandroidclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.envylan.campusassitentandroidclient.R;


/**
 * Created by EnvyLan on 2016/3/7 0007.
 */
public class accountDetailActivity extends Activity {

    private Toolbar mToolbar;
    private Button mButton;
    private EditText mIdEt;
    private EditText mPwdEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail);
        findViewId();
        mToolbar.setTitle("hello");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(accountDetailActivity.this, "ff", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void findViewId(){
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mButton = (Button) findViewById(R.id.btnUpdate);
        mIdEt = (EditText) findViewById(R.id.stuId);
        mPwdEt = (EditText) findViewById(R.id.pwd);

    }
}
