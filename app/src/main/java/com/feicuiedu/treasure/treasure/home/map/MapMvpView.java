package com.feicuiedu.treasure.treasure.home.map;

import com.feicuiedu.treasure.treasure.Treasure;

import java.util.List;

/**
 * Created by ${ljy} on 2016/11/11.
 */

public interface MapMvpView {
    void showMessage(String msg);
    void setTreasureData(List<Treasure> list);
}
