package com.feicuiedu.treasure.treasure.home.detail;

import java.util.List;

/**
 * Created by ${ljy} on 2016/11/15.
 */

public interface TreasureDetailView {
    void showMessage(String msg);
    void setData(List<TreasureDetailResult> results);
}
