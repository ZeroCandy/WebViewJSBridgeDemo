package com.github.zerocandy.webviewjsbridgedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private BridgeWebView mBridgeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBridgeWebView = (BridgeWebView) findViewById(R.id.webview);
        mBridgeWebView.loadUrl("file:///android_asset/demo1.html");
        // 设置DefaultHandler接收来自JS的全部数据
        mBridgeWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG,"jsdata-全局数据："+data);
                function.onCallBack("response data from java - Default");
            }
        });
        // 注册指定函数接收来及JS的指定数据
        mBridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG,"jsdata:" + data);
                function.onCallBack("response data from java");
            }
        });
    }

    /**
     * 发送数据给JS指定方法接收
     * @param view
     */
    protected void sendMsg2JSMethod(View view){
        mBridgeWebView.callHandler("functionInJs","msg from java to functionInJs",new CallBackFunction(){
            @Override
            public void onCallBack(String data) {
                Log.i(TAG,"jsresponse:"+data);
            }
        });
    }

    /**
     * 发送数据给JS默认方法接收
     * @param view
     */
    protected void sendMsg2JSDefault(View view){
        mBridgeWebView.send("msg from java to js default", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i(TAG,"jsresponse - default:" + data);
            }
        });
    }
}
