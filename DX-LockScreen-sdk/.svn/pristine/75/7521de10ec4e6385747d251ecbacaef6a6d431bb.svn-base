
package com.dianxinos.lockscreen_sdk;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

/**
 * DXListPreference :support OnDialogClosedListener
 * @author ouyang
 *
 */
public class DXListPreference extends ListPreference {

    private OnDialogClosedListener mOnDialogClosedListener = null;

    public void setOnDialogClosedListener(OnDialogClosedListener listener) {
        this.mOnDialogClosedListener = listener;
    }

    public DXListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DXListPreference(Context context) {
        super(context);
    }

    /**
     * OnDialogClosedListener
     * @author ouyang
     *
     */
    public interface OnDialogClosedListener {
        boolean onDialogClosed(boolean positiveResult);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (mOnDialogClosedListener != null) {
            mOnDialogClosedListener.onDialogClosed(positiveResult);
        }
    }
}
