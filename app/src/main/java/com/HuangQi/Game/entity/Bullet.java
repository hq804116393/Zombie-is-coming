package com.HuangQi.Game.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.HuangQi.Game.entity.Zombie.Zombie;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/17.
 */
public class Bullet extends BaseModel {
    private float speed;


    public Bullet(int locationX, int locationY,int mapIndex) {
        super(locationX, locationY);
        this.setMapIndex(mapIndex);
        this.setRunWayIndex(mapIndex / 10);
        this.setWidth(Config.bulletCardWidth);
        this.setHeight(Config.bulletCardWidth);
        this.speed = Config.deviceWidth / 5.0f / 20.0f;
    }
    public Bullet(Point center,int mapIndex){
        this(center.x - Config.bulletCardWidth/2,center.y - Config.bulletCardHeight /2,mapIndex);
    }

    @Override
    public void collision(Zombie zombie) {
        super.collision(zombie);
        this.playShootSound();
    }
    private void playShootSound(){
        GameView.getInstanse().getSoundPool().play(Config.bulletShootShoot, GameView.getInstanse().getVolume(), GameView.getInstanse().getVolume(), 1, 0, 0.5f);
    }
    @Override
    public void drawSelf(Canvas canvas) {
        if (this.isALive()) {
            this.setLocationX((int) (this.getLocationX() + this.speed));
            if (this.getLocationX() > Config.deviceWidth)
            {
                this.setIsALive(false);
            }
            canvas.drawBitmap(Config.bullet, this.getLocationX(), this.getLocationY(), new Paint());
        }
    }
}
