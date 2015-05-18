package com.HuangQi.Game.model;

import android.graphics.Point;

import com.HuangQi.Game.global.Config;

/**
 * Created by Qi on 2015/5/8.
 */
public class EmplacePlant extends BaseModel {

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    private Plant plant;

    public EmplacePlant(Point getGravityCenter,int mapIndex){
        this((int)(getGravityCenter.x - Config.plantCardWidth / (float)2), (int)(getGravityCenter.y - Config.plantCardHeight * 3/(float)4),mapIndex);
    }
    @Override
    public Point getGravityCenter() {
        return new Point(this.getCenter().x,(int)(this.getCenter().y + this.getWidth() / 4.0));
    }
    public void setGravityCenter(Point gravityCenter) {
        this.setLocationX((int) (gravityCenter.x - this.getWidth() / 2.0)) ;
        this.setLocationY((int) (gravityCenter.y - this.getHeight() * 3 / 4.0));
    }

    public EmplacePlant(int locationX, int locationY,int mapIndex) {
        super(locationX, locationY);
        this.setWidth(Config.plantCardWidth);
        this.setHeight(Config.plantCardHeight);
        this.setMapIndex(mapIndex);
    }



}
