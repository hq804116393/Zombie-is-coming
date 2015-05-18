package com.HuangQi.Game.model;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.HuangQi.Game.entity.Zombie.Zombie;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/3.
 */
public class BaseModel {
    public BaseModel(int locationX, int locationY) {
        this.setLocationX(locationX);
        this.setLocationY(locationY);
        this.setIsALive(true);
        this.brithTime = System.currentTimeMillis();

        this.setMapIndex(-1);
        this.setRunWayIndex(-1);
        this.setIsAttacked(false);

    }
    private int mapIndex;//地图位置
    private float liveValue;//生命值
    private float defense;//防御力
    private long brithTime;//出生时间
    private int locationX;//x坐标
    private int locationY;//y坐标
    private boolean isALive;//
    private int width = 0;//
    private int height = 0;//
    private Rect touchArea = new Rect();//响应触摸事件区域
    private int runWayIndex;//跑道索引
    private boolean isAttacked = false;

    public void drawSelf(Canvas canvas) {}

    public  void removeFromView(BaseModel baseModel){
        GameView.getInstanse().removeBaseModelFromView(baseModel);
    }
    public  void addToView(BaseModel baseModel){
        GameView.getInstanse().addBaseModelToView(baseModel);
    }
    public void collision(Zombie zombie) {
        this.setIsALive(false);
    }
    public float getDefense() {
        return defense;
    }
    public void setDefense(float defense) {
        this.defense = defense;
    }
    public float getLiveValue() {
        return liveValue;
    }
    public void setLiveValue(float liveValue) {
        this.liveValue = liveValue;
    }
    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }
    public int getMapIndex() {
        return mapIndex;
    }
    public void setBrithTime(long brithTime) {
        this.brithTime = brithTime;
    }
    public long getBirthTime() {
        return brithTime;
    }
    public int getLocationX() {
        return locationX;
    }
    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }
    public int getLocationY() {
        return locationY;
    }
    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
    public boolean isALive() {
        return isALive;
    }
    public void setIsALive(boolean isALive) {
        if (isALive == false){
            this.removeFromView(this);
        }
        this.isALive = isALive;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Point getCenter() {

        return new Point((int)(locationX + width/2.0),(int)(locationY + height/2.0));
    }
    public void setCenter(Point center) {
        this.locationX = (int)(center.x - width/2.0);
        this.locationY = (int)(center.y - height/2.0);
    }
    public Rect getTouchArea() {
        this.touchArea.set(this.getLocationX(),this.getLocationY(),this.getLocationX() + this.getWidth(),this.getLocationY() + this.getHeight());
        return this.touchArea;
    }
    public Point getGravityCenter() {
        return this.getCenter();
    }
    public int getRunWayIndex() {
        return this.runWayIndex;
    }
    public void setRunWayIndex(int runWayIndex) {
        this.runWayIndex = runWayIndex;
    }
    public boolean isAttacked() {
        return isAttacked;
    }
    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }
}
