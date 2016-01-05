package com.yp.lockscreen.work;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yp.enstudy.bean.TableName;
import com.yp.enstudy.db.GlobalConfigMgr;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.StringUtils;

public class CikuListAdapter extends BaseAdapter {

	private Context mContext;

	private List<TableName> mList;

	private LayoutInflater inflater;

	private String curDBname;
	
	private String curCnName;
	
	private int mPos;

	public CikuListAdapter(Context mContext, List<TableName> mList,
			String curDBNmae,String cnName) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		inflater = LayoutInflater.from(mContext);

		curDBname = curDBNmae;
		curCnName = cnName;
	}

	public CikuListAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder = null;

		if (v == null) {
			v = inflater.inflate(R.layout.ciku_list_item, null);
			holder = new ViewHolder();
			holder.nameText = (TextView) v.findViewById(R.id.ciku_list_item_name);
			holder.stateText = (TextView) v.findViewById(R.id.ciku_list_item_state_text);
			holder.stateImg = (ImageView) v.findViewById(R.id.ciku_list_item_state_img);
			holder.viewLine = (View) v.findViewById(R.id.ciku_list_item_view_line);
			holder.itemLy = (RelativeLayout) v.findViewById(R.id.ciku_list_item_ly);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		final TableName name = mList.get(position);

		if (name.sequence == 1) {
			holder.viewLine.setVisibility(View.VISIBLE);
		} else {
			holder.viewLine.setVisibility(View.GONE);
		}

		holder.nameText.setText(name.ciku_name);
		if (name.isDownLoad) {
			holder.stateText.setVisibility(View.GONE);
		} else {
			holder.stateText.setVisibility(View.VISIBLE);
		}
		if (name.name.equals(curDBname)) {
			holder.stateImg.setVisibility(View.VISIBLE);
		} else {
			holder.stateImg.setVisibility(View.GONE);
		}
		holder.itemLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPos = mList.indexOf(name);
				if (name.isDownLoad) {
					curDBname = name.name;
					curCnName = name.ciku_name;
					CikuListAdapter.this.notifyDataSetChanged();
				} else {
				        String content = mContext.getString(R.string.are_you_sure_donwload_ciku_msg,name.ciku_name,StringUtils.parseSizeForMB(name.ciku_size)+"");
	                    crearDialog(content, name.url, name);   
				}
			}
		});
		return v;
	}

	private class ViewHolder {

		TextView nameText;

		RelativeLayout itemLy;

		TextView stateText;

		ImageView stateImg;

		View viewLine;

	}

	void crearDialog(String content,String url,final TableName name) {

		final Dialog downLoadDialog = new AlertDialog.Builder(mContext)
				.create();
		downLoadDialog.show();
		final View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_ciku_download, null);
		downLoadDialog.getWindow().setContentView(v);
		final TextView contextText = (TextView) v.findViewById(R.id.dialog_ciku_download_content_text);
		final Button canncelBtn = (Button) v.findViewById(R.id.dialog_ciku_download_canncel_btn);
		final Button okBtn = (Button) v.findViewById(R.id.dialog_ciku_download_ok_btn);
		contextText.setText(content);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				downLoadDialog.dismiss();
				creatLoadingDialog(name.ciku_size);
				String dbNm = name.name+".zip";
				new LockDownLoadThread(dbNm, new DownDBcallback(), LockDownLoadThread.DOWNLOAD_DB_FLAG).start();
			}
		});
		canncelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				downLoadDialog.dismiss();
			}
		});
	}
	
	private Dialog loadingLog;
	
	private ProgressBar progressBar;
	private TextView contentText;
	
	private long tempBack = 0; 
	
	private void creatLoadingDialog(long max){
		loadingLog = new AlertDialog.Builder(mContext).create();
		loadingLog.setCancelable(false);
		loadingLog.show();
		View v = inflater.inflate(R.layout.dialog_loading_db, null);
		loadingLog.getWindow().setContentView(v);
		
		progressBar = (ProgressBar)v.findViewById(R.id.dialog_loading_context_bar);
		progressBar.setMax((int)(max));
		contentText = (TextView)v.findViewById(R.id.dialog_loading_content_text);
		final Button canncelBtn = (Button)v.findViewById(R.id.dialog_loading_canncel_btn);
		TextView titleTXT = (TextView)v.findViewById(R.id.dialog_loading_title_text);
		titleTXT.setText(R.string.download);
		canncelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Global.gDownLoad.stop();
				loadingLog.dismiss();
			}
		});
	}

	private class DownDBcallback extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			long back = (Long)msg.obj;
			if (back == LockDownLoadThread.DOWNLOAD_SUCCESS) {
				loadingLog.dismiss();
				mList.get(mPos).isDownLoad = true;
				curDBname = mList.get(mPos).name;
				curCnName = mList.get(mPos).ciku_name;
				CikuListAdapter.this.notifyDataSetChanged();
			}else if(back == LockDownLoadThread.DOWNLOAD_ERROR){
				loadingLog.dismiss();
				Toast.makeText(mContext, R.string.download_error_retry, Toast.LENGTH_SHORT).show();
			}else if(back>0){
			    tempBack = back;
			    int progress = (int)(tempBack);
			    progressBar.setProgress(progress);
			}
		}
	}
	
	public String getCurDBName() {

		return curDBname;
	}
	
	public String getCurCnName(){
		
		return curCnName;
	}
}
