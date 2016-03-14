package com.inforun.safecitypolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.utils.DateUtil;

import java.util.ArrayList;

/**
 *历史任务adapter
 * 
 * @author xiongchaoxi
 * 
 */
public class MissTaskAdapter extends BaseAdapter {

	/**
	 * 任务集合
	 */
	private ArrayList<Task> listTask;
	/**
	 * 上下文
	 */
	private Context context;
	public MissTaskAdapter(Context context) {

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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (arg1 == null) {
			arg1 = View.inflate(context, R.layout.misstask_listview_item,
					null);
			holder = new ViewHolder();
			holder.tv_content = (TextView) arg1.findViewById(R.id.tv_content);
			holder.tv_accept = (TextView) arg1.findViewById(R.id.tv_accept);
			holder.tv_pushTime = (TextView) arg1.findViewById(R.id.tv_pushTime);
			holder.tv_acceptBtn = (TextView) arg1.findViewById(R.id.tv_acceptBtn);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.tv_content.setText(listTask.get(arg0).getTaskContent());
		int accept = listTask.get(arg0).getAcceptStatus();
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
			acceptText="无效";
			break;
		}
		if (accept!=0) {
			holder.tv_acceptBtn.setVisibility(View.INVISIBLE);
		}
		holder.tv_accept.setText(acceptText);
		
		//String date = DateUtil.getDate(listTask.get(arg0).getPushTime());
		//holder.tv_pushTime.setText( date+ "");
		holder.tv_acceptBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null!=acceptListener){
					acceptListener.accept(listTask.get(arg0).getId());
				}
			}
		});
		return arg1;
	}

	private class ViewHolder {

		TextView tv_content, tv_accept, tv_pushTime,tv_acceptBtn;
		//内容，接受状态，派警时间
	}

	private AcceptListener acceptListener;

	public void setAcceptListener(AcceptListener acceptListener) {
		this.acceptListener = acceptListener;
	}

	public interface  AcceptListener{
		void accept(int taskId);
	}
}
