package com.pc.multipleviewpager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Dave Smith
 * @devunwired
 * Date: 8/17/12
 * PagerContainer
 */
public class PagerContainer extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    boolean mNeedsRedraw = false;

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) getChildAt(0);
        	/*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(400, LayoutParams.FILL_PARENT);
    		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
    	
            mPager.setLayoutParams(new LayoutParams(layoutParams));*/
            mPager.setOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }
    /*public float getPageWidth(int position) {
    // TODO Auto-generated method stub
    return (1 / 1.2f);
    }
*/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int)ev.getX();
                mInitialTouch.y = (int)ev.getY();
            default:
            	int x = mCenter.x - mInitialTouch.x ;
            	int y = mCenter.y - mInitialTouch.y ;
            	System.err.println("--x---"+x);
            	System.err.println("--y---"+y);
            	
            	System.err.println("--mCenter.x---"+mCenter.x);
            	System.err.println("--mCenter.y---"+mCenter.y);
            	System.err.println("--mInitialTouch.x---"+mInitialTouch.x);
            	System.err.println("--mInitialTouch.y---"+mInitialTouch.y);
                ev.offsetLocation(x, y);
                break;
        }

        return mPager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) { }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
