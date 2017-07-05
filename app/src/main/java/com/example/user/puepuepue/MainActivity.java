package com.example.user.puepuepue;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    ImageView puepue ;
    public int mLastMoveX;
    public int mLastMoveY;
    RelativeLayout baserlay;
    ArrayList<BadGuy> badGuyList = new ArrayList<BadGuy>();
    public float dX;
    public float dY;
    int height;
    int width;
    int pxl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
     initView();
        initData();
        addBadGuy();
    }


    private void addBadGuy() {
        badGuyList.clear();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(95, 95);
        layoutParams.setMargins(30, 60, 0, 0);
        final TextView badGuyView = new TextView(this);
        badGuyView.setText(150+"");
        badGuyView.setGravity(Gravity.CENTER);
                badGuyView.setBackgroundResource(R.color.colorPrimary);
//---------------------------------------------------------------------------


        baserlay.addView(badGuyView, layoutParams);

        final TextView view2 = new TextView(this);
        view2.setText(250+"");
        view2.setBackgroundResource(R.color.colorPrimary);
        view2.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(160, 160);
        layoutParams2.setMargins(500, 300, 0, 0);

        baserlay.addView(view2, layoutParams2);

        final TextView view3 = new TextView(this);
        view3.setText(300+"");
        view3.setGravity(Gravity.CENTER);
        view3.setBackgroundResource(R.color.colorPrimary);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(120, 120);
        layoutParams3.setMargins(300, 800, 0, 0);
        baserlay.addView(view3,layoutParams3);


        ViewTreeObserver observer = baserlay.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Log.i("getLeft", badGuyView.getLeft() + "");
                Log.i("getRight", badGuyView.getRight() + "");

                BadGuy badGuy1 = new BadGuy();
                badGuy1.setBadguyView(badGuyView);
                badGuy1.setHp(150);
                BadGuy badGuy2 = new BadGuy();
                badGuy2.setBadguyView(view2);
                badGuy2.setHp(250);
                BadGuy badGuy3 = new BadGuy();
                badGuy3.setBadguyView(view3);
                badGuy3.setHp(300);


                badGuyList.add(badGuy1);
                badGuyList.add(badGuy2);
                badGuyList.add(badGuy3);

                baserlay.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });

    }
    private void initView() {
//         vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = vi.inflate(R.layout.activity_main, null);

        baserlay = (RelativeLayout)this.findViewById(R.id.baserlay);


        puepue = (ImageView)this.findViewById(R.id.puepue);
        puepue.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Log.i("action down", "action down");
                        mLastMoveX = (int) event.getX();
                        mLastMoveY = (int) event.getY();
                        dX = puepue.getX() - event.getRawX();
                        dY = puepue.getY() - event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("action move", "action move");
                        mLastMoveX = x; //保存了X轴方向
                        mLastMoveY = y;

                        //icon 跟隨x軸移動
                        puepue.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY()+ dY)
                                .setDuration(0)
                                .start();
                        puepue.invalidate(); //重新绘制
                        handleActionDownEvenet(event.getRawX());
                        return true;
                    case MotionEvent.ACTION_UP:
                        //处理Action_Up事件：  判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。

                        return true;
                }
                return false;
            }
        });
        ViewTreeObserver observer = baserlay.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                  int dp= baserlay.getHeight();

                Resources r =getResources();
                 pxl = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dp,
                        r.getDisplayMetrics()
                );

                baserlay.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
    }
    public static float convertDpToPixel(float dp, Context context){
        float px = dp * getDensity(context);
        return px;
    }
    public static float convertPixelToDp(float px, Context context){
        float dp = px / getDensity(context);
        return dp;
    }
    public static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }
    private void initData() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

       Log.i("screen size",height+"");
        Log.i("screen size",convertPixelToDp(height,getApplicationContext())+"");
//        Log.i("screen size",convertDpToPixel(height,getApplicationContext())+"");

    }

    private boolean handleActionDownEvenet(float rawX) {
        TranslateAnimation scale = new TranslateAnimation(Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, -height);
        final View animationBulletView = new View(this);
        final View explodeLocationBulletView = new View(this);
        animationBulletView.setBackgroundResource(R.color.colorAccent);
        explodeLocationBulletView.setBackgroundResource(R.color.white);
        RelativeLayout.LayoutParams bulletParam = new RelativeLayout.LayoutParams(10, 10);
        final RelativeLayout.LayoutParams hitPositionParam = new RelativeLayout.LayoutParams(20, 20);
        int[] locations = new int[2];
        puepue.getLocationOnScreen(locations);
        int x = locations[0];
        int y = locations[1];
        int hitX = x + ((puepue.getRight() - puepue.getLeft()) / 2);

        int hitIndex = 0;
        boolean gotHit = false;
       boolean viewGone  = false;
        TextView removebadGuyView = null;
        bulletParam.setMargins(hitX, y, 0, 0);
       if(badGuyList.size()!=0) {
            for (int i = 0; i < badGuyList.size(); i++) {
                if (badGuyList.get(i).getBadguyView().getLeft() <= hitX && badGuyList.get(i).getBadguyView().getRight() >= hitX) {
                    hitIndex = i;
                    gotHit = true;
                    hitPositionParam.setMargins(hitX, (badGuyList.get(i).getBadguyView().getBottom()), 0, 0);
                    if (badGuyList.get(i).getHp() > 0) {
                        badGuyList.get(i).setHp(badGuyList.get(i).getHp() - 1);

                        scale = new TranslateAnimation(Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,-(y- badGuyList.get(i).getBadguyView().getBottom()));
                    } else {

                            viewGone = true;
                        removebadGuyView=badGuyList.get(i).getBadguyView();
                            badGuyList.remove(i);

                    }
                    break;
                } else {

                    hitPositionParam.setMargins(hitX, 0, 0, 0);

                }

            }
        }else{
            hitPositionParam.setMargins(hitX, 0, 0, 0);
        }
        puepue.invalidate();

        baserlay.addView(animationBulletView, bulletParam);


        final boolean finalGotHit = gotHit;
        final int finalHitIndex = hitIndex;
        final boolean finalViewGone = viewGone;

        final TextView finalRemovebadGuyView = removebadGuyView;
        new CountDownTimer(600, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                if(finalViewGone) {
                    ScaleAnimation scaleAnimation= new ScaleAnimation(2, 0.5f, 2, 0.5f);
                    scaleAnimation.setDuration(400);
                    ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.5f,2,0.5f,2);
                    scaleAnimation1.setDuration(200);

                    AnimationSet animationSet = new AnimationSet(false);
                    animationSet.addAnimation(scaleAnimation);
                    animationSet.addAnimation(scaleAnimation1);
                    animationSet.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            baserlay.removeView(finalRemovebadGuyView);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    finalRemovebadGuyView.startAnimation(animationSet);



                }
                baserlay.removeView(animationBulletView);
                baserlay.addView(explodeLocationBulletView, hitPositionParam);

                new CountDownTimer(100, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        if (finalGotHit) {
                            try {
                                badGuyList.get(finalHitIndex).getBadguyView().setText(badGuyList.get(finalHitIndex).getHp() + "");

                            } catch (Exception e) {

                            }
                        }

                        baserlay.removeView(explodeLocationBulletView);
                    }
                }.start();
            }
        }.start();

        AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(scale);
        scale.setDuration(600);
        animationBulletView.setAnimation(scale);
        animationSet.startNow();
     return true;
    }

}
