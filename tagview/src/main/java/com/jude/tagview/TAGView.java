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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mr.Jude on 2015/8/15.
 */
public class TAGView extends FrameLayout {


    private LinearLayout mContainer;
    private ImageView mImageView;
    private TextView mTextView;

    private float radius;

    private Paint mPaint;

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
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_tag,this,false);
        addView(mContainer);
        mImageView = (ImageView) mContainer.findViewById(R.id.icon);
        mTextView = (TextView) mContainer.findViewById(R.id.text);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TAGView);
        try {
            setIcon(a.getResourceId(R.styleable.TAGView_tag_icon, 0));
            setText(a.getString(R.styleable.TAGView_tag_text));
            setTextColor(a.getColor(R.styleable.TAGView_tag_text_color, Color.WHITE));
            mPaint.setColor(a.getColor(R.styleable.TAGView_tag_color, Color.RED));
            radius = a.getDimension(R.styleable.TAGView_tag_radius, dip2px(getContext(),2));
            int padding = (int) a.getDimension(R.styleable.TAGView_tag_padding, dip2px(getContext(), 4));
            mContainer.setPadding(padding, padding, padding, padding);

            int textSize = (int) a.getDimensionPixelSize(R.styleable.TAGView_tag_text_size, dip2px(getContext(), 13));
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }finally {
            a.recycle();
        }
    }

    public void setTextSize(int sp){
        mTextView.setTextSize(sp);
    }

    public void setPadding(int padding){
        mContainer.setPadding(padding,padding,padding,padding);
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
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, mPaint);
        super.draw(canvas);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
