package com.dianxinos.lockscreen_sdk;

import java.util.List;

import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;
import com.dianxinos.lockscreen_sdk.StatManager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

public class LockscreenApplication extends Application {
    private BroadcastReceiver mPackageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
                Uri uri = intent.getData();
                if (uri == null) {
                    return;
                }
                String pkgName = uri.getSchemeSpecificPart();
                if (pkgName == null) {
                    return;
                }

                if (DXLockScreenUtils.DBG) {
                    Log.i("LockscreenApplication", "pkgName=" + pkgName);
                }
                if (DXLockScreenUtils.DXHOME_PACKAGE_NAME.equals(pkgName)) {
                    PackageManager pm = context.getPackageManager();
                    String installer = pm.getInstallerPackageName(pkgName);
                    StatManager.clickButton(installer);

                    if (DXLockScreenUtils.DBG) {
                        Log.i("LockscreenApplication", "installer=" + installer);
                    }
                    if (DXLockScreenUtils.KEY_THEME_INSTALL.equals(installer)) {
                        List<ResolveInfo> list = DXLockScreenUtils.getDeskInstalls(context);
                        ResolveInfo dxhomeInfo = null;
                        for (ResolveInfo info : list) {
                            if (DXLockScreenUtils.DXHOME_PACKAGE_NAME.equals(info.activityInfo.packageName)) {
                                dxhomeInfo = info;
                                break;
                            }
                        }

                        if (DXLockScreenUtils.DBG) {
                            Log.i("LockscreenApplication", "dxhomeInfo=" + dxhomeInfo);
                        }
                        if (dxhomeInfo != null) {
                            DXLockScreenUtils.startDXHome(context);
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        StatManager.init(this);

        IntentFilter inFilter = new IntentFilter();
        inFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        inFilter.addDataScheme("package");
        this.registerReceiver(mPackageReceiver, inFilter);
    }
}
