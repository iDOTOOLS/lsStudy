package com.yp.lockscreen.work;

import com.yp.enstudy.bean.TableName;
import com.yp.lockscreen.DownloadVoice;
import com.yp.lockscreen.R;
import com.yp.lockscreen.port.Global;
import com.yp.lockscreen.utils.StringUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceDownLoadManger {
	public VoiceDownLoadManger() {
		super();
	}

	public void startDownLoad(final Context cxt) {

		final TableName tableName = Global.gWordData.getCurWordLibraryData();

		final Dialog audioDownloadDialog = new AlertDialog.Builder(cxt)
				.create();
		audioDownloadDialog.show();
		View audioDownloadView = LayoutInflater.from(cxt).inflate(
				R.layout.dialog_audio_download, null);
		audioDownloadDialog.getWindow().setContentView(audioDownloadView);

		final TextView contentText = (TextView) audioDownloadView
				.findViewById(R.id.dialog_audio_download_content_text);
		final Button canncelBtn = (Button) audioDownloadView
				.findViewById(R.id.dialog_audio_download_canncel_btn);
		final Button OkBtn = (Button) audioDownloadView
				.findViewById(R.id.dialog_audio_download_ok_btn);
//		contentText.setText("是否下载“" + tableName.ciku_name + "”语音包\n大小：" + StringUtils.parseSizeForMB(tableName.audio_size) + "MB");
		contentText.setText(cxt.getString(R.string.are_you_sure_donwload_voicepkg_msg, tableName.ciku_name,StringUtils.parseSizeForMB(tableName.audio_size)+""));

		canncelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				audioDownloadDialog.dismiss();
			}
		});
		OkBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    audioDownloadDialog.dismiss();
			    if(!DownloadVoice.isDownLoading){
	                Intent serviceIntent = new Intent(cxt, DownloadVoice.class);
	                serviceIntent.putExtra("name", tableName.name + ".zip");
	                serviceIntent.putExtra("file_size", tableName.audio_size);
	                cxt.startService(serviceIntent);			        
			    }else{
			        Toast.makeText(cxt, R.string.have_task_downloading, Toast.LENGTH_LONG).show();
			    }
			}
		});
	}
	
}
