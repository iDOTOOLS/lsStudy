package com.yp.lockscreen.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.apache.http.protocol.HTTP;


import android.content.Context;

public class HttpUtils {
    private static final String TAG = "DxHttpUtils";
    private static final boolean DEBUG = false;

    public static class HttpStatusException extends IOException {
        private static final long serialVersionUID = 1L;
        private int mStatusCode;

        public HttpStatusException(int statusCode) {
            super("Http status exception-" + statusCode);
            mStatusCode = statusCode;
        }

        public int getStatusCode() {
            return mStatusCode;
        }
    }

    public static final String DEFAULT_ENCODING = "utf-8";

    public static String commonPost(Context cxt, String url, String body) throws IOException {
        return commonPost(cxt, url, body, null);
    }

    public static String commonPost(Context cxt, String url, String body, String extraParams)
            throws IOException {
        DxHttpClient httpClient = DxHttpClient.createInstance();
        String urlWithParams = appendDxUrlParams(cxt, url, extraParams);
        if (DEBUG) {
            LogHelper.d(TAG, "request: " + urlWithParams + ", content size: " + body.length()
                    + ", content: " + body);
        }
        return httpClient.commonPost(cxt, urlWithParams, body, DEFAULT_ENCODING);
    }

    public static String commonGet(Context cxt, String url) throws IOException, HttpStatusException {
        return commonGet(cxt, url, null, null, null, null);
    }

    public static String commonGet(Context cxt, String url, String extraParams)
            throws IOException, HttpStatusException {
        return commonGet(cxt, url, extraParams, null, null, null);
    }

    public static String commonGet(Context cxt, String url, String extraParams,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders) throws IOException, HttpStatusException {
        return commonGet(cxt, url, extraParams, requestHeaders, responseHeaderNames, responseHeaders, true);
    }

    public static String commonGet(Context cxt, String url, String extraParams,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders, boolean appendDxParam) throws IOException, HttpStatusException {
        DxHttpClient httpClient = DxHttpClient.createInstance();
        String urlWithParams = appendDxUrlParams(cxt, url, extraParams, appendDxParam);
        if (DEBUG) {
            LogHelper.d(TAG, "request: " + urlWithParams);
        }
        return httpClient.commonGet(cxt, urlWithParams, DEFAULT_ENCODING,
                requestHeaders, responseHeaderNames, responseHeaders);
    }

    public static void commonDownload(Context cxt, String url, File targetFile) throws IOException {
        commonDownload(cxt, url, targetFile, null, null, null, null);
    }

    public static void commonDownload(Context cxt, String url, File targetFile, String extraParams,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders) throws IOException {
        DxHttpClient httpClient = DxHttpClient.createInstance();
        httpClient.commonDownload(cxt, url, targetFile, HTTP.UTF_8, requestHeaders,
                responseHeaderNames, responseHeaders);
    }

    public static String appendDxUrlParams(Context cxt, String url) {
        return appendDxUrlParams(cxt, url, null, true);
    }

    public static String appendDxUrlParams(Context cxt, String url, String extraParams) {
        return appendDxUrlParams(cxt, url, null, true);
    }

    public static String appendDxUrlParams(Context cxt, String url, String extraParams, boolean appendDxParam) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!url.contains("?")) {
            sb.append('?');
        } else if (!url.endsWith("?")) {
            sb.append("&");
        }
//        if (appendDxParam) {
//            sb.append(DXStatService.getUrlSuffix(cxt));
//        }
        if (extraParams != null) {
            if (appendDxParam) sb.append("&");
            sb.append(extraParams);
        }
        return sb.toString();
    }

    public static String toUrlParams(String key, String value) {
        try {
            return URLEncoder.encode(key, DEFAULT_ENCODING) +  "=" +
                    URLEncoder.encode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            // ignore, won't happen
        }
        return "";
    }
}
