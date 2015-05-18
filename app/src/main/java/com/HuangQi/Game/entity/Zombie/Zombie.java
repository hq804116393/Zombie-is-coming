package com.HuangQi.Game.entity.Zombie;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.HuangQi.Game.entity.Bullet;
import com.HuangQi.Game.entity.Sun;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.model.Plant;
import com.HuangQi.Game.tools.DeviceTools;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/10.
 */
public class Zombie extends BaseModel {
    private int frameIndex = 0;
    private float moveSpeedPerTimes = Config.gameBK.getWidth() / 10 / 20.0f;
    private float movedDistance = 0;
    private Bitmap bitmap;
    private int  attackedStateTime = 0;

    private BaseModel baseModel = null;

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }

    private boolean isStop = false;

    public Zombie(int locationX, int locationY,int runWayIndex) {
        super(locationX, locationY);
        this.setWidth(Config.zombieCardWith);
        this.setHeight(Config.zombieCardHeight);
        this.setRunWayIndex(runWayIndex);
        this.setMapIndex(-1);
        this.setLiveValue(100);
        this.setDefense(10);
    }

    public Point  getGravityCenter(){
        return new Point((int)(this.getCenter().x), (int)(this.getCenter().y + this.getHeight() * 3/8.0));
    }
    public Zombie(Point gravityCenter,int runWayIndex ) {
        this((int)(gravityCenter.x - Config.zombieCardWith / 2.0),(int)(gravityCenter.y - Config.zombieCardHeight * 7/8.0), runWayIndex);
    }

    @Override
    public void drawSelf(Canvas canvas) {

        if (this.isALive()) {
            if (baseModel !=null && !baseModel.isALive()){
                this.setIsStop(false);
                baseModel = null;
            }
            if (!this.isStop) {
                this.movedDistance += moveSpeedPerTimes;
                this.setLocationX((int) (this.getLocationX() - this.moveSpeedPerTimes));
                if (this.getLocationX() + this.getWidth() < 1)
                {
                    this.setIsALive(false);
                }
            }
            bitmap = Config.zombieFrames[frameIndex].copy(Config.zombieFrames[frameIndex].getConfig(),true);
            if (bitmap.getWidth() < this.getWidth() && bitmap.getHeight() < this.getHeight())
                bitmap = DeviceTools.resizeBitmap(bitmap,this.getWidth(),this.getHeight());

            Paint paint = new Paint();
            setAttackedPaint(paint);
            canvas.drawBitmap(bitmap,this.getLocationX(),this.getLocationY(),paint);
            frameIndex = (++frameIndex) % Config.zombieFrames.length;

            checkCollision(this);
        }
    }

    private void checkCollision(Zombie zombie) {
        GameView.getInstanse().checkCollision(zombie);
    }


    public void collision(BaseModel baseModel) {
        if (baseModel instanceof Sun){
            collisionWithSun(baseModel);
        }else if (baseModel instanceof Bullet){
            collisionWithBullet(baseModel);
        }else if(baseModel instanceof Plant){
            collisionWithPlant(baseModel);
        }
    }



    private void collisionWithSun(BaseModel baseModel){
        Point gravityCenter = this.getGravityCenter();
        float scale = this.getHeight() / (float)this.getWidth();
        int xincrece = 10;
        int yincrece = (int)( xincrece * scale );
        if (this.getWidth() + xincrece>Config.zombieCardWith*2 || this.getHeight()  + yincrece > Config.zombieCardHeight*2)
        {
            this.setWidth(Config.zombieCardWith * 2);
            this.setHeight(Config.zombieCardHeight * 2);
        }else
        {
            this.setHeight(this.getHeight() + yincrece);
            this.setWidth(this.getWidth() + xincrece);
        }
        this.setDefense(this.getDefense() + 10);
        this.setLiveValue(this.getLiveValue() + 10);
        this.setLocation(gravityCenter);
    }
    private void collisionWithBullet(BaseModel baseModel){
        this.setLiveValue(this.getLiveValue() - 30 * 10 / this.getDefense());
        this.setIsAttacked(true);
        if (this.attackedStateTime <= 0)
            this.attackedStateTime = 10;
        if (this.getLiveValue() <= 0){
            this.setIsALive(false);
        }
    }
    private void collisionWithPlant(BaseModel baseModel) {
        this.isStop = true;
        this.baseModel = baseModel;
    }

    private void setLocation(Point gravityCenter) {
       this.setLocationX((int)(gravityCenter.x - this.getWidth() / 2.0));
       this.setLocationY((int)(gravityCenter.y - this.getHeight() * 7/8.0));
    }
    private void setAttackedPaint(Paint paint){
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
}
