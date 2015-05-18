package com.HuangQi.Game.entity.Seed;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.HuangQi.Game.entity.EmplaceFlower.EmplaceFlower;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.model.Seed;
import com.HuangQi.Game.model.Touchable;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/3.
 */
public class SeedFlower extends Seed implements Touchable{

    public SeedFlower(int locationX, int locationY) {
        super(locationX, locationY);
        this.setPrice(50);
    }

//    public SeedFlower(Point centerPoint) {
//        super(centerPoint);
//    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            int x = (int) event.getX();
            int y = (int) event.getY();
            if (this.isTouchAble()) {
                if (this.getTouchArea().contains(x, y)) {
                    this.applay4EmplacePlant(new EmplaceFlower(new Point(x, y),9));
                    Log.e("Game", "seedFlower is Touch Down");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(Config.seedFlower, this.getLocationX(), this.getLocationY(), new Paint());
        this.drawRect(canvas);
    }


}
