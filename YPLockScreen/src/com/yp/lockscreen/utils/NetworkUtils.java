
package com.yp.lockscreen.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";
    private static final boolean DEBUG = FeatureConfig.DEBUG_LOG;

    /** Same to {@link ConnectivityManager#TYPE_WIMAX} (API 8) */
    private static final int CM_TYPE_WIMAX = 6;
    /** Same to {@link ConnectivityManager#TYPE_ETHERNET} (API 13) */
    private static final int CM_TYPE_ETHERNET = 9;

    /** Same to {@link TelephonyManager#NETWORK_TYPE_EVDO_B} (API 9) 5 Mbps */
    private static final int TM_NETWORK_TYPE_EVDO_B = 12;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_LTE} (API 11) 10+ Mbps */
    private static final int TM_NETWORK_TYPE_LTE = 13;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_EHRPD} (API 11) 1~2 Mbps */
    private static final int TM_NETWORK_TYPE_EHRPD = 14;
    /** Same to {@link TelephonyManager#NETWORK_TYPE_HSPAP} (API 13) 10~20 Mbps */
    private static final int TM_NETWORK_TYPE_HSPAP = 15;

    public static final int NET_TYPE_NONE = -1;
    // Don't touch! The following network types are defined by Server.
    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_2G = 2;
    public static final int NET_TYPE_3G = 3;

    /**
     * @param cxt
     * @return One of values {@link #NET_TYPE_WIFI}, {@link #NET_TYPE_2G},
     *         {@link #NET_TYPE_3G} or {@link #NET_TYPE_NONE}
     */
    public static int getNetworkType(Context cxt) {
        ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null) {
            int type = netInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI
                    || type == CM_TYPE_WIMAX
                    || type == CM_TYPE_ETHERNET) {
                return NET_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                int subType = netInfo.getSubtype();
                if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                        || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_HSUPA
                        || subType == TelephonyManager.NETWORK_TYPE_HSPA
                        || subType == TM_NETWORK_TYPE_EVDO_B
                        || subType == TM_NETWORK_TYPE_LTE
                        || subType == TM_NETWORK_TYPE_EHRPD
                        || subType == TM_NETWORK_TYPE_HSPAP) {
                    return NET_TYPE_3G;
                }
                return NET_TYPE_2G; // Take other data types as 2G
            }
            return NET_TYPE_2G; // Take unknown networks as 2G
        }
        return NET_TYPE_NONE;
    }

    /**
     * Check if there is an active network connection
     */
    public static boolean hasActiveNetwork(Context cxt) {
        ConnectivityManager cm = (ConnectivityManager)
                cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null;
    }

    public static boolean isNetworkAvaialble(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connMgr.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }

    public static boolean isWifiNetworkAvaialble(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connMgr.getActiveNetworkInfo();
        return network != null && ConnectivityManager.TYPE_WIFI == network.getType()
                && network.isConnected();
    }
    /**
     * Get the IP address of the device.
     * @return null may be returned
     */
    public static String getIpAddress(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            if (DEBUG) {
                LogHelper.d(TAG, "Active network found");
            }
            try {
                Enumeration<NetworkInterface> netIter = NetworkInterface.getNetworkInterfaces();
                while (netIter.hasMoreElements()) {
                    NetworkInterface netInterface = netIter.nextElement();
                    Enumeration<InetAddress> inetAddrIter = netInterface.getInetAddresses();
                    while (inetAddrIter.hasMoreElements()) {
                        InetAddress inetAddr = inetAddrIter.nextElement();
                        String ip = inetAddr.getHostAddress();
                        if (!inetAddr.isLoopbackAddress() && !TextUtils.isEmpty(ip)) {
                            if (DEBUG) {
                                LogHelper.d(TAG, "Host name: " + inetAddr.getHostName()
                                        + ", IP: " + ip);
                            }
                            return ip;
                        }
                    }
                }
            } catch (SocketException e) {
                LogHelper.w(TAG, "Failed to get network IP with exception: " + e);
            }
        }
        if (DEBUG) {
            LogHelper.d(TAG, "Failed to get IP address");
        }
        return null;
    }

    /**
     * Open a HTTP connection to the specified URL. Use proxy automatically if needed.
     * @throws IOException
     */
    public static HttpURLConnection openHttpURLConnection(Context ctx, String url) throws IOException {
        String proxyAddr = getProxyAddrIfNeeded(ctx);
        int proxyPort = getProxyPort(ctx);
        if (proxyAddr != null && proxyPort != -1) {
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP,
                    new InetSocketAddress(proxyAddr, proxyPort));
            return (HttpURLConnection) new URL(url).openConnection(proxy);
        } else {
            return (HttpURLConnection) new URL(url).openConnection();
        }
    }

    /**
     * @return Return the proxy address if needed and possible.
     *         If not needed or not possible, null will be returned.
     */
    private static String getProxyAddrIfNeeded(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // TODO: The following method was deprecated.
                //return android.net.Proxy.getDefaultHost();
                return android.net.Proxy.getHost(ctx);
            }
        }
        return null;
    }

    /**
     * @return Return the port number to be used with the proxy host.
     *         Return -1 if there is no proxy for this carrier.
     */
    private static int getProxyPort(Context ctx) {
        // TODO: The following method was deprecated.
        //return android.net.Proxy.getDefaultPort();
        return android.net.Proxy.getPort(ctx);
    }

}
