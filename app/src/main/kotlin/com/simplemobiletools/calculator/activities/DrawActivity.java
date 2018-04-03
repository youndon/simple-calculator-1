package com.simplemobiletools.calculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import com.simplemobiletools.calculator.R;
import com.simplemobiletools.calculator.helpers.GetGraphTask;
import com.simplemobiletools.calculator.views.SketchSheetView;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

public class DrawActivity extends AppCompatActivity {

    private SketchSheetView equationSketchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        RelativeLayout relativeLayout = findViewById(R.id.draw_layout);
        equationSketchView = new SketchSheetView(DrawActivity.this);
        relativeLayout.addView(equationSketchView, new LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        findViewById(R.id.btn_draw_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equationSketchView.clearSketch();
            }
        });

        final DrawActivity activity = this;

        findViewById(R.id.btn_draw_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do web request
                    new GetGraphTask(equationSketchView.exportScgink(),activity).execute();
                }
        });

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toggleWebView(null);
            }
        });
    }

    @TestOnly
    public void supersedeSketchSheetView(SketchSheetView equationSketchView) {
        this.equationSketchView = equationSketchView;
    }

    public void toggleWebView(@Nullable String uri){
        WebView view = findViewById(R.id.wolfram_view);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setAppCacheEnabled(true);

        view.setWebViewClient(new WebViewClient());

        LinearLayout buttons = findViewById(R.id.draw_ui);
        RelativeLayout canvas = findViewById(R.id.draw_layout);
        Button done = findViewById(R.id.done);
        if(view.getVisibility() == View.GONE && uri != null){
            view.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.GONE);
            canvas.setVisibility(View.GONE);
            view.loadUrl(uri);
        }
        else{
            view.setVisibility(View.GONE);
            buttons.setVisibility(View.VISIBLE);
            canvas.setVisibility(View.VISIBLE);
            done.setVisibility(View.GONE);
            equationSketchView.clearSketch();
            view.invalidate();
        }
    }
}