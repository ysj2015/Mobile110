package com.inforun.safecitypolice.other;

import android.os.Bundle;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;


/**
 *  消息提示
 * Created by daniel on 2015/9/29.
 */
public class NotificationHint extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tishi);
        setUpActionBar();
    }
    /**
     * 隐藏actionBar上的控件
     */
    private void setUpActionBar() {
        
        setActionBarMidTitle(getString(R.string.login));
    }
}
