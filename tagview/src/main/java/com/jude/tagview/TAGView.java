package com.jude.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
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
    private int paddingLeft;
    private int paddingTop;
    private int paddingBottom;
    private int paddingRight;

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
        LayoutInflater.from(getContext()).inflate(R.layout.tag_view,this,true);
        mImageView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TAGView);
        try {
            setIcon(a.getResourceId(R.styleable.TAGView_tag_icon, 0));
            setText(a.getString(R.styleable.TAGView_tag_text));
            setTextColor(a.getColor(R.styleable.TAGView_tag_text_color, Color.WHITE));
            mPaint.setColor(a.getColor(R.styleable.TAGView_tag_color, Color.RED));
            radius = a.getDimension(R.styleable.TAGView_tag_radius, dip2px(2));

            dividerWidth = (int) a.getDimension(R.styleable.TAGView_tag_divider, dip2px(2));
            paddingLeft=paddingRight=paddingTop=paddingBottom
                    =(int) a.getDimension(R.styleable.TAGView_tag_padding, dip2px(2));
            paddingLeft = (int) a.getDimension(R.styleable.TAGView_tag_padding_left, dip2px(2));
            paddingRight = (int) a.getDimension(R.styleable.TAGView_tag_padding_right, dip2px(2));
            paddingTop = (int) a.getDimension(R.styleable.TAGView_tag_padding_top, dip2px(0));
            paddingBottom = (int) a.getDimension(R.styleable.TAGView_tag_padding_bottom, dip2px(0));


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
        Log.i("TAGView", "onMeasure");
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);


        int inWidth = width-paddingLeft-paddingRight;
        int inHeight = height-paddingTop-paddingBottom;
        if (mImageView.getVisibility()==VISIBLE)
        mImageView.measure(
                MeasureSpec.makeMeasureSpec(inWidth,widthModel),
                MeasureSpec.makeMeasureSpec(inHeight,heightModel)
        );
        //int tw = width - mImageView.getMeasuredWidth() - ((mTextView.getVisibility()==VISIBLE && mImageView.getVisibility()==VISIBLE)?dividerWidth:0);
        if (mTextView.getVisibility()==VISIBLE)
        mTextView.measure(
                MeasureSpec.makeMeasureSpec(inWidth,widthModel),
                MeasureSpec.makeMeasureSpec(inHeight,heightModel)
        );
        Log.i("TAGView", "!TEXT "+ mTextView.getText() + " tw" + mTextView.getMeasuredWidth() + " th" + mTextView.getMeasuredHeight());
        int widthTotal = mImageView.getMeasuredWidth()+mTextView.getMeasuredWidth()+paddingLeft+paddingRight+((mTextView.getVisibility()==VISIBLE && mImageView.getVisibility()==VISIBLE)?dividerWidth:0);
        int heightTotal = Math.max(mImageView.getMeasuredHeight(),mTextView.getMeasuredHeight())+paddingTop+paddingBottom;

        if (widthModel != MeasureSpec.EXACTLY){
            int rewidth = ((widthModel == MeasureSpec.AT_MOST)?Math.min(widthTotal, width):widthTotal);
            int reheight = ((heightModel == MeasureSpec.AT_MOST)?Math.min(heightTotal, height):heightTotal);
            Log.i("TAGView","!EXACTLY"+" w"+rewidth+" h"+reheight);
            setMeasuredDimension(rewidth, reheight);
        }else {
            Log.i("TAGView","EXACTLY"+" w"+width+" h"+height);
            setMeasuredDimension(width,height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left,height = bottom - top;
        int l = paddingLeft,r = width-paddingRight,t = paddingTop,b = height-paddingBottom;
        mImageView.layout(l, t, l + mImageView.getMeasuredWidth(), b);



        if (mTextView.getVisibility()==VISIBLE&&mImageView.getVisibility()==VISIBLE){
            Log.i("TAGView", "!TEXTLayout " + mTextView.getText() + " l" + l+mImageView.getMeasuredWidth()+dividerWidth +" t"+t+" r"+r+" b"+b);

            mTextView.layout(l+mImageView.getMeasuredWidth()+dividerWidth,t,r,b);
        }else {
            Log.i("TAGView", "!TEXTLayout " + mTextView.getText() + " l" + l+mImageView.getMeasuredWidth() +" t"+t+" r"+r+" b"+b);

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
