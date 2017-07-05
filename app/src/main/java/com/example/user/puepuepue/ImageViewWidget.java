package com.example.user.puepuepue;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by User on 2016/11/25.
 */
public class ImageViewWidget extends ImageView {
    int mLastMoveX;
    int mLastMoveY;
    float dX, dY;
    public ImageViewWidget(Context context) {
        super(context);
    }

    public ImageViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageViewWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.i("action down","action down");
                mLastMoveX = (int) event.getX();
                mLastMoveY = (int) event.getY();
                dX = this.getX() - event.getRawX();
                return handleActionDownEvenet(event);
            case MotionEvent.ACTION_MOVE:
                Log.i("action move","action move");
                mLastMoveX = x; //保存了X轴方向
                mLastMoveY = y;

                //icon 跟隨x軸移動
                this.animate()
                        .x(event.getRawX() + dX)
                        .setDuration(0)
                        .start();
                invalidate(); //重新绘制
                return true;
            case MotionEvent.ACTION_UP:
                //处理Action_Up事件：  判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。

                return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean handleActionDownEvenet(MotionEvent event) {
        Rect rect = new Rect();
        this.getHitRect(rect);
        boolean isHit = rect.contains((int) event.getX(), (int) event.getY());
        return isHit;
    }
}
