package com.example.user.puepuepue;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016/11/25.
 */
public class BadGuy {
TextView badguyView;
    int hp;

    public TextView getBadguyView() {
        return badguyView;
    }

    public void setBadguyView(TextView badguyView) {
        this.badguyView = badguyView;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
