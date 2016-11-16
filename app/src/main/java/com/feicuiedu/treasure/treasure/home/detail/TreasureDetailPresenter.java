package com.feicuiedu.treasure.treasure.home.detail;

import com.feicuiedu.treasure.net.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${ljy} on 2016/11/15.
 */

public class TreasureDetailPresenter {

    private Call<List<TreasureDetailResult>> treasureDetailCall;
    private TreasureDetailView treasureDetailView;

    public TreasureDetailPresenter(TreasureDetailView treasureDetailView) {
        this.treasureDetailView = treasureDetailView;
    }

    public void getTreasureDetail(TreasureDetail treasureDetail){
        if (treasureDetailCall ==null){
            treasureDetailCall.cancel();
        }
        treasureDetailCall = NetClient.getInstance().getTreasureApi().getTreasureDetail(treasureDetail);
        treasureDetailCall.enqueue(callback);
    }
    Callback<List<TreasureDetailResult>> callback = new Callback<List<TreasureDetailResult>>() {
        @Override
        public void onResponse(Call<List<TreasureDetailResult>> call, Response<List<TreasureDetailResult>> response) {
            if (response!=null && response.isSuccessful()){
                List<TreasureDetailResult> resultList = response.body();
                if (resultList==null){
                    // 弹出吐司，发生了错误
                    treasureDetailView.showMessage("unknown error");
                    return;
                }
                // 数据有了，设置给视图来展示
                treasureDetailView.setData(resultList);
            }
        }

        @Override
        public void onFailure(Call<List<TreasureDetailResult>> call, Throwable t) {
            treasureDetailView.showMessage(t.getMessage());
        }
    };
}
