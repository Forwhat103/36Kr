package com.zhuxiaoming.kr36.setting;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.base.MyApplication;
import com.zhuxiaoming.kr36.util.DataCleanManager;

/**
 * Created by zhuxiaoming on 16/5/10.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView cacheSizeTv;
    private LinearLayout cleanLl;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        cacheSizeTv = bindView(R.id.setting_aty_cache_size);
        cleanLl = bindView(R.id.setting_aty_clear_ll);
    }

    @Override
    protected void initData() {
        try {
            cacheSizeTv.setText(DataCleanManager.getCacheSize(this.getCacheDir()));
            Log.d("uuuuuuuuuuu", DataCleanManager.getCacheSize(this.getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cleanLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_aty_clear_ll:
                // 清理缓存
                DataCleanManager.deleteDir(this.getCacheDir());
                try {
                    cacheSizeTv.setText(DataCleanManager.getCacheSize(this.getCacheDir()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
