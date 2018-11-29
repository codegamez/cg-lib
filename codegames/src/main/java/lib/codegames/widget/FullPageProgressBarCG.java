package lib.codegames.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import lib.codegames.R;
import lib.codegames.graphics.ColorCG;
import lib.codegames.graphics.SizeCG;

public class FullPageProgressBarCG extends RelativeLayout {

    private enum Mode {ACTIVITY, VIEW}
    private FrameLayout rootView;
    private ProgressBarCG loadingBar;
    private TextViewCG loadingTextView;
    private final int defLoadingBackGroundColor = ColorCG.parseColor("#000000");
    private final int defLoadingTextSize = SizeCG.Companion.dp2Px(30);
    private final int defLoadingBarColor = ColorCG.parseColor("#3F51B5");
    private final int defLoadingBarWidth = SizeCG.Companion.dp2Px(20);
    private final int defLoadingTextColor = ColorCG.parseColor("#FAFAFA");
    private Mode mode;
    private boolean loading = false;
    private float loadingBackGroundVisibility = 0.9f;
    private int loadingBackGroundColor = defLoadingBackGroundColor;

    public FullPageProgressBarCG(Context context) {
        super(context);
        mode = Mode.ACTIVITY;

        setupRootView();
        setupContent();
    }

    public FullPageProgressBarCG(Context context, AttributeSet attrs) {
        super(context, attrs);
        mode = Mode.VIEW;

        setupRootView();
        setupContent();

        initialise(context, attrs);
    }

    public FullPageProgressBarCG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mode = Mode.VIEW;

        setupRootView();
        setupContent();

        initialise(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FullPageProgressBarCG(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mode = Mode.VIEW;

        setupRootView();
        setupContent();

        initialise(context, attrs);
    }

    private void initialise(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.FullPageProgressBarCG);

        boolean loading = attributeArray.getBoolean(R.styleable.FullPageProgressBarCG_loading, false);
        int loadingBarColor = attributeArray.getColor(R.styleable.FullPageProgressBarCG_barColor, defLoadingBarColor);
        int loadingBarWidth = attributeArray.getDimensionPixelSize(R.styleable.FullPageProgressBarCG_barWidth, defLoadingBarWidth);
        int loadingTextColor = attributeArray.getColor(R.styleable.FullPageProgressBarCG_loadingTextColor, defLoadingTextColor);
        int loadingBackGroundColor = attributeArray.getColor(R.styleable.FullPageProgressBarCG_loadingBackGroundColor, defLoadingBackGroundColor);
        float loadingBackGroundVisibility = attributeArray.getFloat(R.styleable.FullPageProgressBarCG_loadingBackGroundVisibility, this.loadingBackGroundVisibility);
        int loadingTextSize = attributeArray.getDimensionPixelSize(R.styleable.FullPageProgressBarCG_loadingTextSize, defLoadingTextSize);
        String loadingText = attributeArray.getString(R.styleable.FullPageProgressBarCG_loadingText);
        attributeArray.recycle();

        setLoadingTextSize(loadingTextSize);
        setLoadingBarColor(loadingBarColor);
        setLoadingTextColor(loadingTextColor);
        setLoadingText(loadingText);
        setLoadingBackGroundColor(loadingBackGroundColor);
        setLoadingBackGroundVisibility(loadingBackGroundVisibility);
        setLoadingBarWidth(loadingBarWidth);
        setLoading(loading);

    }

    private void setupRootView() {

        if(mode.equals(Mode.ACTIVITY) && getContext() instanceof Activity) {

            rootView = new FrameLayout(getContext());

            FrameLayout.LayoutParams layoutParams =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            rootView.setLayoutParams(layoutParams);

            rootView.setVisibility(View.GONE);

            rootView.setFocusable(true);
            rootView.setClickable(true);

            ((Activity) getContext()).addContentView(rootView, layoutParams);

        }else if(mode.equals(Mode.VIEW)) {

            rootView = new FrameLayout(getContext());

            FrameLayout.LayoutParams layoutParams =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            rootView.setLayoutParams(layoutParams);

            rootView.setVisibility(View.GONE);

            rootView.setFocusable(true);
            rootView.setClickable(true);

            addView(rootView, layoutParams);

            rootView.bringToFront();
        }

    }

