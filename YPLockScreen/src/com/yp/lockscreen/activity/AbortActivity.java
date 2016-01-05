package com.yp.lockscreen.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.yp.enstudy.utils.DeviceUtil;
import com.yp.lockscreen.R;
import com.yp.lockscreen.utils.NetworkUtils;

public class AbortActivity extends Activity implements OnClickListener {

    private LinearLayout backLy;

    private RelativeLayout versionRy;

    private RelativeLayout fbLy;

    private RelativeLayout updataLy;
    
    private Context mCxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abort_activity);
        mCxt = this;
        backLy = (LinearLayout) findViewById(R.id.abort_activity_back_ly);
        backLy.setOnClickListener(this);
        versionRy = (RelativeLayout) findViewById(R.id.abort_activity_version_ry);
        versionRy.setOnClickListener(this);
        fbLy = (RelativeLayout) findViewById(R.id.set_more_activity_question_ry);
        fbLy.setOnClickListener(this);
        updataLy = (RelativeLayout) findViewById(R.id.set_more_activity_check_updata_ry);
        updataLy.setOnClickListener(this);
        
        TextView versionTxt = (TextView)findViewById(R.id.version);
        String version = DeviceUtil.getVersionName(this);
        versionTxt.setText(version);
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
    private void inflaterToast(Context cxt,String str){
        Toast.makeText(cxt, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.abort_activity_back_ly:
            finish();
            break;
        case R.id.abort_activity_version_ry:

            break;
        case R.id.set_more_activity_question_ry:
            FeedbackAgent agent = new FeedbackAgent(this);
            agent.startFeedbackActivity();
            break;
        case R.id.set_more_activity_check_updata_ry:
            if (!NetworkUtils.isNetworkAvaialble(mCxt)) {
                inflaterToast(mCxt, getString(R.string.netword_error));
                return;
            }
            break;            
        }
    }
}
