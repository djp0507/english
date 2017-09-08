package com.yc.english.news.view.activity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.example.comm_recyclviewadapter.BaseItemDecoration;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.yc.english.R;
import com.yc.english.base.view.BaseToolBar;
import com.yc.english.base.view.FullScreenActivity;
import com.yc.english.base.view.SharePopupWindow;
import com.yc.english.base.view.StateView;
import com.yc.english.news.adapter.NewsDetailAdapter;
import com.yc.english.news.bean.CourseInfoWrapper;
import com.yc.english.news.contract.NewsDetailContract;
import com.yc.english.news.presenter.NewsDetailPresenter;
import com.yc.english.news.view.widget.MediaPlayerView;
import com.yc.english.news.view.widget.NewsScrollView;
import com.yc.english.weixin.model.domain.CourseInfo;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by wanglin  on 2017/9/6 08:32.
 */

public class NewsDetailActivity extends FullScreenActivity<NewsDetailPresenter> implements NewsDetailContract.View {
    @BindView(R.id.mJCVideoPlayer)
    JCVideoPlayerStandard mJCVideoPlayer;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.mLinearLayoutMore)
    LinearLayout mLinearLayoutMore;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_rootView)
    LinearLayout llRootView;

    @BindView(R.id.m_ll_recommend)
    LinearLayout mLlRecommend;
    @BindView(R.id.mMediaPlayerView)
    MediaPlayerView mMediaPlayerView;
    @BindView(R.id.mTextViewTitle)
    TextView mTextViewTitle;
    @BindView(R.id.mTextViewTime)
    TextView mTextViewTime;
    @BindView(R.id.nestedScrollView)
    NewsScrollView nestedScrollView;
    @BindView(R.id.stateView)
    StateView stateView;

    private NewsDetailAdapter newsDetailAdapter;
    private String title;
    private int screenHeight;
    private String id;

    @Override
    public void init() {
        mPresenter = new NewsDetailPresenter(this, this);
        mToolbar.setTitle("");
        mToolbar.setMenuTitle(getString(R.string.share));
        mToolbar.showNavigationIcon();
        mToolbar.setMenuTitleColor(R.color.black_333333);

        if (getIntent() != null) {
            CourseInfo courseInfo = getIntent().getParcelableExtra("info");
            id = courseInfo.getId();
            mPresenter.getWeixinInfo(id);
        }
        Utils.init(this);
        screenHeight = ScreenUtils.getScreenHeight();

        initRecycleView();
        initListener();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }


    private void initData(CourseInfo courseInfo) {
        title = courseInfo.getTitle();
        mTextViewTitle.setText(title);

        String time = null;
        if (!TextUtils.isEmpty(courseInfo.getAdd_time())) {
            time = TimeUtils.millis2String(Long.parseLong(courseInfo.getAdd_time()) * 1000, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }

        mTextViewTime.setText(time);

        String url = courseInfo.getUrl();
        if (courseInfo.getUrl_type() == 1) {
            playAudio(url);
        } else if (courseInfo.getUrl_type() == 2) {
            playVideo(url, courseInfo.getImg());
        }


    }

    private void initListener() {
        mToolbar.setOnItemClickLisener(new BaseToolBar.OnItemClickLisener() {
            @Override
            public void onClick() {
                SharePopupWindow sharePopupWindow = new SharePopupWindow(NewsDetailActivity.this);
                sharePopupWindow.show(llRootView);
            }
        });
        nestedScrollView.setListener(new NewsScrollView.onScrollChangeListener() {
            @Override
            public void onScrollChange(int l, int t, int oldl, int oldt) {
//                LogUtils.e("l-->" + l + "  t-->" + t + "  oldl-->" + oldl + "  oldt-->" + oldt);
                if (t > screenHeight / 2) {
                    mToolbar.setTitle(title);
                } else {
                    mToolbar.setTitle("");
                }
            }
        });
    }


    private void initRecycleView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsDetailAdapter = new NewsDetailAdapter(this, null);
        mRecyclerView.setAdapter(newsDetailAdapter);
        RecyclerView.ItemDecoration itemDecoration = new BaseItemDecoration(this);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private String makeBody(String data) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><meta charset=\"utf-8\" /><meta content=\"yes\" name=\"apple-mobile-web-app-capable\" />\n" +
                "    <meta content=\"yes\" name=\"apple-touch-fullscreen\" />\n" +
                "    <meta content=\"telephone=no,email=no\" name=\"format-detection\" />\n" +
                "    <meta name=\"App-Config\" content=\"fullscreen=yes,useHistoryState=yes,transition=yes\" /><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />");
        stringBuilder.append("</style></head><body>");
        stringBuilder.append(data);
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

    private void initWebView(final CourseInfo courseInfo) {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView.addJavascriptInterface(new JavascriptInterface(), "HTML");
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存 //优先使用缓存:
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        String body = makeBody(courseInfo.getBody());
        webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.HTML.getContentHeight(document.getElementByTagName('html')");
                initData(courseInfo);

            }
        });


    }

    @Override
    public int getLayoutId() {

        return R.layout.common_activity_news_detail;
    }

    /**
     * 播放音频
     *
     * @param path
     */
    private void playAudio(String path) {
        mMediaPlayerView.setVisibility(View.VISIBLE);
        mJCVideoPlayer.setVisibility(View.GONE);
        mMediaPlayerView.setPath(path);
        mMediaPlayerView.startPlay();
    }

    /**
     * 播放视频
     *
     * @param url
     */
    private void playVideo(String url, String imgUrl) {
        mJCVideoPlayer.setVisibility(View.VISIBLE);
        mMediaPlayerView.setVisibility(View.GONE);
        mJCVideoPlayer.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);
        Glide.with(this).load(imgUrl).into(mJCVideoPlayer.thumbImageView);
        mJCVideoPlayer.battery_level.setVisibility(View.GONE);
        mJCVideoPlayer.backButton.setVisibility(View.GONE);

    }


    @Override
    public void showCourseResult(CourseInfoWrapper data) {
        initWebView(data.getInfo());
        if (data.getRecommend() != null && data.getRecommend().size() > 0) {
            newsDetailAdapter.setData(data.getRecommend());
            mLlRecommend.setVisibility(View.VISIBLE);
        } else {
            mLlRecommend.setVisibility(View.GONE);
        }


    }

    @Override
    public void hideStateView() {
        stateView.hide();
    }

    @Override
    public void showNoNet() {
        stateView.showNoNet(nestedScrollView, HttpConfig.NET_ERROR, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getWeixinInfo(id);
            }
        });
    }

    @Override
    public void showNoData() {
        stateView.showNoData(nestedScrollView);
    }

    @Override
    public void showLoading() {
        stateView.showLoading(nestedScrollView);
    }


    private class JavascriptInterface {
        public void getContentHeight(String value) {
            if (value != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webView.getLayoutParams();
                layoutParams.height = Integer.parseInt(value);
                webView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayerView.destory();
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    private JCVideoPlayer.JCAutoFullscreenListener mSensorEventListener;

    private SensorManager mSensorManager;


    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        mSensorManager.unregisterListener(mSensorEventListener);
        JCVideoPlayer.clearSavedProgress(this, null);
    }

}