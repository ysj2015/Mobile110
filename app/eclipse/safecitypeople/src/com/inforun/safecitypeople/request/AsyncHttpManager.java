package com.inforun.safecitypeople.request;

import com.loopj.android.http.AsyncHttpClient;

public class AsyncHttpManager {
	
	private static AsyncHttpClient client;
	
	public static AsyncHttpClient getAsyncHttpClient() {
		if(client == null) {
			client = new AsyncHttpClient();
		}
		return client;
	}
}