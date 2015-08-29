package com.jude.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mr.Jude on 2015/8/15.
 */
public class TAGView extends FrameLayout {

    private ImageView mImageView;
    private TextView mTextView;

    private float radius;

    private Paint mPaint;

    private int dividerWidth;

    public TAGView(Context context) {
        this(context,null);
    }

    public TAGView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TAGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        setWillNotDraw(false);
        mPaint = new Paint();
        LayoutInflater.from(getContext()).inflate(R.layout.view_tag,this,true);
        mImageView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TAGView);
        try {
            setIcon(a.getResourceId(R.styleable.TAGView_tag_icon, 0));
            setText(a.getString(R.styleable.TAGView_tag_text));
            setTextColor(a.getColor(R.styleable.TAGView_tag_text_color, Color.WHITE));
            mPaint.setColor(a.getColor(R.styleable.TAGView_tag_color, Color.RED));
            radius = a.getDimension(R.styleable.TAGView_tag_radius, dip2px(2));

            dividerWidth = (int) a.getDimension(R.styleable.TAGView_tag_divider, dip2px(4));
            setPadding(
                    getPaddingLeft()+dip2px(4),
                    getPaddingTop()+dip2px(4),
                    getPaddingRight()+dip2px(4),
                    getPaddingBottom()+dip2px(4)
            );

            int textSize = a.getDimensionPixelSize(R.styleable.TAGView_tag_text_size, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }finally {
            a.recycle();
        }
    }

    public void setTextSize(int sp){
        mTextView.setTextSize(sp);
    }


    public void setText(String text){
        if (text==null||text.isEmpty()){
            mTextView.setVisibility(GONE);
        }
        else{
            mTextView.setVisibility(VISIBLE);
            mTextView.setText(text);
        }
    }
    public void setTextColor(int color){
        mTextView.setTextColor(color);
    }
    public void setIcon(int res){
        if (res == 0)removeIcon();
        else {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageResource(res);
        }
    }

    public void removeIcon(){
        mImageView.setVisibility(GONE);
    }

    public void setBackgroundColor(int color){
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        if (widthModel != MeasureSpec.EXACTLY){
            int widthTotal = getPaddingLeft()+getPaddingRight()+mImageView.getMeasuredWidth()+mTextView.getMeasuredWidth();
            if (mTextView.getVisibility()==VISIBLE&&mImageView.getVisibility()==VISIBLE)widthTotal+=dividerWidth;
            int rewidth = ((widthModel == MeasureSpec.AT_MOST)?Math.min(widthTotal, width):widthTotal);
            setMeasuredDimension(rewidth,getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left,height = bottom - top;
        int l = getPaddingTop(),r = width-getPaddingRight(),t = getPaddingTop(),b = height-getPaddingBottom();
        mImageView.layout(l,t,l+mImageView.getMeasuredWidth(),b);
        if (mTextView.getVisibility()==VISIBLE&&mImageView.getVisibility()==VISIBLE){
            mTextView.layout(l+mImageView.getMeasuredWidth()+dividerWidth,t,r,b);
        }else {
            mTextView.layout(l+mImageView.getMeasuredWidth(),t,r,b);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, mPaint);
        super.draw(canvas);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
