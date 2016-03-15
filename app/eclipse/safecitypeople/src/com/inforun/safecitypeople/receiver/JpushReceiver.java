package com.inforun.safecitypeople.receiver;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.inforun.safecitypeople.activity.FeedbackActivity;

/**
 * 推送接受器 xiongchaoxi
 */
public class JpushReceiver extends BroadcastReceiver {

	private static final String TAG = "JPush";
	private static final String STATUE = "JPUSH";

	private TextView tv;
	private SharedPreferences sp;
	private Editor editor;

	public JpushReceiver(TextView tv) {
		this.tv = tv;
	}

	public JpushReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String extras = printBundle(bundle);
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			processCustomMessage(context, bundle);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			sp = context.getSharedPreferences(STATUE, Context.MODE_PRIVATE);
			editor = sp.edit();
			editor.putInt("status", notifactionId);
			editor.clear().commit();
			try {
				tv.setText("警察正在赶来处理");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			for (String key : bundle.keySet()) {
				if (key.equals(JPushInterface.EXTRA_EXTRA)) {
					if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
						continue;
					}
					// try {
					// JSONObject json = new
					// JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					// int status = json.getInt("alarmStatus");
					// Intent i = new Intent(context, FeedbackActivity.class);
					// i.putExtra("status", status);
					// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
					// Intent.FLAG_ACTIVITY_CLEAR_TOP );
					// context.startActivity(i);
					// break;
					// } catch (Exception e) {}
					Intent i = new Intent(context, FeedbackActivity.class);
					i.putExtra("status",
							bundle.getString(JPushInterface.EXTRA_EXTRA));
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}

			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..
			

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
		} else {
			
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}
				Log.v(TAG, bundle.getString(JPushInterface.EXTRA_EXTRA));
				try {
					JSONObject json = new JSONObject(
							bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it = json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" + myKey + " - "
								+ json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		// if (MainActivity.isForeground) {
		// String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		// String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		// Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
		// msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
		// if (!ExampleUtil.isEmpty(extras)) {
		// try {
		// JSONObject extraJson = new JSONObject(extras);
		// if (null != extraJson && extraJson.length() > 0) {
		// msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
		// }
		// } catch (JSONException e) {
		//
		// }
		//
		// }
		// context.sendBroadcast(msgIntent);
		// }
	}
}
