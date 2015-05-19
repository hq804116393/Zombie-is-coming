package com.HuangQi.Game.entity.Zombie;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;

/**
 * Created by Qi on 2015/5/10.
 */
public class ZombiesManager extends BaseModel {

    private float brithZombieTimePitch = 25;
    public ZombiesManager() {
        super(0,0);
        this.setBrithTime(System.currentTimeMillis());
    }

    @Override
    public void drawSelf(Canvas canvas) {
        int timeCountDown = (int) (this.brithZombieTimePitch * 1000 - (System.currentTimeMillis() - this.getBirthTime())) / 1000;
        if (timeCountDown <= 10 && timeCountDown >= 1) {
            drawTimeCountDown(canvas, timeCountDown);
        }
        if (System.currentTimeMillis() - this.getBirthTime() > this.brithZombieTimePitch * 1000) {
            this.setBrithTime(System.currentTimeMillis());
            brith2Zombie();

        }
    }

    private void brith2Zombie() {
        int runWayIndex = (int)(Math.random() * 5);
        Log.v("Zombie", "runWayIndex is " + runWayIndex);

        Zombie zombie = new Zombie(new Point(Config.deviceWidth - Config.zombieCardWith/2,Config.raceWayLocationY[runWayIndex]),runWayIndex);
        this.addToView(zombie);
        if (this.brithZombieTimePitch > 8) {
            this.brithZombieTimePitch -= 0.2;
        }
    }

    private void drawTimeCountDown(Canvas canvas, int time) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(10);
        paint.setTextSize(100);
        if (time < 5) {
            paint.setColor(0xffff0000);
        } else {
            paint.setColor(0xff00ff00);
        }
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = Config.timeCountDownRect.top + (Config.timeCountDownRect.bottom - Config.timeCountDownRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        paint.setTextAlign(Paint.Align.CENTER);
        String timeCountDown = time + "";
        canvas.drawText(timeCountDown, Config.timeCountDownRect.centerX(), baseline, paint);
    }
}
