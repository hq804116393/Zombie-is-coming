package com.HuangQi.Game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/9.
 */
public class Seed extends BaseModel {


    private boolean isTouchAble = true;
    Paint paint = new Paint();
    private int Price = 0; //价格

    public EmplacePlant getEmplacePlant() {
        return emplacePlant;
    }

    public void setEmplacePlant(EmplacePlant emplacePlant) {
        this.emplacePlant = emplacePlant;
    }

    private EmplacePlant emplacePlant;

    public Seed(int locationX, int locationY) {
        super(locationX,locationY);
        this.setWidth(Config.seedCardWidth);
        this.setHeight(Config.seedCardHeight);
    }

    public Seed(Point centerPoint){
        this(centerPoint.x - Config.seedCardWidth / 2, centerPoint.y - Config.seedCardHeight / 2);
    }

    @Override
    public void drawSelf(Canvas canvas) {}



    public void applay4EmplacePlant(EmplacePlant emplacePlant) {
        this.setEmplacePlant(emplacePlant);
        GameView.getInstanse().applay4EmplacePlant(emplacePlant, false);
    }



    public boolean isTouchAble() {
        return isTouchAble;
    }
    public void setIsTouchAble(boolean isTouchAble) {
        this.isTouchAble = isTouchAble;
    }
    public Paint getPaint() {
        return paint;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public int getPrice() {
        return Price;
    }
    public void setPrice(int price) {
        Price = price;
    }


    public void drawRect(Canvas canvas){
        if (this.getEmplacePlant() != null && this.getEmplacePlant().getPlant() != null)
        {
            this.setBrithTime(System.currentTimeMillis());
            this.setIsTouchAble(false);
            this.setEmplacePlant(null);
        }
        if (!this.isTouchAble() && System.currentTimeMillis() - this.getBirthTime() < 5000)
        {
            this.getPaint().setAlpha(150);
            canvas.drawRect(this.getLocationX(),
                    this.getLocationY(),
                    this.getLocationX()+this.getWidth(),
                    (int)((this.getLocationY()+this.getHeight())*((5000 - System.currentTimeMillis() + this.getBirthTime())/5000.0f)),this.getPaint());
        }else{
            this.setIsTouchAble(true);
        }
        if (Config.sunCount < this.getPrice()){
            this.getPaint().setAlpha(200);
            canvas.drawRect(this.getLocationX(),
                    this.getLocationY(),
                    this.getLocationX()+this.getWidth(),
                    this.getLocationY()+this.getHeight(),this.getPaint());
        }
    }
}
