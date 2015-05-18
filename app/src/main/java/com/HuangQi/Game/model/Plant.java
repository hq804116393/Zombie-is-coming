package com.HuangQi.Game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.HuangQi.Game.entity.Zombie.Zombie;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.view.GameView;

import java.util.ArrayList;

/**
 * Created by Qi on 2015/5/9.
 */

public class Plant extends BaseModel {

    private BaseModel baseModel = null;



    private int frameIndex = 0;//动画帧索引
    private int Price = 0; //价格
    private int  attackedStateTime = 0;

    public Plant(int locationX, int locationY,int mapIndex) {
        super(locationX, locationY);
        this.setMapIndex(mapIndex);
        this.setWidth(Config.plantCardWidth);
        this.setHeight(Config.plantCardHeight);
        this.setRunWayIndex(mapIndex / 10);
        this.setLiveValue(100);
        this.setDefense(10);
    }
    public Plant(Point gravityCenter,int mapIndex){
        this(gravityCenter.x - Config.plantCardWidth / 2, gravityCenter.y - Config.plantCardHeight * 3 / 4, mapIndex);

    }

    @Override
    public void drawSelf(Canvas canvas) {}

    public static boolean applay4Plant(Plant plant){
        int locationX = plant.getCenter().x;
        int locationY = plant.getCenter().y;
        Point point;
        for (Integer key : Config.plantPoint.keySet()) {
            point = Config.plantPoint.get(key);
            if (Math.abs(point.x - locationX) <= Config.groundCardWidth / 2 && Math.abs(point.y - locationY) <= Config.groundCardWidth / 2) {
                for (int i = 0; i < Config.raceWayLocationY.length; i++) {
                    if (GameView.getInstanse().isExistInMapIndex(key)) return false;
                    if (point.y == Config.raceWayLocationY[i]) {
                        plant.setRunWayIndex(i);
                        plant.setMapIndex(key);
                        plant.setGravityCenter(point);
                        plant.addToView(plant);
                        Config.sunCount -= plant.getPrice();
                        return true;
                    }
                }

            }

        }
        return false;
    }

    public void collision(Zombie zombie) {
        this.setLiveValue(this.getLiveValue() - 50 * this.getDefense() / 10.0f / 20.0f);
        this.setIsAttacked(true);
        if (this.attackedStateTime <=0 )
            this.attackedStateTime = 10;
        if (this.getLiveValue() <= 0){
            this.setIsALive(false);
        }
    }
    public void setAttackedPaint(Paint paint){

        if (this.isAttacked()){
            if (attackedStateTime < 0){
                this.setIsAttacked(false);
            }else{
//                paint.setColor(0x80ff0000);
                attackedStateTime --;
                if (attackedStateTime % 2 == 0){
                    paint.setAlpha(100);
                }else
                {
                    paint.setAlpha(250);
                }
            }
        }
    }


    public void setGravityCenter(Point gravityCenter) {
        this.setCenter(new Point(gravityCenter.x, (int) (gravityCenter.y - this.getWidth() / 4.0)));
    }
    public Point getGravityCenter() {
        return new Point(this.getCenter().x,(int)(this.getCenter().y + this.getWidth() / 4.0));
    }
    public int getFrameIndex() {
        return frameIndex;
    }
    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }
    public int getPrice() {
        return Price;
    }
    public void setPrice(int price) {
        Price = price;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }
}

