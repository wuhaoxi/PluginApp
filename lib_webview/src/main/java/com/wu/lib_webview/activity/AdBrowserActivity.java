package com.wu.lib_webview.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.lib_webview.adbrowser.AdBrowserLayout;
import com.wu.lib_webview.adbrowser.AdBrowserWebViewClient;
import com.wu.lib_webview.adbrowser.Base64Drawables;
import com.wu.lib_webview.adbrowser.BrowserWebview;
import com.wu.lib_webview.utils.Utils;

/**
 * Webview页面
 */
@Route(path = "/webview/web_activity")
public final class AdBrowserActivity extends Activity {

    /**
     * 常量区
     */
    public static final String KEY_URL = "url";

    private BrowserWebview mAdBrowserWebview;
    private AdBrowserLayout mLayout;
    private View mProgress;
    private Button mBackButton;
    private Base64Drawables mBase64Drawables = new Base64Drawables();

    @Autowired(name = KEY_URL)
    protected String mUrl;
    private boolean mIsBackFromMarket = false;
    private AdBrowserWebViewClient.Listener mWebClientListener;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        //添加到arouter中
        ARouter.getInstance().inject(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mLayout = new AdBrowserLayout(this.getApplicationContext());
        mProgress = mLayout.getProgressBar();
        mBackButton = mLayout.getBackButton();
        mAdBrowserWebview = mLayout.getWebView();
        setContentView(mLayout);
        initWebView(mAdBrowserWebview);
        if (bundle != null) {
            mAdBrowserWebview.restoreState(bundle);
        } else {
            mAdBrowserWebview.loadUrl(mUrl);
        }
        initButtonListener(mAdBrowserWebview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview.clearCache(true);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsBackFromMarket) {
            //finish();
        }
        mIsBackFromMarket = true;
        mLayout.getProgressBar().setVisibility(View.INVISIBLE);
    }

    private void initButtonListener(BrowserWebview mAdBrowserWebview) {

    }

    private void initWebView(BrowserWebview webView) {
        mWebClientListener = initAdBrowserClientListener();
        AdBrowserWebViewClient client = new AdBrowserWebViewClient(mWebClientListener);
        webView.setWebViewClient(client);
        webView.getSettings().setBuiltInZoomControls(false);
    }

    private AdBrowserWebViewClient.Listener initAdBrowserClientListener() {
        return new AdBrowserWebViewClient.Listener() {
            @Override
            public void onPageStarted() {
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(boolean canGoBack) {
                mProgress.setVisibility(View.INVISIBLE);
                if (canGoBack) {
                    setImage(mBackButton, mBase64Drawables.getBackActive());
                } else {
                    setImage(mBackButton, mBase64Drawables.getBackInactive());
                }
            }

            @Override
            public void onReceiveError() {
                finish();
            }

            @Override
            public void onLeaveApp() {

            }
        };
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setImage(Button backButton, String imageString) {
        if (Build.VERSION.SDK_INT < 16) {
            backButton.setBackgroundDrawable(Utils.decodeImage(imageString));
        } else {
            backButton.setBackground(Utils.decodeImage(imageString));
        }
    }

    private void initButtonListeners(final WebView webView) {
        mLayout.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    mLayout.getProgressBar().setVisibility(View.VISIBLE);
                    webView.goBack();
                }
            }
        });

        mLayout.getCloseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLayout.getNativeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = webView.getUrl();
                if (uriString != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                    boolean isActivityResolved =
                            getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
                            != null ? true : false;
                    if (isActivityResolved) {
                        startActivity(browserIntent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAdBrowserWebview.canGoBack()) {
                mAdBrowserWebview.goBack();
                return true;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        mAdBrowserWebview.saveState(outState);
        super.onRestoreInstanceState(outState);
    }
}
