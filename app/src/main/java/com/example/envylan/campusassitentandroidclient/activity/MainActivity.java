package com.example.envylan.campusassitentandroidclient.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.envylan.campusassitentandroidclient.R;
import com.example.envylan.campusassitentandroidclient.fragment.SettingsFragment;
import com.example.envylan.campusassitentandroidclient.fragment.accountManageFragment;
import com.example.envylan.campusassitentandroidclient.fragment.bookSearchFragment;
import com.example.envylan.campusassitentandroidclient.fragment.booklendFragment;
import com.example.envylan.campusassitentandroidclient.fragment.curriculumCheckFragment;
import com.example.envylan.campusassitentandroidclient.fragment.gradeCheckFragment;
import com.example.envylan.campusassitentandroidclient.fragment.indexFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager fM = getSupportFragmentManager();
    private Fragment fragment = null;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.tl_custom);
        mToolbar.setTitle("Toolbar");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        fM.beginTransaction()
                .replace(R.id.main_content, new indexFragment())
                .commit();
        String[] values = new String[]{
                "主页",
                "账号管理",
                "课表查询",
                "成绩查询",
                "图书借阅查询",
                "图书查找",
                "设置",
                "Share",
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar ,R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new drawerItemClickListener());
    }

    private class drawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                    fragment = new indexFragment();break;
                case 1:
                    fragment = new accountManageFragment();break;
                case 2:
                    fragment = new curriculumCheckFragment();break;
                case 3:
                    fragment = new gradeCheckFragment();break;
                case 4:
                    fragment = new booklendFragment();break;
                case 5:
                    fragment = new bookSearchFragment();break;
                case 6:
                    fragment = new SettingsFragment();break;
                case 7:
                    break;
                default:
                    break;
            }
            FragmentTransaction tr = fM.beginTransaction();
            tr.replace(R.id.main_content, fragment);
            tr.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
