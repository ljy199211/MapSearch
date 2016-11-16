package com.feicuiedu.treasure.treasure.home.hide;

import com.feicuiedu.treasure.net.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${ljy} on 2016/11/15.
 */

public class HideTreasurePresenter {


    private HideTreasureView hideTreasureView;
    private Call<HideTreasureResult> hideTreasureResultCall;

    public HideTreasurePresenter(HideTreasureView hideTreasureView) {
        this.hideTreasureView = hideTreasureView;
    }

    public void hideTreasure(HideTreasure hideTreasure){
        hideTreasureView.showProgress();
        hideTreasureResultCall = NetClient.getInstance().getTreasureApi().hideTreasure(hideTreasure);
        hideTreasureResultCall.enqueue(callback);
    }
   private Callback<HideTreasureResult> callback = new Callback<HideTreasureResult>() {
       @Override
       public void onResponse(Call<HideTreasureResult> call, Response<HideTreasureResult> response) {
           hideTreasureView.hideProgress();
           if (response.isSuccessful()&&response!=null){
               HideTreasureResult hideTreasureResult = response.body();
               if (hideTreasureResult==null){
                   hideTreasureView.showMessage("unknown error");
               }
               if (hideTreasureResult.getErrcode() == 1){
                   hideTreasureView.navigationToHome();
               }
               hideTreasureView.showMessage(hideTreasureResult.getErrmsg());
           }
       }

       @Override
       public void onFailure(Call<HideTreasureResult> call, Throwable t) {
            hideTreasureView.hideProgress();
           hideTreasureView.showMessage(t.getMessage());
       }
   };
}
