package com.feicuiedu.treasure.treasure.home.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.treasure.TreasureRepo;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ${ljy} on 2016/11/16.
 */

public class TreasureListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TreasureListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setBackgroundResource(R.drawable.screen_bg);


        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TreasureListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addItems(TreasureRepo.getInstance().getTreasure());

    }
}
