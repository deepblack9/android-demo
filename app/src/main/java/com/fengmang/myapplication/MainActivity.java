package com.fengmang.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TileOverlay;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.TileProvider;
import com.amap.api.maps.model.UrlTileProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends FragmentActivity {
    private FirstFragment firstFragment;



    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    public void initView() {
        firstFragment = FirstFragment.newInstance();
    }

    protected int[] getTabClickIds() {
        return new int[]{R.id.llBottomTabTab0, R.id.llBottomTabTab1, R.id.llBottomTabTab2, R.id.llBottomTabTab3};
    }

    protected int[][] getTabSelectIds() {
        return new int[][]{
                new int[]{R.id.ivBottomTabTab0, R.id.ivBottomTabTab1, R.id.ivBottomTabTab2, R.id.ivBottomTabTab3},//顶部图标
                new int[]{R.id.tvBottomTabTab0, R.id.tvBottomTabTab1, R.id.tvBottomTabTab2, R.id.tvBottomTabTab3}//底部文字
        };
    }

    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }


    protected Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return firstFragment;
            case 2:
                return firstFragment;
            case 3:
                return firstFragment;
            default:
                return firstFragment;
        }
    };

    private static final String[] TAB_NAMES = {"主页", "消息", "发现", "设置"};

    protected void selectTab(int position) {
        //导致切换时闪屏，建议去掉BottomTabActivity中的topbar，在fragment中显示topbar
        //		rlBottomTabTopbar.setVisibility(position == 2 ? View.GONE : View.VISIBLE);

//        tvBaseTitle.setText(TAB_NAMES[position]);

        //点击底部tab切换顶部tab，非必要
//        if (position == 2 && position == currentPosition && firstFragment != null) {
//            firstFragment.selectNext();
//        }
    }

    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    public void initData() {// 必须调用
//        super.initData();
//    }

    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    public void initEvent() {// 必须调用
//        super.initEvent();
//
//    }

//    public void onDragBottom(boolean rightToLeft) {
//        //将Activity的onDragBottom事件传递到Fragment，非必要<<<<<<<<<<<<<<<<<<<<<<<<<<<
//        switch (currentPosition) {
//            case 2:
//                if (firstFragment != null) {
//                    if (rightToLeft) {
//                        firstFragment.selectMan();
//                    } else {
//                        firstFragment.selectPlace();
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        //将Activity的onDragBottom事件传递到Fragment，非必要>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    }



    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
//    private long firstTime = 0;//第一次返回按钮计时
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch(keyCode){
//            case KeyEvent.KEYCODE_BACK:
//                long secondTime = System.currentTimeMillis();
//                if(secondTime - firstTime > 2000){
//                    showShortToast("再按一次退出");
//                    firstTime = secondTime;
//                } else {//完全退出
//                    moveTaskToBack(false);//应用退到后台
//                    System.exit(0);
//                }
//                return true;
//        }
//
//        return super.onKeyUp(keyCode, event);
//    }
    //双击手机返回键退出>>>>>>>>>>>>>>>>>>>>>

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
    }
}
