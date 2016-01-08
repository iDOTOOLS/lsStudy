/**
 * Copyright (c) Tapas Mobile.  All Rights Reserved.
 *
 * @author DaiHui 
 * @version 1.0
 */

package com.dotools.utils;

import android.content.Context;

public class Utilities {
    static Context sApplicationContext = null;
    
    public static void initEnvironment(Context ctx) {
        sApplicationContext = ctx;
    }


    public static Context getApplicationContext() {
        if (sApplicationContext == null) {
            throw new java.lang.IllegalStateException("Common library is used before initialize!");
        }

        return sApplicationContext;
    }

}
