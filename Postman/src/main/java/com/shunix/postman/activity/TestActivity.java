package com.shunix.postman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.shunix.postman.R;
import com.shunix.postman.service.PostmanService;
import com.shunix.postman.util.CommonUtils;

/**
 * @author shunix
 * @since 2016/6/7
 */
public class TestActivity extends Activity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                Intent intent = new Intent(this, PostmanService.class);
                startService(intent);
                break;
            case R.id.button2:

                break;
            case R.id.button3:
                CommonUtils.checkNotificationPermission(this, getPackageName());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }
}
