package com.HuangQi.Game.entity.Zombie;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;

/**
 * Created by Qi on 2015/5/10.
 */
public class ZombiesManager extends BaseModel {

    private float brithZombieTimePitch = 20;
    public ZombiesManager() {
        super(0,0);
        this.setBrithTime(System.currentTimeMillis());
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (System.currentTimeMillis() - this.getBirthTime() > this.brithZombieTimePitch * 1000) {
            this.setBrithTime(System.currentTimeMillis());
            brith2Zombie();
        }
    }

    private void brith2Zombie() {
        int runWayIndex = (int)(Math.random() * 5);
        Log.v("Zombie","runWayIndex is " + runWayIndex);
        Zombie zombie = new Zombie(new Point(Config.deviceWidth - Config.zombieCardWith/2,Config.raceWayLocationY[runWayIndex]),runWayIndex);
        this.addToView(zombie);
        if (this.brithZombieTimePitch > 8) {
            this.brithZombieTimePitch -= 0.2;
        }
    }
}
