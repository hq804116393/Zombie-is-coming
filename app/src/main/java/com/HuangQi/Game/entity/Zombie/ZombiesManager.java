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

    public ZombiesManager() {
        super(0,0);
        this.setBrithTime(System.currentTimeMillis());
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (System.currentTimeMillis() - this.getBirthTime() > 10000){
            this.setBrithTime(System.currentTimeMillis());
            brith2Zombie();
        }
    }

    private void brith2Zombie() {
        int runWayIndex = (int)(Math.random() * 5);
        Log.v("Zombie","runWayIndex is " + runWayIndex);
        Zombie zombie = new Zombie(new Point(Config.deviceWidth - Config.zombieCardWith/2,Config.raceWayLocationY[runWayIndex]),runWayIndex);
        this.addToView(zombie);

    }
}
