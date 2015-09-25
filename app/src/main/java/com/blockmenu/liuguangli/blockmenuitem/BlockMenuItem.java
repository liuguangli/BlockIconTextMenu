package com.blockmenu.liuguangli.blockmenuitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuguangli on 15/9/24.
 */
public class BlockMenuItem extends View {

    private static final int DEFAULT_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_BORDER_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_SIZE = 16;
    private float mTopBorder ;
    private float mBottomBorder;
    private int   mBorderColor;
    private int   mTextColor;
    private float mIconMargin;
    private float mTextMargin;
    private boolean mTopBorderStartFromText;
    private boolean mBottomBorderStartFromText;
    private boolean mVertical;
    private int mHeight;
    private int mWidth;
    private float mTextSize;
    private float mMainIconSize;
    private float mExtendIconSize;
    private Bitmap mIcon;
    private String mText;
    private Bitmap mExtendIcon;
    private Paint mBorderPaint;
    private Paint mTextPaint;
    public BlockMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr( context,  attrs);
    }

    public BlockMenuItem(Context context, AttributeSet attrs, int defStyleRes) {
        super(context, attrs, defStyleRes);
        initAttr(context, attrs);
        mWidth = getWidth();
        mHeight = getHeight();

    }
    private void initAttr(Context context,AttributeSet attrs){
        mBorderPaint = new Paint();
        mTextPaint = new Paint();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BlockMenuItem);
        mVertical = ta.getBoolean(R.styleable.BlockMenuItem_vertical,false);
        mTopBorder = ta.getDimension(R.styleable.BlockMenuItem_topBorder, 0);
        mBottomBorder = ta.getDimension(R.styleable.BlockMenuItem_bottomBorder, 0);
        mBorderColor = ta.getColor(R.styleable.BlockMenuItem_borderColor, DEFAULT_BORDER_COLOR);
        mTextColor = ta.getColor(R.styleable.BlockMenuItem_textColor, DEFAULT_TEXT_COLOR);
        mTopBorderStartFromText = ta.getBoolean(R.styleable.BlockMenuItem_topBorderStartFromText, false);
        mBottomBorderStartFromText = ta.getBoolean(R.styleable.BlockMenuItem_bottomBorderStartFromText, false);
        mIconMargin = ta.getDimension(R.styleable.BlockMenuItem_IconMargin, 0);
        mTextSize = ta.getDimension(R.styleable.BlockMenuItem_textSize, DEFAULT_TEXT_SIZE);
        mTextMargin = ta.getDimension(R.styleable.BlockMenuItem_textMargin, 0);
        mMainIconSize = ta.getDimensionPixelSize(R.styleable.BlockMenuItem_mainIconSize, 0);
        mExtendIconSize = ta.getDimensionPixelSize(R.styleable.BlockMenuItem_extendIconSize, 0);
        mText = ta.getString(R.styleable.BlockMenuItem_text);
        BitmapDrawable lBitmapDrawable = (BitmapDrawable) ta.getDrawable(R.styleable.BlockMenuItem_mainIcon);
        if (lBitmapDrawable != null){
            mIcon = lBitmapDrawable.getBitmap();
        }
        BitmapDrawable rBitmapDrawable = (BitmapDrawable) ta.getDrawable(R.styleable.BlockMenuItem_extendIcon);
        if (rBitmapDrawable != null){
            mExtendIcon = rBitmapDrawable.getBitmap();
        }


        mBorderPaint.setStyle(Paint.Style.STROKE);//设置非填充
        mBorderPaint.setAntiAlias(true);//锯齿不显示
        mBorderPaint.setColor(mBorderColor);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mVertical){
            showVertical(canvas);
        } else {
            showHorizontal(canvas);
        }



    }

    private void showVertical(Canvas canvas) {
        if (mIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mIcon.getWidth(), mIcon.getHeight());
            mMainIconSize = mMainIconSize == 0?src.width():mMainIconSize;
            float scale = mMainIconSize/new Float(src.width());
            Rect dest = new Rect();
            int startX = mWidth/2- (int)(src.width()*scale)/2;
            int startY = (int)mIconMargin;
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mIcon,src,dest,mTextPaint);

        }



        if (mText != null) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
            canvas.drawText(mText, mWidth / 2 - rect.width() / 2, mHeight - mTextMargin-rect.height()/2, mTextPaint);
        }

    }

    private void showHorizontal(Canvas canvas) {
        if (mIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mIcon.getWidth(), mIcon.getHeight());
            mMainIconSize = mMainIconSize == 0?src.width():mMainIconSize;
            float scale = mMainIconSize/new Float(src.width());
            Rect dest = new Rect();
            int startX = (int)mIconMargin;
            int startY = (int)(mHeight/2- src.height()*scale/2);
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mIcon,src,dest,mTextPaint);
            mTextMargin += src.width() + mIconMargin;
        }

        if (mExtendIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mExtendIcon.getWidth(), mExtendIcon.getHeight());
            mExtendIconSize = mExtendIconSize == 0?src.width():mExtendIconSize;
            Rect dest = new Rect();
            int startX = (int)(mWidth- mExtendIcon.getWidth()-mIconMargin);
            int startY = (int)(mHeight/2- mExtendIconSize/2);
            float scale = mExtendIconSize/new Float(src.width());
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mExtendIcon,src,dest,mTextPaint);

        }

        if (mText != null) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(mText,0,mText.length(),rect);
            canvas.drawText(mText, mTextMargin, mHeight/2+rect.height()/4, mTextPaint);
        }

        if (mTopBorder > 0){
            mBorderPaint.setStrokeWidth(mTopBorder);
            canvas.drawLine(mTopBorderStartFromText?mTextMargin:0, 0,mWidth, 0, mBorderPaint);

        }

        if (mBottomBorder > 0){
            mBorderPaint.setStrokeWidth(mBottomBorder);
            canvas.drawLine(mBottomBorderStartFromText?mTextMargin:0,mHeight-mBottomBorder,mWidth,mHeight-mBottomBorder,mBorderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }
}
