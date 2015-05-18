package com.HuangQi.Game.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.model.Touchable;

/**
 * Created by Qi on 2015/5/8.
 */

public class Sun extends BaseModel implements Touchable{


    public Sun(int locationX,int locationY,int mapIndex) {
        super(locationX,locationY);
        this.state = SunState.SHOW;

        this.setWidth(Config.sunCardWidth);
        this.setHeight(Config.sunCardHeight);
        this.setMapIndex(mapIndex);
        this.setRunWayIndex(mapIndex / 10);

        this.paint.setAlpha(0);

    }

    public Sun(Point center,int mapIndex) {
        this(center.x - Config.sunCardWidth / 2, center.y - Config.sunCardHeight / 2,mapIndex);
    }


    private SunState state;
    private float xDistanceMovePerTimes;
    private float yDistanceMovePerTimes;

    private float  xDistanceToSunCollecterCenter;
    private float  yDistanceToSunCollecterCenter;

    Paint paint = new Paint();

    private enum SunState{
        SHOW,MOVE
    }

    @Override
    public void drawSelf(Canvas canvas) {

        if (this.isALive())
        {
            if (this.state  == SunState.SHOW){
                if (this.paint.getAlpha() < 235){
                    this.paint.setAlpha(this.paint.getAlpha() + 20);
                }
                if (System.currentTimeMillis() - this.getBirthTime() > 4000){
                    if (this.paint.getAlpha() > 200){
                        this.paint.setAlpha(this.paint.getAlpha() - 100);
                    }else {
                        this.paint.setAlpha(this.paint.getAlpha() + 100);
                    }
                }
                if (System.currentTimeMillis() - this.getBirthTime() > 5000){
                    this.setIsALive(false);
                }
            }else{
                this.xDistanceToSunCollecterCenter -= this.xDistanceMovePerTimes;
                this.yDistanceToSunCollecterCenter -= this.yDistanceMovePerTimes;
                if (this.xDistanceToSunCollecterCenter < this.getWidth() && this.yDistanceToSunCollecterCenter < this.getHeight()){
                        if (paint.getAlpha() > 20)
                            paint.setAlpha(paint.getAlpha() - 20);
                }
                this.setLocationX((int)(Config.sunCollecterCentrePoint.x + this.xDistanceToSunCollecterCenter - Config.sunCardWidth / (float)2));
                this.setLocationY((int) (Config.sunCollecterCentrePoint.y + this.yDistanceToSunCollecterCenter - Config.sunCardHeight / (float) 2));
                if ((this.yDistanceMovePerTimes >= 0 && this.yDistanceToSunCollecterCenter < 1)||(this.yDistanceMovePerTimes <= 0 && this.yDistanceToSunCollecterCenter > -1)){
                    this.setIsALive(false);
                    Config.sunCount += 25;
                }
            }
            canvas.drawBitmap(Config.sun,this.getLocationX(),this.getLocationY(),paint);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (this.getTouchArea().contains(x, y) && this.state == SunState.SHOW)
        {
            this.state = SunState.MOVE;
            xDistanceToSunCollecterCenter = this.getCenter().x - Config.sunCollecterCentrePoint.x;
            yDistanceToSunCollecterCenter = this.getCenter().y - Config.sunCollecterCentrePoint.y;

            xDistanceMovePerTimes = (xDistanceToSunCollecterCenter / 50/(float)0.5);
            yDistanceMovePerTimes = (yDistanceToSunCollecterCenter / 50/(float)0.5);
            return true;
        }
        return false;
    }

}
