package com.vitiello.android.retrofitgithub.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.vitiello.android.retrofitgithub.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val STARGAZERS_SCHEME = "stargazers"
        const val STARGAZERS_CODE = "code"
        private const val TAG = "LoginActivity"
        private const val WEB_PAGE_URL = "https://github.com/login/oauth/authorize?client_id=%s"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        webView.webViewClient = LoginWebViewClient()
        val webPageUrl = String.format(WEB_PAGE_URL, getString(R.string.client_id))
        webView.loadUrl(webPageUrl)
    }

    open inner class LoginWebViewClient : WebViewClient() {
        /**
         * success uri:
         *  url intercepted=stargazers://stargazers.com/?code=021e1178c4fa4d623955
         *
         * The generic URI syntax is as follows:
         * URI-reference := [scheme ":"] ["//" [userinfo "@"] host [":" port]] path \ [ "?" query ] [ "#" fragment ]
         */
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
            var shouldOverride = false
            val url = request.url.toString()
            if (!TextUtils.isEmpty(url)) {
                Log.d(TAG, "LoginWeb: url intercepted=$url")
                if (request.url.scheme == STARGAZERS_SCHEME) {
                    shouldOverride = true
                    startActivity(Intent(Intent.ACTION_VIEW, request.url))
                    finish()
                }
            }
            return shouldOverride
        }
    }

}