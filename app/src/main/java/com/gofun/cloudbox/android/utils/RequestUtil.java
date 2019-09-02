package com.gofun.cloudbox.android.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gofun.cloudbox.android.entity.ResultInfo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.gofun.cloudbox.android.utils.HttpConstant.CODE_OK;
import static com.gofun.cloudbox.android.utils.HttpConstant.DESC_UNKNOW;
import static com.gofun.cloudbox.android.utils.HttpConstant.PARAMETER_DEVICE_ID;
import static com.gofun.cloudbox.android.utils.HttpConstant.PARAMETER_OS_VERSION;
import static com.gofun.cloudbox.android.utils.HttpConstant.PARAMETER_TIMESTAMP;
import static java.lang.String.valueOf;

public class RequestUtil implements Callback {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = RequestUtil.class.getSimpleName();
    private static final long timeout = 30 * 1000;
    private IHttpResponse onNetCallback;
    private Handler handler;

    public void setiHttpResponse(IHttpResponse iHttpResponse) {
        this.onNetCallback = iHttpResponse;
    }

    public void request(String url, Map<String, String> paramater, IHttpResponse iHttpResponse) {
    }

    private OkHttpClient okHttpClient;
    private Call call;

    public RequestUtil() {
        buildClient();
    }

    public RequestUtil(IHttpResponse onNetCallback) {
        buildClient();
        this.onNetCallback = onNetCallback;
    }

    private void buildClient() {
        handler = new Handler(Looper.getMainLooper());
        okHttpClient = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .build();
    }

    public void requestPost(String url, Map<String, Object> parameters, IHttpResponse iHttpResponse) {
        this.onNetCallback = iHttpResponse;
        requestPost(url, parameters);
    }

    public void requestPost(String url, Map<String, Object> parameters) {
        Request request;
        if (parameters != null && !parameters.isEmpty()) {
            RequestBody jsonBody = RequestBody.create(MEDIA_TYPE_JSON, JSON.toJSONString(parameters));
            request = addHeaders().url(url).post(jsonBody).build();
        } else {
            request = addHeaders().url(url).build();
        }
        call = okHttpClient.newCall(request);
        call.enqueue(this);
    }

    public void uploadFile(File file, String fileType, Map<String, Object> map) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse(fileType), file);
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        RequestBody rb = requestBody.build();
        Request request = addHeaders().url(HttpConstant.FILE_UPLOAD_SERVICE).post(rb).build();
        call = okHttpClient.newCall(request);
        call.enqueue(this);
    }

    private void sendTestJson(String responseStr) {
        if (onNetCallback != null) {
            ResultInfo resultInfo = JSONObject.parseObject(responseStr, ResultInfo.class);
            Log.e(TAG, "code:" + resultInfo.getCode() + " desc:" + resultInfo.getMessage());
            if (resultInfo.getCode() != CODE_OK) {
                onNetCallback.response(false, resultInfo.getMessage());
            } else {
                Log.e(TAG, "result:" + resultInfo.getMessage());
                onNetCallback.response(true, resultInfo.getMessage());
            }
        }
    }

    /**
     * 统一为请求添加头信息
     */
    private Request.Builder addHeaders() {
        String token = SPUtils.get(SPConstant.SP_TOKEN, "").toString();
        Request.Builder builder = new Request.Builder().addHeader("Connection", "keep-alive")
                .addHeader(PARAMETER_DEVICE_ID, CommonUtils.getDeviceId())
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Content-Type", "application/json")
                .addHeader("xr-auth-token", token)
                .addHeader(PARAMETER_OS_VERSION, CommonUtils.getOsVersion())
                .addHeader(PARAMETER_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return builder;
    }

    /**
     * 添加通用参数
     * deviceId         设备唯一编号
     * appVersion       软件版本
     * osVersion        操作系统版本
     * timestamp        时间戳
     */
    private Map<String, Object> addCommonHeader(Map<String, Object> parameters) {
        parameters.put(PARAMETER_DEVICE_ID, CommonUtils.getDeviceId());
        parameters.put(PARAMETER_OS_VERSION, CommonUtils.getOsVersion());
        parameters.put(PARAMETER_TIMESTAMP, System.currentTimeMillis());
        return parameters;
    }

    public void interrupt() {
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("IOException", "IOException");
        postResult(false, DESC_UNKNOW);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseStr = response.body().string();
        ResultInfo resultInfo = null;
        try {
            resultInfo = JSONObject.parseObject(responseStr, ResultInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (resultInfo == null) {
            postResult(false, "");
            Log.e(TAG, "result:  resultInfo == null");
            return;
        }
        Log.e(TAG, call.request().url().host() + " code:" + resultInfo.getCode() + " desc:" + resultInfo.getResult());
        if (resultInfo.getCode() != CODE_OK) {
            Log.e(TAG, "result:" + resultInfo.getMessage());
            postResult(false, resultInfo.getMessage());
        } else {
            Log.e(TAG, "result:" + resultInfo.getResult());
            postResult(true, resultInfo.getResult());
        }
    }

    public void requestPostByParmarts(String url, Map<String, Object> parmarts, IHttpResponse iHttpResponse) {
        this.onNetCallback = iHttpResponse;
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        Set<String> keys = parmarts.keySet();
        for (String key : keys) {
            if (parmarts.get(key) == null) {
                requestBodyBuilder.add(key, "");
            } else {
                requestBodyBuilder.add(key, parmarts.get(key).toString());
            }
        }
        RequestBody requestBody = requestBodyBuilder.build();
        Request request = addHeaders().url(url).post(requestBody).build();
        call = okHttpClient.newCall(request);
        call.enqueue(this);
    }

    public void requestGetByParmarts(String url, Map<String, Object> parmarts, IHttpResponse iHttpResponse) {
        this.onNetCallback = iHttpResponse;
        Request.Builder reqBuild = addHeaders();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (parmarts != null && !parmarts.isEmpty()) {
            Set<String> keys = parmarts.keySet();
            for (String key : keys) {
                if (null == parmarts.get(key)) {
                    urlBuilder.addQueryParameter(key, "");
                } else {
                    urlBuilder.addQueryParameter(key, parmarts.get(key).toString());
                }
            }
        }
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        call = okHttpClient.newCall(request);
        call.enqueue(this);
    }

    private void postResult(final boolean success, final String result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (onNetCallback != null) {
                    onNetCallback.response(success, result);
                }
            }
        });
    }
}
