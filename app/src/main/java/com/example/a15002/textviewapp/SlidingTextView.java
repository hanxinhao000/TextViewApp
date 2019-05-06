package com.example.a15002.textviewapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Scroller;
import android.widget.Toast;


public class SlidingTextView extends View {

    /**
     * 分为3种路径
     * <p>
     * <p>
     * 1.字体的路径
     * <p>
     * 2.背景的路径
     */

    private String mContent = "请设置字体";
    private Context mContext;
    private int mBackColor = Color.parseColor("#00000000");
    private int mTextColor = Color.parseColor("#007FFF");
    private Paint mTextPaint;
    private Paint mBackPaint;
    private Scroller mScroller;


    /**
     * 该动画判断是开始动画还是结束动画
     */
    private boolean isStart = false;


    private int textFontSize = 55;

    /**
     * 左边距
     */
    private int leftLen = 30;

    private int leftLenAnim = 0;

    private int width;

    private int height;

    //文字总宽度
    private int textSizeWidth = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (leftLenAnim == 0) {
                return;
            }


            Log.e("leftLenAnim", "handleMessage: " + leftLenAnim);
            leftLenAnim = 0;
            if (isStart) {
                mScroller.startScroll((leftLen + textSizeWidth), 0, -((leftLen + textSizeWidth) * 2 - (textSizeWidth)), 0, 1000);
                isStart = false;
            }
            invalidate();
        }
    };


    public SlidingTextView(Context context) {
        super(context);
        initView(context);
    }

    public SlidingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SlidingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context mContext) {

        this.mContext = mContext;
        mTextPaint = new Paint();
        mTextPaint.setTextSize(textFontSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mBackPaint = new Paint();
        mBackPaint.setColor(mBackColor);
        mScroller = new Scroller(mContext);


    }


    /**
     * 计量文字总宽度
     *
     * @param content
     */
    private void calculateString(String content) {


        char[] chars = content.toCharArray();

        int temp = 0;

        for (int i = 0; i < chars.length; i++) {

            //判断是否是 a-z
            if (chars[i] > 'a' && chars[i] < 'z') {
                temp += (textFontSize / 2);
                continue;
            }
            //判断是否是 A-Z
            if (chars[i] > 'A' && chars[i] < 'Z') {
                temp += (textFontSize / 2);
                continue;
            }
            //判断是否是 0-9
            if (chars[i] > '0' && chars[i] < '9') {
                temp += (textFontSize / 2);
                continue;
            }
            temp += textFontSize;

        }
        textSizeWidth = temp;

    }

    /**
     * 设置字体
     *
     * @param content
     */

    public void setText(String content) {
        mContent = content;
        calculateString(content);
        invalidate();

    }



    public void rolling(float rolling) {

        Log.e("anim", "onRolling: " + rolling);

    }


    /**
     * 设置字体颜色
     */

    public void setTextColor(int color) {
        invalidate();
        mTextColor = color;


    }

    /**
     * 设置背景
     *
     * @param color
     */
    public void setBackground(int color) {
        mBackColor = color;
        invalidate();
    }

    /**
     * 显示动画
     */

    public void accAnimation() {


        isStart = true;
        mScroller.startScroll(
                -( textSizeWidth),
                0,
                (leftLen + textSizeWidth) * 2,
                0,
                1000
        );

        invalidate();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }

    private void onDrawText(Canvas canvas) {

        canvas.drawRect(
                -((leftLen*2) + textSizeWidth)
                        + leftLenAnim,
                0,
                0 + leftLenAnim ,
                height,
                mBackPaint);
        //canvas.drawRect(0, 0, (leftLen + (mContent.length() * textFontSize))+ leftLenAnim, height, mBackPaint);

        Log.e("height", "height: " + height);
        if (height != 0) {
            //canvas.drawText(mContent, -(leftLen + mContent.length() * textFontSize) + (leftLenAnim + leftLen), (height / 2) + (textFontSize / 2), mTextPaint);
            canvas.drawText(mContent, -((leftLen*2) + textSizeWidth) + (leftLenAnim + leftLen), (height / 2) + (textFontSize / 2), mTextPaint);
            // canvas.drawText(mContent, (leftLen ), (height / 2) + (textFontSize / 2), mTextPaint);
        } else {
            canvas.drawText(mContent, -((leftLen*2) + textSizeWidth) + (leftLenAnim + leftLen), textFontSize, mTextPaint);
            // canvas.drawText(mContent, (leftLen + (mContent.length() * textFontSize)), textFontSize, mTextPaint);
        }

    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {

            leftLenAnim = mScroller.getCurrX();

            invalidate();
        } else {
            if (leftLenAnim != 0)

                mHandler.sendEmptyMessageDelayed(0, 1000);
        }

        //

        Log.e("mScroller", "computeScroll: " + mScroller.getCurrX());
    }
}
