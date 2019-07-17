package com.fengmang.myapplication;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.CameraPosition;
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

public class FirstFragment extends Fragment implements View.OnClickListener, AMap.OnMyLocationChangeListener {

    private TextureMapView textureMapView = null;
    private AMap aMap;
    private Button btn_tile;
    private TextView textPos;
    private TileOverlay mtileOverlay;

    private CustomMapStyleOptions mapStyleOptions = new CustomMapStyleOptions();

    private FirstViewModel mViewModel;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        // TODO: Use the ViewModel

        //获取地图控件引用
        textureMapView = (TextureMapView) getView().findViewById(R.id.map);
        btn_tile = (Button) getView().findViewById(R.id.btn_tile);
        textPos = (TextView) getView().findViewById(R.id.text_pos);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        textureMapView.onCreate(savedInstanceState);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = textureMapView.getMap();
        }
        //移动中心点到西安
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.341568, 108.940174), 10));

        btn_tile.setOnClickListener(this);

//        setMapCustomStyleFile(this);
//        basicmap = (Button)findViewById(R.id.basicmap);
//        basicmap.setOnClickListener(this);
//        rsmap = (Button)findViewById(R.id.rsmap);
//        rsmap.setOnClickListener(this);
//        nightmap = (Button)findViewById(R.id.nightmap);
//        nightmap.setOnClickListener(this);
//        navimap = (Button)findViewById(R.id.navimap);
//        navimap.setOnClickListener(this);

//        mStyleCheckbox = (CheckBox) findViewById(R.id.check_style);
//
//        mStyleCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(mapStyleOptions != null) {
//                    // 设置自定义样式
//                    mapStyleOptions.setEnable(b);
////					mapStyleOptions.setStyleId("your id");
//                    aMap.setCustomMapStyle(mapStyleOptions);
//                }
//            }
//        });
//        // 定位蓝点
//        setLocationStyle(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tile:
                if (mtileOverlay != null){
                    mtileOverlay.remove();
                    mtileOverlay = null;
                } else {
                    //在线瓦片数据
                    useOMCMap();
                }
                break;
        }
    }
    /**
     * 加载在线瓦片数据
     */
    private void useOMCMap() {
//        final String url = "http://tile.opencyclemap.org/cycle/%d/%d/%d.png";
        final String url = "http://mt2.google.cn/vt/lyrs=y&hl=zh-CN&gl=cn&src=app&x=%d&y=%d&z=%d&s=g";
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    return new URL(String.format(url, x, y, zoom));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions()
                .tileProvider(tileProvider)
                .diskCacheEnabled(true)
                .diskCacheDir("/storage/emulated/0/amap/OMCcache")
                .diskCacheSize(100000)
                .memoryCacheEnabled(true)
                .memCacheSize(100000)
                .zIndex(-9999);
        mtileOverlay = aMap.addTileOverlay(tileOverlayOptions);
    }

    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_new.data";
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            if(mapStyleOptions != null) {
                // 设置自定义样式
                mapStyleOptions.setStyleData(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLocationStyle(Context context) {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //定位蓝点提供8种展现模式
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

//        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    @Override
    public void onMyLocationChange(Location location) {
        textPos.setText(location.getLatitude()+ " : " + location.getLatitude());
        textPos.requestLayout();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        textureMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
//        setCameraPosition(aMap.getCameraPosition());//保存地图状态
        super.onDestroy();
        textureMapView.onDestroy();
    }

}
