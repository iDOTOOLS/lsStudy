package com.yp.lockscreen.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;


import android.content.Context;

public class DxHttpClient {
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

    private static final String TAG = "DxHttpClient";
    private static final boolean DEBUG = FeatureConfig.DEBUG_LOG;

    private int mConnTimeout;  // milliseconds
    private int mReadTimeout;  // milliseconds

    private DxHttpClient(int connTimeOut, int readTimeOut) {
        mConnTimeout = connTimeOut;
        mReadTimeout = readTimeOut;
    }

    /**
     * @param connTimeout In milliseconds
     * @param readTimeout In milliseconds
     */
    public static DxHttpClient createInstance(int connTimeout, int readTimeout){
        return new DxHttpClient(connTimeout, readTimeout);
    }

    public static DxHttpClient createInstance(){
        return new DxHttpClient(20000, 20000);  // 20 seconds
    }

    public String commonPost(Context cxt, String url, String body, String encoding) throws IOException {
        HttpURLConnection httpConn = null;
        DataOutputStream os = null;

        httpConn = getHttpConnection(cxt, url, true, encoding, null);

        // Send the "POST" request
        try {
            os = new DataOutputStream(httpConn.getOutputStream());
            os.write(body.getBytes(encoding));
            os.flush();
            return getResponse(httpConn);
        } catch (Exception e) {
            // should not be here, but.....
            throw new IOException(e.toString());
        } finally {
            // Must be called before calling HttpURLConnection.disconnect()
            FileHelper.close(os);

            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }

    public String commonGzipPost(Context cxt, String url, byte[] body, String encoding) throws IOException {
        HttpURLConnection httpConn = null;
        DataOutputStream os = null;

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Encoding", "gzip");
        httpConn = getHttpConnection(cxt, url, true, encoding, headers);

        // Send the "POST" request
        try {
            os = new DataOutputStream(httpConn.getOutputStream());
            os.write(body);
            os.flush();
            return getResponse(httpConn);
        } catch (Exception e) {
            // should not be here, but.....
            throw new IOException(e.toString());
        } finally {
            // Must be called before calling HttpURLConnection.disconnect()
            FileHelper.close(os);

            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }

    public String commonGet(Context cxt, String url, String encoding,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders)
            throws IOException, HttpStatusException {
        HttpURLConnection httpConn = null;
        httpConn = getHttpConnection(cxt, url, false, encoding, requestHeaders);
        try {
            httpConn.connect();
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
        try {
            int statusCode = httpConn.getResponseCode();
            if (statusCode != 200) {
                throw new HttpStatusException(statusCode);
            }
            if (responseHeaderNames != null && responseHeaders != null) {
                parseResponseHeaders(httpConn, responseHeaderNames, responseHeaders);
            }
            return getResponse(httpConn);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }

    public void commonDownload(Context cxt, String url, File targetFile, String encoding,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders)
            throws IOException, HttpStatusException {
        HttpURLConnection httpConn = null;
        httpConn = getHttpConnection(cxt, url, false, encoding, requestHeaders);
        try {
            httpConn.connect();
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
        try {
            int statusCode = httpConn.getResponseCode();
            if (statusCode != 200) {
                throw new HttpStatusException(statusCode);
            }
            if (responseHeaderNames != null && responseHeaders != null) {
                parseResponseHeaders(httpConn, responseHeaderNames, responseHeaders);
            }
            saveResponse(httpConn, targetFile);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }

    public byte[] commonGetBytes(Context cxt, String url, String encoding,
            HashMap<String, String> requestHeaders, List<String> responseHeaderNames,
            HashMap<String, String> responseHeaders)
            throws IOException, HttpStatusException {
        HttpURLConnection httpConn = null;
        httpConn = getHttpConnection(cxt, url, false, encoding, requestHeaders);
        try {
            httpConn.connect();
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
        try {
            int statusCode = httpConn.getResponseCode();
            if (statusCode != 200) {
                throw new HttpStatusException(statusCode);
            }
            if (responseHeaderNames != null && responseHeaders != null) {
                parseResponseHeaders(httpConn, responseHeaderNames, responseHeaders);
            }
            return getResponseBytes(httpConn);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }

    private HttpURLConnection getHttpConnection(Context cxt, String url,
            boolean post, String encoding, HashMap<String, String> requestHeaders) throws IOException {
        HttpURLConnection httpConn = NetworkUtils.openHttpURLConnection(cxt, url);
        httpConn.setConnectTimeout(mConnTimeout);
        httpConn.setReadTimeout(mReadTimeout);
        httpConn.setDoInput(true);
        httpConn.setUseCaches(false);
        httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
        httpConn.setRequestProperty("Charset", encoding);
        if (requestHeaders != null) {
            addRequestHeaders(httpConn, requestHeaders);
        }
        if (post) {
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
        } else {
            httpConn.setRequestMethod("GET");  // by default
        }
        return httpConn;
    }

    private void addRequestHeaders(HttpURLConnection httpConn, HashMap<String, String> requestHeaders) {
        Set<Map.Entry<String, String>> allHeaders = requestHeaders.entrySet();
        for (Map.Entry<String, String> header : allHeaders) {
            httpConn.addRequestProperty(header.getKey(), header.getValue());
        }
    }

    private void parseResponseHeaders(HttpURLConnection httpConn, List<String> headerNames,
            HashMap<String, String> headerNameValues) {
        headerNameValues.clear();
        for (String name : headerNames) {
            String value = httpConn.getHeaderField(name);
            if (value != null) {
                headerNameValues.put(name, value);
            }
        }
    }

    private String getResponse(HttpURLConnection httpConn) throws IOException {
        String contentEncoding = httpConn.getContentEncoding();
        if (DEBUG) {
            LogHelper.d(TAG, "response code: " + httpConn.getResponseCode()
                    + ", encoding: " + contentEncoding
                    + ", method: " + httpConn.getRequestMethod());
        }

        InputStream httpInputStream = null;
        try {
            httpInputStream = httpConn.getInputStream();
        } catch (IllegalStateException e) {
            // ignore
        }
        if (httpInputStream == null) {
            // null will be returned on some phones
            throw new IOException("HttpURLConnection.getInputStream() returned null");
        }

        InputStream is = null;
        if (contentEncoding != null && contentEncoding.contains("gzip")) {
            is = new GZIPInputStream(httpInputStream);
        } else if (contentEncoding != null && contentEncoding.contains("deflate")) {
            is = new InflaterInputStream(httpInputStream);
        } else {
            is = httpInputStream;
        }

        // Read the response content
        try {
            byte[] responseContent = readAllBytes(is);
            return new String(responseContent);  // TODO default encoding
        } finally {
            // Must be called before calling HttpURLConnection.disconnect()
            FileHelper.close(is);
        }
    }

    private void saveResponse(HttpURLConnection httpConn, File targeFile) throws IOException {
        String contentEncoding = httpConn.getContentEncoding();
        if (DEBUG) {
            LogHelper.d(TAG, "response code: " + httpConn.getResponseCode()
                    + ", encoding: " + contentEncoding
                    + ", method: " + httpConn.getRequestMethod());
        }

        InputStream httpInputStream = null;
        try {
            httpInputStream = httpConn.getInputStream();
        } catch (IllegalStateException e) {
            // ignore
        }
        if (httpInputStream == null) {
            // null will be returned on some phones
            throw new IOException("HttpURLConnection.getInputStream() returned null");
        }

        InputStream is = null;
        if (contentEncoding != null && contentEncoding.contains("gzip")) {
            is = new GZIPInputStream(httpInputStream);
        } else if (contentEncoding != null && contentEncoding.contains("deflate")) {
            is = new InflaterInputStream(httpInputStream);
        } else {
            is = httpInputStream;
        }

        // Read the response content
        try {
            FileHelper.saveStreamToFile(is, targeFile);
        } finally {
            // Must be called before calling HttpURLConnection.disconnect()
            FileHelper.close(is);
        }
    }

    private byte[] getResponseBytes(HttpURLConnection httpConn) throws IOException {
        String contentEncoding = httpConn.getContentEncoding();
        if (DEBUG) {
            LogHelper.d(TAG, "response code: " + httpConn.getResponseCode()
                    + ", encoding: " + contentEncoding
                    + ", method: " + httpConn.getRequestMethod());
        }

        InputStream httpInputStream = null;
        try {
            httpInputStream = httpConn.getInputStream();
        } catch (IllegalStateException e) {
            // ignore
        }
        if (httpInputStream == null) {
            // null will be returned on some phones
            throw new IOException("HttpURLConnection.getInputStream() returned null");
        }

        InputStream is = null;
        if (contentEncoding != null && contentEncoding.contains("gzip")) {
            is = new GZIPInputStream(httpInputStream);
        } else if (contentEncoding != null && contentEncoding.contains("deflate")) {
            is = new InflaterInputStream(httpInputStream);
        } else {
            is = httpInputStream;
        }

        // Read the response content
        try {
            byte[] responseContent = readAllBytes(is);
            return responseContent;
        } finally {
            // Must be called before calling HttpURLConnection.disconnect()
            FileHelper.close(is);
        }
    }

    public static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bytesBuf = new ByteArrayOutputStream(1024);
        int bytesRead = 0;
        byte[] readBuf = new byte[1024];
        while ((bytesRead = is.read(readBuf, 0, readBuf.length)) != -1) {
            bytesBuf.write(readBuf, 0, bytesRead);
        }
        return bytesBuf.toByteArray();
    }
}
