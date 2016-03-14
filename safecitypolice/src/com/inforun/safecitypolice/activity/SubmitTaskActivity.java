package com.inforun.safecitypolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.UHelper;
import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 提交任务界面
 * Created by daniel on 2015/11/4.
 */
public class SubmitTaskActivity extends BaseActivity {
    /**
     * 处理结果
     */
    private EditText et_result;

    private ImageView btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submittask);
        setUpActionBar();
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        et_result= (EditText) findViewById(R.id.et_result);
        btn_submit= (ImageView) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTask();
            }
        });
    }

    /**
     * 提交任务
     */
    private void submitTask() {
        String result=et_result.getText().toString().trim();
        if (null==result){
            UHelper.showToast(SubmitTaskActivity.this,"请输入处理结果！");
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("resultText",result);
        final XRequestManager manager = new XRequestManager(SubmitTaskActivity.this);
        manager.post(Constants.POLICE_SUBMITTASK, map, new XRequestListener() {
            @Override
            public void onRequestStart() {
                manager.setProgressMessage("正在提交中...");
            }

            @Override
            public void onRequestSuccess(XResponse res) {
                int code = res.getStatusCode();
                if (code == 1) {
                    UHelper.showToast(SubmitTaskActivity.this, "任务提交成功");
                    Intent intent = new Intent();
//                    intent.setAction("Refresh");
//                    SubmitTaskActivity.this.sendBroadcast(intent);
                    intent.setClass(SubmitTaskActivity.this, MainActivity.class);
                    SubmitTaskActivity.this.startActivity(intent);
                    finish();
                    
                }else{
                    UHelper.showToast(SubmitTaskActivity.this,res.getMessage());
                }
            }

            @Override
            public void onRequestFailure(HttpException error, String msg) {

            }
        });
    }
    /**
     * 隐藏actionBar上的控件
     */
    private void setUpActionBar() {
        
        setActionBarMidTitle(getString(R.string.task_finish));
    }
    
	@Override
	public void finish() {
		setResult(2);
		super.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
