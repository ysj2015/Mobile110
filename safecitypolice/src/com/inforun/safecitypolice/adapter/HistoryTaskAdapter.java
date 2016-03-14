package com.inforun.safecitypolice.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.utils.DateUtil;
import com.inforun.safecitypolice.utils.StringUtils;

/**
 *历史任务adapter
 * 
 * @author xiongchaoxi
 * 
 */
public class HistoryTaskAdapter extends BaseAdapter {

	/**
	 * 任务集合
	 */
	private ArrayList<Task> listTask;
	/**
	 * 上下文
	 */
	private Context context;
	public HistoryTaskAdapter(Context context) {

		this.context = context;
	}

	public void setList(ArrayList<Task> list) {
		if (null == list)
			this.listTask = new ArrayList<Task>();
		else
			this.listTask = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listTask.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return  listTask.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (arg1 == null) {
			arg1 = View.inflate(context, R.layout.historytask_listview_item,
					null);
			holder = new ViewHolder();
			holder.tv_content = (TextView) arg1.findViewById(R.id.tv_content);
			holder.tv_accept = (TextView) arg1.findViewById(R.id.tv_accept);
			holder.tv_acceptTime = (TextView) arg1.findViewById(R.id.tv_acceptTime);
			holder.tv_submitTime = (TextView) arg1.findViewById(R.id.tv_submitTime);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		int id = listTask.get(arg0).getId();
		String content = listTask.get(arg0).getTaskContent();
		
		holder.tv_content.setText("编号:"+id + " "+content);
		byte accept = listTask.get(arg0).getAcceptStatus();
		// 接受状态 0新建的 1处理中的 2已经完成的 -1无效的
		String acceptText="";
		switch (accept) {
			case 0:
				acceptText="新建";
				break;
			case 1:
				acceptText="处理中";				
				break;
			case 2:
				acceptText="已经完成";
				break;
			case -1:
				acceptText="超时";
				break;
		}
		
		holder.tv_accept.setText(acceptText);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date acceptTime = listTask.get(arg0).getAcceptTime();
		if(acceptTime != null) {
			holder.tv_acceptTime.setText("接警时间:" + sdf.format(acceptTime)+ "");
		}
		Date submitTime = listTask.get(arg0).getSubmitTime();
		if(submitTime != null) {
			holder.tv_submitTime.setText("提交时间:" + sdf.format(submitTime)+ "");
		}
		return arg1;
	}

	private class ViewHolder {

		TextView tv_content, tv_accept,tv_acceptTime,tv_submitTime;
		//内容，接受状态，接警时间，提交时间
	}
}
