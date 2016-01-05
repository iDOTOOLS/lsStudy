package com.dianxinos.lockscreen_sdk;

import java.lang.ref.*;
import java.util.*;
import android.util.*;

public class TestMemoryLeak {

    static private class IdentityWeakReference extends WeakReference {
        public String mIdentity;
        public int mIndex;
        public IdentityWeakReference(Object r, String identity, int index) {
            super(r, sQueue);
            this.mIdentity = identity;
            this.mIndex = index;
        }
    }

    static final private boolean IS_TEST = false;
    static final private String TAG = "TestMemoryLeak";

    static private ReferenceQueue sQueue;
    static private List<IdentityWeakReference> sReference;
    static private Thread sTestThread;
    static private int sIndexSeed;

    static public void addObjectMonitor(Object obj, String identity) {
        if (IS_TEST) {
            synchronized (TestMemoryLeak.class) {
                if (sQueue == null) {
                    sQueue = new ReferenceQueue();
                    sReference = new ArrayList();

                    sTestThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (;;) {
                                try {
                                    IdentityWeakReference finilizedRef = (IdentityWeakReference)sQueue.remove();
                                    if (finilizedRef == null)
                                        break;
                                    Log.d(TAG, "object ----: "+finilizedRef.mIndex + " - " +finilizedRef.mIdentity);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    sTestThread.setDaemon(true);
                    sTestThread.start();
                }

                IdentityWeakReference ref = new IdentityWeakReference(obj, identity, ++sIndexSeed);
                sReference.add(ref);
                Log.d(TAG, "object ++++: "+ref.mIndex + " - " +ref.mIdentity);
            }
        }
    }

    static public void gcAndFinalization() {
        if (IS_TEST) {
            System.gc();
            System.runFinalization();
        }
    }
}
