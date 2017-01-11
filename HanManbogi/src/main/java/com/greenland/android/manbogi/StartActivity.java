package com.greenland.android.manbogi;

/**
 * Created by mudaya4 on 2017-01-10.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class StartActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.real_tabcontent);


        tabhost.addTab(tabhost.newTabSpec("0").setIndicator("만보기 화면"),
                ManboScreen.class, null);

        tabhost.addTab(tabhost.newTabSpec("1").setIndicator("만보기 기록"),
                ManboLog.class, null);
    }
}
