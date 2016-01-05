package com.dianxinos.lockscreen_sdk.views;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dianxinos.lockscreen_sdk.DXLockScreenMediator;
import com.dianxinos.lockscreen_sdk.DXLockScreenUtils;

/**
 * The screen within {@link LockPatternKeyguardView} that shows general
 * information about the device depending on its state, and how to get past it,
 * as applicable.
 */
public abstract class DXLockScreenSDKBaseView extends FrameLayout{

    private static final String TAG = "DXLockScreenSDKBaseView";

    protected DXLockScreenMediator mDXLockScreenMediator;

    protected boolean mIsPaused = true;

    protected Context mContext;

    protected static AudioManager sAudioManager;

    protected static TelephonyManager sTelephonyManager;

    // for pressing volume_down and menu key, we will unlock lockscreen.
    private boolean mFirstForceUnlockKeyIsDown;
    private boolean mSecondForceUnlockKeyIsDown;
    private static final int FIRST_FORCE_UNLOCK_KEY_CODE = KeyEvent.KEYCODE_VOLUME_UP;
    private static final int SECOND_FORCE_UNLOCK_KEY_CODE = KeyEvent.KEYCODE_MENU;

    public DXLockScreenSDKBaseView(Context context,
            DXLockScreenMediator mediator) {
        super(context);
        mContext = context;
        mDXLockScreenMediator = mediator;

        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    public abstract void setWallpaper();

    public abstract void updateTimeAndDate();

    public abstract void updateCarrierInfo(CharSequence carrierStr);

    public abstract void updateCallSmsCount(int mUnreadType, int unreadNum);
    
    public abstract void updateBatteryStatus(boolean isDevicePluggedIn, int batteryLevel);

    public void onResume() {
        mIsPaused = false;
    }

    public void onPause() {
        mIsPaused = true;
    }

    public void destroy() {
        mIsPaused = true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == SECOND_FORCE_UNLOCK_KEY_CODE && event.getAction() == KeyEvent.ACTION_DOWN) {
            mFirstForceUnlockKeyIsDown = true;
        } else if (keyCode == FIRST_FORCE_UNLOCK_KEY_CODE
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            mSecondForceUnlockKeyIsDown = true;
        } else if ((keyCode == FIRST_FORCE_UNLOCK_KEY_CODE && !mFirstForceUnlockKeyIsDown)
                || (keyCode == SECOND_FORCE_UNLOCK_KEY_CODE && !mSecondForceUnlockKeyIsDown)) {
            mFirstForceUnlockKeyIsDown = false;
            mSecondForceUnlockKeyIsDown = false;
        } else if (mFirstForceUnlockKeyIsDown && mSecondForceUnlockKeyIsDown
                && (keyCode == FIRST_FORCE_UNLOCK_KEY_CODE || keyCode == SECOND_FORCE_UNLOCK_KEY_CODE)
                && event.getAction() == KeyEvent.ACTION_UP) {
            Log.d(TAG, "menu and volume_down have been pressed.");
            mFirstForceUnlockKeyIsDown = false;
            mSecondForceUnlockKeyIsDown = false;
            if (mDXLockScreenMediator != null)
                mDXLockScreenMediator.notifyUnlockNormal(DXLockScreenUtils.UNLOCK_POSITION_HOME);
        } else {
            mFirstForceUnlockKeyIsDown = false;
            mSecondForceUnlockKeyIsDown = false;
        }
        if (interceptMediaKey(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * Allows the media keys to work when the keyguard is showing.
     * The media keys should be of no interest to the actual keyguard view(s),
     * so intercepting them here should not be of any harm.
     * @param event The key event
     * @return whether the event was consumed as a media key.
     */
    private boolean interceptMediaKey(KeyEvent event) {
        final int keyCode = event.getKeyCode();
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    /* Suppress PLAYPAUSE toggle when phone is ringing or
                     * in-call to avoid music playback */
                    if (sTelephonyManager == null) {
                        sTelephonyManager = (TelephonyManager) getContext().getSystemService(
                                Context.TELEPHONY_SERVICE);
                    }
                    if (sTelephonyManager != null &&
                            sTelephonyManager.getCallState() != TelephonyManager.CALL_STATE_IDLE) {
                        return true;  // suppress key event
                    }
                case KeyEvent.KEYCODE_HEADSETHOOK: 
                case KeyEvent.KEYCODE_MEDIA_STOP: 
                case KeyEvent.KEYCODE_MEDIA_NEXT: 
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS: 
                case KeyEvent.KEYCODE_MEDIA_REWIND: 
                case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD: {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
                    intent.putExtra(Intent.EXTRA_KEY_EVENT, event);
                    getContext().sendOrderedBroadcast(intent, null);
                    return true;
                }

                case KeyEvent.KEYCODE_VOLUME_UP:
                case KeyEvent.KEYCODE_VOLUME_DOWN: {
                    synchronized (this) {
                        if (sAudioManager == null) {
                            sAudioManager = (AudioManager) getContext().getSystemService(
                                    Context.AUDIO_SERVICE);
                        }
                    }
                    // Volume buttons should only function for music.
                    if (sAudioManager.isMusicActive()) {
                        sAudioManager.adjustStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    keyCode == KeyEvent.KEYCODE_VOLUME_UP
                                            ? AudioManager.ADJUST_RAISE
                                            : AudioManager.ADJUST_LOWER,
                                    0);
                    }
                    // Don't execute default volume behavior
                    return true;
                }
            }
        } else if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_MUTE:
                case KeyEvent.KEYCODE_HEADSETHOOK: 
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE: 
                case KeyEvent.KEYCODE_MEDIA_STOP: 
                case KeyEvent.KEYCODE_MEDIA_NEXT: 
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS: 
                case KeyEvent.KEYCODE_MEDIA_REWIND: 
                case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD: {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
                    intent.putExtra(Intent.EXTRA_KEY_EVENT, event);
                    getContext().sendOrderedBroadcast(intent, null);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return if the view in previewAcitivity return true.else false
     */
    public boolean isShowInPreview() {
        return mDXLockScreenMediator == null;
    }
}
