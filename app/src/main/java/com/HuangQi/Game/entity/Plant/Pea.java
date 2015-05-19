package com.HuangQi.Game.entity.Plant;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.HuangQi.Game.entity.Bullet;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.Plant;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/6.
 */
public class Pea extends Plant {

//    public Pea(int locationX, int locationY,int mapIndex) {
//       super(locationX, locationY, mapIndex);
//    }

    public Pea(Point gravityCenter,int mapIndex) {
        super(gravityCenter,mapIndex);
        this.setPrice(100);
    }


    @Override
    public void drawSelf(Canvas canvas) {
        if (this.isALive())
        {
            Paint paint = new Paint();
            this.setAttackedPaint(paint);
            canvas.drawBitmap(Config.peaFrames[this.getFrameIndex()],this.getLocationX(),this.getLocationY(),paint);
            this.setFrameIndex((this.getFrameIndex() + 1) % Config.peaFrames.length);
            brith2Bullet();
        }
    }
    private void brith2Bullet(){
        if (System.currentTimeMillis() - this.getBirthTime() > 3000)
        {
            this.setBrithTime(System.currentTimeMillis());
            Point point =  new Point(this.getLocationX(),this.getLocationY());
            point.x = (int)(60 * this.getWidth() / 80.0f + point.x);
            point.y = (int)(20 * this.getHeight() / 80.0f + point.y);
            this.addToView(new Bullet(point,this.getMapIndex()));
            GameView.getInstanse().getSoundPool().play(Config.bulletShootSend, GameView.getInstanse().getVolume(), GameView.getInstanse().getVolume(), 1, 1, 1f);
        }
    }
}
