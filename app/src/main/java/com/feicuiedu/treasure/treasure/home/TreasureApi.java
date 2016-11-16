package com.feicuiedu.treasure.treasure.home;

import com.feicuiedu.treasure.treasure.Area;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.home.detail.TreasureDetail;
import com.feicuiedu.treasure.treasure.home.detail.TreasureDetailResult;
import com.feicuiedu.treasure.treasure.home.hide.HideTreasure;
import com.feicuiedu.treasure.treasure.home.hide.HideTreasureResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ${ljy} on 2016/11/11.
 */

public interface TreasureApi {
    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> showTreasureArea(@Body Area area);
    @POST("/Handler/TreasureHandler.ashx?action=tdetails")
    Call<List<TreasureDetailResult>> getTreasureDetail(@Body TreasureDetail treasureDetail);

    @POST("/Handler/TreasureHandler.ashx?action=hide")
    Call<HideTreasureResult> hideTreasure(@Body HideTreasure hideTreasure);
}
