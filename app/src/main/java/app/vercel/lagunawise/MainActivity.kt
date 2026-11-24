package app.vercel.lagunawise

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var splashOverlay: FrameLayout
    private val targetUrl = "https://lagunawise.vercel.app"
    private val mainHandler = Handler(Looper.getMainLooper())
    private var splashDismissed = false

    private val hideSplashRunnable = Runnable {
        hideSplash()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        splashOverlay = findViewById(R.id.splashOverlay)

        splashOverlay.visibility = View.VISIBLE
        splashOverlay.alpha = 1f

        mainHandler.postDelayed(hideSplashRunnable, 5000L)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mainHandler.removeCallbacks(hideSplashRunnable)
                mainHandler.postDelayed({ hideSplash() }, 400L)
            }
        }

        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(targetUrl)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun hideSplash() {
        if (splashDismissed) return
        splashDismissed = true

        splashOverlay.animate()
            .alpha(0f)
            .setDuration(300L)
            .withEndAction {
                splashOverlay.visibility = View.GONE
            }
            .start()
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(hideSplashRunnable)
        splashOverlay.animate().cancel()
        super.onDestroy()
    }
}
