package com.imdoon.daemonguard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DaemonWatcher {
    


    public static final String TAG = "imdoon_DaemonWatcher";

    private static DaemonWatcher mWatcher = null;
    private WatcherLooper mThread = null;

    private DaemonWatcher() {
        mThread = new WatcherLooper();
        mThread.start();
    }

    static {
        try {
            System.loadLibrary("daemonguard");
        } catch(UnsatisfiedLinkError ule){
            System.err.println("WARNING: Could not load library: " + ule);
        }
    }

    public static DaemonWatcher getInstance() {
        if (mWatcher == null) {
            mWatcher = new DaemonWatcher();
        }
        return mWatcher;
    }

    public boolean init(int level, String packageName, String serviceName) {
        return native_init(level, packageName, serviceName);
    }

    public boolean deinit() {
        return native_deinit();
    }

    public void createAppMonitor() {
        mThread.getHandler().sendEmptyMessage(Messages.MSG_CREATE_MONITOR);
    }

    public void connectToAppMonitor() {
        mThread.getHandler().sendEmptyMessage(Messages.MSG_CONNECT_MONITOR);
    }

    public void disconnectToAppMonitor() {
        mThread.getHandler().sendEmptyMessage(Messages.MSG_DISCONNECT_MONITOR);
    }

//    public void tickStats() {
//        Log.d(TAG, "Send tickStats message to WatcherLooper");
//        mThread.getHandler().sendEmptyMessage(Messages.MSG_TICK_STATS);
//    }

    public String tickStats() {
        return String.valueOf(native_tickStats());
    }

    public class WatcherLooper extends Thread {
        private Handler handler;

        public WatcherLooper() {
            handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    switch(msg.what) {
                        case Messages.MSG_CREATE_MONITOR:
                            if (!native_createWatcher()) {
                            }
                            break;
                        case Messages.MSG_CONNECT_MONITOR:
                            if (native_connectToMonitor()) {
                            }
                            break;
                        case Messages.MSG_DISCONNECT_MONITOR:
                            if (native_disconnectToMonitor()) {
                            }
                            break;
//                        case Messages.MSG_TICK_STATS:
//                            Toast.makeText(getApplicationContext(), String.valueOf(native_tickStats()), Toast.LENGTH_SHORT).show();
//                            break;
                        default:
                            break;
                    }
                }
            };
        }

        public Handler getHandler() {
            return handler;
        }

        public void setHandler(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }

    public class Messages {
        public static final int MSG_CREATE_MONITOR = 0x01;
        public static final int MSG_CONNECT_MONITOR = 0x02;
        public static final int MSG_DISCONNECT_MONITOR = 0x03;
        public static final int MSG_TICK_STATS = 0x04;
    }

    public native boolean native_init(int level, String packageName, String serviceName);
    public native boolean native_deinit();
    public native boolean native_createWatcher();
    public native boolean native_connectToMonitor();
    public native boolean native_disconnectToMonitor();
    public native long native_tickStats();
}