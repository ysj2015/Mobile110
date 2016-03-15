package com.inforun.safecitypeople.activity;

import com.inforun.safecitypeople.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AddDataActivity extends Activity implements OnClickListener {
	EditText et;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        et = (EditText)findViewById(R.id.edt_name);
        findViewById(R.id.button).setOnClickListener(this);
	}
	
	public void add() {
		DbUtils util = DbUtils.create(this, "test");
		//Student s = new Student();
//		s.setName(et.getText().toString());
//		try {
//			util.save(s);
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.button:
			add();
//			setResult(RESULT_OK,getIntent());
//			finish();
//			Intent in = new Intent(this,TestActivity.class);
//			startActivity(in);
		}
	}
}
