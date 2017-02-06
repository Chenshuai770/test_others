package com.cs.test_others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cs.test_others.imageloader2.Imageloader2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "http://7xi8d6.com1.z0.glb.clouddn.com/16124047_121657248344062_4191645221970247680_n.jpg";
    private final String url2 = "http://7xi8d6.com1.z0.glb.clouddn.com/16124351_1863111260639981_4361246625721483264_n.jpg";
    private Button mBtnMain1;
    private ImageView mIvMain1;
    private Button mBtnMain2;
    private ImageView mIvMain2;
    private ImageLoader imageLoader=new ImageLoader();
    private Imageloader2 imageloader2 =new Imageloader2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
    }

    private void initDatas() {
        imageLoader.dispalyImage(url, mIvMain1);

    }

    private void initView() {

        mBtnMain1 = (Button) findViewById(R.id.btn_main1);
        mBtnMain1.setOnClickListener(this);
        mIvMain1 = (ImageView) findViewById(R.id.iv_main1);
        mIvMain1.setOnClickListener(this);
        mBtnMain2 = (Button) findViewById(R.id.btn_main2);
        mBtnMain2.setOnClickListener(this);
        mIvMain2 = (ImageView) findViewById(R.id.iv_main2);
        mIvMain2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main1:
              //  Toast.makeText(this, "图片加载中", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_main2:
                imageloader2.dispalyImage(url2,mIvMain2);
             //   Toast.makeText(this, "图片加载中", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
