package com.feicuiedu.treasure.treasure.home.map;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.treasure.Area;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.home.TreasureApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${ljy} on 2016/11/11.
 */

public class MapPresenter {
    private MapMvpView mapMvpView;

    public MapPresenter(MapMvpView mapMvpView) {
        this.mapMvpView = mapMvpView;
    }

    public void getTreasure(Area area) {
        TreasureApi treasureApi = NetClient.getInstance().getTreasureApi();
        Call<List<Treasure>> listCall = treasureApi.showTreasureArea(area);
        listCall.enqueue(callBack);
    }

    private Callback<List<Treasure>> callBack = new Callback<List<Treasure>>() {
        @Override
        public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
            if (response.isSuccessful()) {

                List<Treasure> treasureList = response.body();
                if (treasureList == null) {
                    mapMvpView.showMessage("发生了未知错误。。。");
                }

                mapMvpView.setTreasureData(treasureList);
            }
        }

        @Override
        public void onFailure(Call<List<Treasure>> call, Throwable t) {
            mapMvpView.showMessage("请求失败");
        }
    };
}