    private void setupContent() {

        if(getContext() == null)
            return;

        int margin = SizeCG.Companion.dp2Px(10);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        rootView.addView(linearLayout);
        FrameLayout.LayoutParams linearLayoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        linearLayoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        linearLayoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        linearLayoutParams.setMargins(margin, margin, margin, margin);
        linearLayoutParams.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(linearLayoutParams);

        loadingBar = new ProgressBarCG(getContext());
        loadingBar.setMode(ProgressBarCG.Mode.MODE_CIRCLE);
        linearLayout.addView(loadingBar, 0);
        LinearLayout.LayoutParams progressBarParams = (LinearLayout.LayoutParams) loadingBar.getLayoutParams();
        int minSize = Math.min(SizeCG.Companion.getScreenWidthPX(), SizeCG.Companion.getScreenWidthPX());
        progressBarParams.width = minSize / 3;
        progressBarParams.height = minSize / 3;
        progressBarParams.gravity = Gravity.CENTER;
        progressBarParams.bottomMargin = margin;
        loadingBar.setLayoutParams(progressBarParams);


        loadingTextView = new TextViewCG(getContext());
        linearLayout.addView(loadingTextView, 1);
        setLoadingText("hello");
        LinearLayout.LayoutParams textViewParams = (LinearLayout.LayoutParams) loadingTextView.getLayoutParams();
        textViewParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        textViewParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        textViewParams.gravity = Gravity.CENTER;
        textViewParams.topMargin = margin;
        loadingTextView.setLayoutParams(textViewParams);
        setLoadingTextSize(defLoadingTextSize);
        setLoadingTextColor(defLoadingTextColor);

    }

    @Override
    public void addView(View child) {
        super.addView(child);
        rootView.bringToFront();
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        rootView.bringToFront();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        rootView.bringToFront();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        rootView.bringToFront();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        rootView.bringToFront();
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        if(loading) show();
        else hide();
    }

    public void setLoadingBarColor(int color) {
        loadingBar.setBarColor(color);
    }

    public void setLoadingBarColorResource(int resource) {
        setLoadingBarColor(ColorCG.parseColorResource(getContext(), resource));
    }

    public int getLoadingBarColor() {
        return loadingBar.getBarColor();
    }

    public void setLoadingBarWidth(int width) {
        loadingBar.setBarWidth(width);
    }

    public int getLoadingBarWidth() {
        return loadingBar.getBarWidth();
    }

    public void setLoadingBackGroundColor(int color) {
        this.loadingBackGroundColor = color;
        rootView.setBackgroundColor(ColorCG.parseTransparentColor(this.loadingBackGroundColor, this.loadingBackGroundVisibility));
    }

    public void setLoadingBackGroundColorResource(int resource) {
        this.loadingBackGroundColor = ColorCG.parseColorResource(getContext(), resource);
        setLoadingBackGroundColor(this.loadingBackGroundColor);
    }

    public void setLoadingBackGroundVisibility(float visibility) {
        this.loadingBackGroundVisibility = visibility;
        setLoadingBackGroundColor(this.loadingBackGroundColor);
    }

    public float getLoadingBackGroundVisibility() {
        return loadingBackGroundVisibility;
    }

    public void setLoadingTextSize(int size) {
        loadingTextView.setTextSize(size);
    }

    public int getLoadingTextSize() {
        return (int) loadingTextView.getTextSize();
    }

    public void setLoadingText(String text) {
        loadingTextView.setText(text);
    }

    public String getLoadingText() {
        return loadingTextView.getText().toString();
    }

    public void setLoadingTextColor(int color) {
        loadingTextView.setTextColor(color);
    }

    public void setLoadingTextColorResource(int resource) {
            loadingTextView.setTextColor(ColorCG.parseColorResource(getContext(), resource));
    }

    public void show() {
        loading = true;
        loadingBar.startLoading();
        rootView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        loading = false;
        loadingBar.stopLoading();
        rootView.setVisibility(View.GONE);
    }

}
