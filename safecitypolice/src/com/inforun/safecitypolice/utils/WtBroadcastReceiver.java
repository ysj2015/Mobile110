package com.inforun.safecitypolice.utils;

import com.inforun.safecitypolice.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//The WtBroadcastReceiver's function is that start WtService to keep the Service all running in the background。
public class WtBroadcastReceiver extends BroadcastReceiver {
	private Intent msIntent = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		LogUtil.write("WtBroadcastReceiver start WtService\n");
		msIntent = new Intent(context, WtService.class);
		context.startService(msIntent);
	}

}
