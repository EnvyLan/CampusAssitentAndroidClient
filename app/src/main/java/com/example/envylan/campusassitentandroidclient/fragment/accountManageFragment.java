package com.example.envylan.campusassitentandroidclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.envylan.campusassitentandroidclient.R;
import com.example.envylan.campusassitentandroidclient.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/*
@author EnvyLan
@version 2016-3-5 下午6:49:40
 */

public class accountManageFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_manager, null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView= (RecyclerView) v.findViewById(R.id.ac_recyclerView);
        initData();
        adapter=new RecyclerViewAdapter(list, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return  v;
    }

    public void initData(){
        list.add("教务系统");
        list.add("统一账号");
    }
}
