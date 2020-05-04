package loginregistration.learn2crack.com.webviewapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    private SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swiper = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swiper.setColorSchemeColors(Color.rgb(0, 21, 255));
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com.tr");

        //WebView açılan siteyi otomatik olarak mobil uyumlu web sitesine gönderecektir.
        //Bunun sebebi, WebView'in ön tanımlı olarak UserAgent değeri içerisinde mobil bir cihazdan geldiğine dair birtakım bilgiler içermesidir.
        //webview.getSettings().setUserAgentString("Mozilla/5.0(Windows NT 6.1;rv:15.0) Gecko/20120716 Firefox/15.0a2");
        //Mobil uyumlu siteler yerine web sayfalarının normal halini görüntülemek istiyorsak bu kodu yazmak lazım.
        //Firefox uyumlu olarak siteyi yükleyecek. Masaüstü sitesi yükleyecektir.

        final ProgressDialog progress = ProgressDialog.show(this, "Android WebView", "Yükleniyor...", true);
        //ilk parametre progress in hangi activity de gösterileceği,
        //2. parametre dialog un başlığı
        //3. parametre dialog un içeriği
        //4. parametre gösterilip gösterilmiceği
        progress.show();// url yüklenir yüklenmez animasyonu göster
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                if (swiper.isRefreshing())
                    swiper.setRefreshing(false);
                progress.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(getApplicationContext(), "Bir hata oluştu.", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack())
                        webView.goBack();

                    else
                        finish();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
