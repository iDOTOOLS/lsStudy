
package com.dianxinos.lockscreen_sdk;

import android.app.KeyguardManager;
import android.content.Context;

class DXKeyGuardLocker {
    private static DXKeyGuardLocker mInstance = null;

    private KeyguardManager.KeyguardLock mKeyguardLock = null;

    private KeyguardManager mKeyguardManager;

    private DXKeyGuardLocker(Context paramContext) {
        KeyguardManager localKeyguardManager = (KeyguardManager) paramContext
                .getSystemService(Context.KEYGUARD_SERVICE);
        this.mKeyguardManager = localKeyguardManager;
    }

    public static DXKeyGuardLocker getInstance(Context paramContext) {
        if (mInstance == null) {
            Context localContext = paramContext.getApplicationContext();
            mInstance = new DXKeyGuardLocker(localContext);
        }
        return mInstance;
    }

    public void disableKeyGuard() {
        if (this.mKeyguardLock != null)
            this.mKeyguardLock.disableKeyguard();
    }

    public void getNewKeyGuard() {
        if (this.mKeyguardLock != null)
            releaseKeyGuard();
        KeyguardManager.KeyguardLock localKeyguardLock = this.mKeyguardManager
                .newKeyguardLock("KeyGuardLocker");
        this.mKeyguardLock = localKeyguardLock;
        //int i = Log.d("KeyGuardLocker", "Disabled keyguard");
    }

    public void reenableKeyGuard() {
        if (this.mKeyguardLock != null)
            this.mKeyguardLock.reenableKeyguard();
    }

    public void releaseKeyGuard() {
        if (this.mKeyguardLock != null) {
            this.mKeyguardLock.reenableKeyguard();
            this.mKeyguardLock = null;
        }
    }
}
