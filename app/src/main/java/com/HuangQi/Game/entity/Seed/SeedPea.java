package com.HuangQi.Game.entity.Seed;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.HuangQi.Game.entity.EmplaceFlower.EmplacePea;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.Seed;
import com.HuangQi.Game.model.Touchable;
import com.HuangQi.Game.view.GameView;

/**
 * Created by Qi on 2015/5/3.
 */
public class SeedPea extends Seed implements Touchable{

    public SeedPea(int locationX, int locationY) {
        super(locationX, locationY);
        this.setPrice(100);
    }

//    public SeedPea(Point centerPoint) {
//        super(centerPoint);
//    }

    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(Config.seedPea, this.getLocationX(), this.getLocationY(), new Paint());
        this.drawRect(canvas);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (this.isTouchAble()) {
                if (this.getTouchArea().contains(x, y)) {
                    this.applay4EmplacePlant(new EmplacePea(new Point(x, y), 9));
                    return true;
                }
            }
        }

        return false;
    }

    private void applay4EmplacePea(Point gravityCenter) {
        GameView.getInstanse().applay4EmplacePlant(new EmplacePea(gravityCenter,9),false);
    }


}
