package com.feicuiedu.treasure.treasure.home.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.components.TreasureView;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.home.map.MapFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${ljy} on 2016/11/14.
 */

public class TreasureDetailActivity extends AppCompatActivity implements TreasureDetailView{
    private static final String KEY_TREASURE = "key_treasure";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.treasureView)
    TreasureView treasureView;
    @BindView(R.id.tv_detail_description)
    TextView tvDetailDescription;
    private Treasure treasure;
    private BitmapDescriptor dot = BitmapDescriptorFactory.fromResource(R.drawable.treasure_expanded);
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_detail);

    }

    public static void start(Context context, Treasure treasure) {
        Intent starter = new Intent(context, TreasureDetailActivity.class);
        starter.putExtra(KEY_TREASURE, treasure);
        context.startActivity(starter);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        treasure = (Treasure) getIntent().getSerializableExtra(KEY_TREASURE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(treasure.getTitle());

        treasureView.bindTreasure(treasure);

        //地图展示
        initMap();
        TreasureDetail treasureDetail = new TreasureDetail(treasure.getId());
        //请求业务了
        TreasureDetailPresenter presenter = new TreasureDetailPresenter(this);
        presenter.getTreasureDetail(treasureDetail);
    }

    private void initMap() {
        LatLng latLng = new LatLng(treasure.getLatitude(),treasure.getLongitude());
        MapStatus status = new MapStatus.Builder()
                .zoom(18)
                .target(latLng)
                .overlook(-20)
                .build();
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(status)
                .compassEnabled(false)
                .scrollGesturesEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false)
                .rotateGesturesEnabled(false)
                .scaleControlEnabled(false);

        MapView mapView = new MapView(this,options);
        frameLayout.addView(mapView,0);

        BaiduMap baiduMap = mapView.getMap();


        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .anchor(0.5f,0.5f)
                .icon(dot);
        baiduMap.addOverlay(markerOptions);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // 弹出菜单进行导航
    @OnClick(R.id.iv_navigation)
    public void showPopupMenu(View view) {

        // 弹出菜单创建
        PopupMenu popupMenu = new PopupMenu(this, view);

        // 菜单布局填充
        popupMenu.inflate(R.menu.menu_navigation);

        // 菜单项的点击监听
        popupMenu.setOnMenuItemClickListener(onMenuItemListener);

        // 显示
        popupMenu.show();
    }

    private PopupMenu.OnMenuItemClickListener onMenuItemListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            // 起点和终点位置和地址
            LatLng startLocation = MapFragment.getMyLocation();
            String startAddr = MapFragment.getMyAddress();
            LatLng endLocation = new LatLng(treasure.getLatitude(),treasure.getLongitude());
            String endAddr = treasure.getLocation();

            //处理点击的是哪一项
            switch (item.getItemId()) {
                case R.id.walking_navi:
                    startWalkingNavi(startLocation,startAddr,endLocation,endAddr);
                    break;
                case R.id.biking_navi:
                    startBikingNavi(startLocation,startAddr,endLocation,endAddr);
                    break;
            }
            return false;
        }
    };

    // 开始步行导航
    public void startWalkingNavi(LatLng start,String startAddr,LatLng end,String endAddr){
        NaviParaOption option = new NaviParaOption()
                .startPoint(start)
                .endPoint(end)
                .startName(startAddr)
                .endName(endAddr);

        // BaiduMapNavigation.openBaiduMapWalkNavi(option,this) 去导航
        boolean walkNavi = BaiduMapNavigation.openBaiduMapWalkNavi(option, this);//去导航
        if (!walkNavi){
            startWebNavi(start, startAddr, end, endAddr);
        }
    }

    // 开始骑行导航
    public void startBikingNavi(LatLng start,String startAddr,LatLng end,String endAddr){
        NaviParaOption option = new NaviParaOption()
                .startPoint(start)
                .endPoint(end)
                .startName(startAddr)
                .endName(endAddr);
        boolean bikeNavi = BaiduMapNavigation.openBaiduMapBikeNavi(option, this);
        if (!bikeNavi){
            showDialog();
        }
    }

    // 打开网页导航
    public void startWebNavi(LatLng start,String startAddr,LatLng end,String endAddr){

        NaviParaOption option = new NaviParaOption()
                .startPoint(start)
                .endPoint(end)
                .startName(startAddr)
                .endName(endAddr);
        BaiduMapNavigation.openWebBaiduMapNavi(option,this);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图App或App版本过低，要不要安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(TreasureDetailActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(List<TreasureDetailResult> results) {
        // 设置数据
        if (results.size() >= 1) {
            TreasureDetailResult result = results.get(0);
            tvDetailDescription.setText(result.description);
            return;
        }
        activityUtils.showToast("没有记录");
    }
}
