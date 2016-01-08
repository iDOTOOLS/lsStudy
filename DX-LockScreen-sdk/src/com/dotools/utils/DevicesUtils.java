
package com.dotools.utils;

import android.annotation.SuppressLint;
import android.os.Build;


@SuppressLint("NewApi")
public class DevicesUtils {
    static Boolean isLenovoDevice = null;
    public static boolean isLenovoDevice() {
        if (isLenovoDevice == null) {
            String model = "Lenovo";
            isLenovoDevice = Build.MODEL.startsWith(model) || Build.FINGERPRINT.startsWith(model);
        }
        return isLenovoDevice;
    }
}
