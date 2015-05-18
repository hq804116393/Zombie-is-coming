package com.HuangQi.Game.entity.Plant;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.HuangQi.Game.entity.Sun;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.Plant;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/6.
 */
public class Flower extends Plant {


//    public Flower(int locationX, int locationY,int mapIndex) {
//        super(locationX, locationY, mapIndex);
//
//   }

    public Flower(Point gravityCenter, int mapIndex) {
        super(gravityCenter, mapIndex);
        this.setPrice(50);
  }

    @Override
    public void drawSelf(Canvas canvas) {
        if (this.isALive())
        {

            Paint paint = new Paint();
            this.setAttackedPaint(paint);
            canvas.drawBitmap(Config.flowerFrames[this.getFrameIndex()], this.getLocationX(), this.getLocationY(), paint);
            this.setFrameIndex((this.getFrameIndex() + 1) % Config.flowerFrames.length);
            this.brith2Sun();
        }
    }
    private void brith2Sun(){
        if (System.currentTimeMillis() - this.getBirthTime() > 1000)
        {
            this.setBrithTime(System.currentTimeMillis());
            int runWayIndex = (int)(Math.random() * 5);
            int mapIndex = runWayIndex * 10 +  (int)(Math.random() * 9);
            Log.v("MapIndex",mapIndex + "");
            Point point =  new Point(Config.plantPoint.get(mapIndex));
            point.y -= Config.sunCardHeight/8.0f;
            this.addToView(new Sun(point,mapIndex));
        }
    }


}
