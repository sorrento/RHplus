package com.stupidpeople.rhplus;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Milenko on 30/03/2016.
 */
public class DrawerSurveyActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_survey);
        Initalize();
    }
}
