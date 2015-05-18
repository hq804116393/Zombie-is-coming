package com.HuangQi.Game.entity.EmplaceFlower;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.HuangQi.Game.entity.Plant.Pea;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.EmplacePlant;
import com.HuangQi.Game.model.Plant;
import com.HuangQi.Game.model.Touchable;

/**
 * Created by Qi on 2015/5/4.
 */
public class EmplacePea extends EmplacePlant implements Touchable{


//    public EmplacePea(int locationX, int locationY) {
//        super(locationX, locationY);
//    }

    public EmplacePea(Point gravityCenter,int mapIndex) {
        super(gravityCenter,mapIndex);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        //if (this.touchArea.contains(x,y))
        if (true)
        {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.setGravityCenter(new Point(x,y));
                    break;
                case MotionEvent.ACTION_UP:
                    this.setIsALive(false);
                    Plant baseModel = new Pea(this.getGravityCenter(), -1);
                    if (Plant.applay4Plant(baseModel)){
                        this.setPlant(baseModel);
                    }

                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (this.isALive()) {
            canvas.drawBitmap(Config.peaFrames[0],this.getLocationX(),this.getLocationY(),new Paint());

        }
    }


}
