package com.HuangQi.Game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.HuangQi.Game.entity.Bullet;
import com.HuangQi.Game.entity.EmplaceFlower.EmplacePea;
import com.HuangQi.Game.entity.Seed.SeedFlower;
import com.HuangQi.Game.entity.Seed.SeedPea;
import com.HuangQi.Game.entity.Sun;
import com.HuangQi.Game.entity.Zombie.Zombie;
import com.HuangQi.Game.entity.Zombie.ZombiesManager;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.model.BaseModel;
import com.HuangQi.Game.model.EmplacePlant;
import com.HuangQi.Game.model.Plant;
import com.HuangQi.Game.model.Touchable;

import java.util.ArrayList;

/**
 * Created by Qi on 2015/5/2.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    final private int PlantOrderInOneRunWay = 0;
    final private int ZibieOerInOneRunWay = 1;
    final private int BulletOrderInOneRunWay = 2;
    final private int SunOrderInOneRunWay = 3;

    private boolean[] mapIndexIsUsed = new boolean[50];
    //private ArrayList<Integer> mapIndexIsUsed =  new ArrayList<>();
    private ArrayList<BaseModel> deadList = new ArrayList<>();

    private ArrayList<BaseModel> addList = new ArrayList<>();



    private static GameView gameView;

    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean gameRunFlag;
    private Context context;
//    private ArrayList<BaseModel> deadList;


    private ArrayList<BaseModel> gameLayout4Seed; //seed层
    private ArrayList<BaseModel> gameLayout4Emplace; //Emplant层

    private ArrayList<ArrayList<ArrayList<BaseModel>>> gameLayouRunWays;

    private ArrayList<ArrayList<BaseModel>> gameLayout4Bullets;
    private ArrayList<ArrayList<BaseModel>> gameLayout4Plants;
    private ArrayList<ArrayList<BaseModel>> gameLayout4Zombies;
    private ArrayList<ArrayList<BaseModel>> gameLayout4Suns;

    private ZombiesManager zombiesManager = new ZombiesManager();
    private Thread drawThread;

    public GameView(Context context) {
        super(context);
        this.context = context;
        paint = new Paint();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameRunFlag = true;
        gameView = this;

    }

    public static GameView getInstanse() {
        return gameView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        creatElement();
        new Thread(this).start();

    }

    private void creatElement() {
        gameLayout4Emplace = new ArrayList<>();
        gameLayout4Seed = new ArrayList<>();
//        deadList = new ArrayList<>();

        gameLayout4Bullets = new ArrayList<>();
        gameLayout4Plants = new ArrayList<>();
        gameLayout4Zombies = new ArrayList<>();
        gameLayout4Suns = new ArrayList<>();
        gameLayouRunWays = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<BaseModel> bulletArray = new ArrayList<>();
            ArrayList<BaseModel> plantArray = new ArrayList<>();
            ArrayList<BaseModel> zombieArray = new ArrayList<>();
            ArrayList<BaseModel> sunArray = new ArrayList<>();
            ArrayList<ArrayList<BaseModel>> runWayArray = new ArrayList<>();

            gameLayout4Bullets.add(bulletArray);
            gameLayout4Plants.add(plantArray);
            gameLayout4Zombies.add(zombieArray);
            gameLayout4Suns.add(sunArray);

            runWayArray.add(plantArray);
            runWayArray.add(zombieArray);
            runWayArray.add(bulletArray);
            runWayArray.add(sunArray);
            gameLayouRunWays.add(runWayArray);
        }

        applay4Seed();
        drawThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (surfaceHolder) {
                    try {

                        zombiesManager.drawSelf(canvas);
                        canvas = surfaceHolder.lockCanvas();
                        canvas.drawBitmap(Config.gameBK, 0, 0, paint);
                        canvas.drawBitmap(Config.seedBank, (Config.deviceWidth - Config.seedBank.getWidth()) / 2, 0, paint);

                        drawSunCount();

                        updata();
                        ondraw(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }

                }
            }
            private void drawSunCount() {
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStrokeWidth(3);
                paint.setTextSize(30);
                paint.setColor(0xffff0000);
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                int baseline = Config.sunCountRect.top + (Config.sunCountRect.bottom - Config.sunCountRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                paint.setTextAlign(Paint.Align.CENTER);
                String sunCount = Config.sunCount + "";
                canvas.drawText(sunCount, Config.sunCountRect.centerX(), baseline, paint);
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        while (gameRunFlag) {
//            synchronized (surfaceHolder) {
//
//                try {
//                    zombiesManager.drawSelf(canvas);
//                    canvas = surfaceHolder.lockCanvas();
//                    canvas.drawBitmap(Config.gameBK, 0, 0, paint);
//                    canvas.drawBitmap(Config.seedBank, (Config.deviceWidth - Config.seedBank.getWidth()) / 2, 0, paint);
//                    updata();
//                    ondraw(canvas);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    surfaceHolder.unlockCanvasAndPost(canvas);
//                }
//
//            }
            drawThread.run();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updata() {
        synchronized (surfaceHolder) {
            if (addList.size() > 0) {
                for (BaseModel baseModel : addList) {

                    if (baseModel instanceof EmplacePlant) {
                        gameLayout4Emplace.add(baseModel);
                        continue;
                    }
                    ArrayList<BaseModel> arrayList = null;
                    if (baseModel instanceof Bullet) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(BulletOrderInOneRunWay);
                        arrayList = gameLayout4Bullets.get(baseModel.getRunWayIndex());
                    }if (baseModel instanceof Plant) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(PlantOrderInOneRunWay);
                        arrayList = gameLayout4Plants.get(baseModel.getRunWayIndex());
                    } else if (baseModel instanceof Zombie) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(ZibieOerInOneRunWay);
                        arrayList = gameLayout4Zombies.get(baseModel.getRunWayIndex());
                    } else if (baseModel instanceof Sun) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(SunOrderInOneRunWay);
                        arrayList = gameLayout4Suns.get(baseModel.getRunWayIndex());
                    }
                    arrayList.add(baseModel);
//                    Log.v("GameView",baseModel.toString() + " add to "+arrayList.toString());
                }
            }
            if (deadList.size() > 0) {
                for (BaseModel baseModel : deadList) {
                    if (baseModel instanceof EmplacePlant) {
                        gameLayout4Emplace.remove(baseModel);
                        continue;
                    }
                    ArrayList<BaseModel> arrayList = null;

                    if (baseModel instanceof Bullet) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(BulletOrderInOneRunWay);
                        arrayList = gameLayout4Bullets.get(baseModel.getRunWayIndex());
                    }if (baseModel instanceof Plant) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(PlantOrderInOneRunWay);
                        arrayList = gameLayout4Plants.get(baseModel.getRunWayIndex());
                    } else if (baseModel instanceof Zombie) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(ZibieOerInOneRunWay);
                        arrayList = gameLayout4Zombies.get(baseModel.getRunWayIndex());
                    } else if (baseModel instanceof Sun) {
//                        arrayList = gameLayouRunWays.get(baseModel.getRunWayIndex()).get(SunOrderInOneRunWay);
                        arrayList = gameLayout4Suns.get(baseModel.getRunWayIndex());
                    }
                    arrayList.remove(baseModel);
//                    Log.v("GameView", baseModel.toString() + " remove to " + arrayList.toString());
                }
            }
            addList.clear();
            deadList.clear();

        }
    }

    private void ondraw(Canvas canvas) {
        //种子层
        synchronized (surfaceHolder) {

            for (BaseModel baseModel : gameLayout4Seed) {
                baseModel.drawSelf(canvas);
            }

            for (ArrayList<ArrayList<BaseModel>> runWayLayOut : gameLayouRunWays) {
                if (runWayLayOut.size() < 1) continue;
                for (ArrayList<BaseModel> arrayList : runWayLayOut) {
                    if (arrayList.size() < 1) continue;
                    for (BaseModel baseModel : arrayList) {
                        baseModel.drawSelf(canvas);
                    }
                }
            }
            //可安放植物层
            for (BaseModel baseModel : gameLayout4Emplace) {
                baseModel.drawSelf(canvas);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        synchronized (surfaceHolder) {

            //Emplant层
            for (BaseModel model : gameLayout4Emplace) {
                if (model instanceof Touchable) {
                    if (((Touchable) model).onTouch(event)) {
                        return true;
                    }
                }
            }
            float i = event.getY();
            i = i - Config.plantFristCenterLocation.y + Config.groundCardHeight / 2.0f;
            i /= Config.groundCardHeight;
            if (i >= 0 && i <= 5) {
                for (BaseModel model : gameLayouRunWays.get((int) i).get(SunOrderInOneRunWay)) {
                    if (model instanceof Touchable) {
                        if (((Touchable) model).onTouch(event)) {
                            return true;
                        }
                    }
                }
            }
            //seed层
            for (BaseModel model : gameLayout4Seed) {
                if (model instanceof Touchable) {
                    if (((Touchable) model).onTouch(event)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void applay4Seed() {

        synchronized (surfaceHolder) {

            SeedFlower seedFlower = new SeedFlower(Config.seedCardFristLocationX, (int) (Config.seedBank.getHeight() / (float) 10));
            SeedPea seedPea = new SeedPea(Config.seedCardFristLocationX + Config.seedCardPitch, (int) (Config.seedBank.getHeight() / (float) 10));
            gameLayout4Seed.add(seedFlower);
            gameLayout4Seed.add(seedPea);

        }

    }

    public void applay4EmplacePlant(BaseModel emplacePlant, boolean delete) {
        synchronized (surfaceHolder) {

            if (!delete) {
                if (gameLayout4Emplace.size() < 1) {
                    gameLayout4Emplace.add((BaseModel) emplacePlant);
                }
            } else {
                gameLayout4Emplace.remove(emplacePlant);
            }

        }
    }

    public void checkCollision(Zombie zombie) {
        synchronized (surfaceHolder) {
            for (ArrayList<BaseModel> arrayList:gameLayouRunWays.get(zombie.getRunWayIndex())){
                for (BaseModel baseModel : arrayList){
                    if (baseModel instanceof  Zombie) break;
                    if (!baseModel.isALive()) continue;
                    if (Math.abs((zombie.getLocationX() + zombie.getWidth() / 2.0f) - (baseModel.getLocationX() + baseModel.getWidth() / 2.0f)) <
                            (zombie.getWidth() + baseModel.getWidth())/2.0f  - (zombie.getWidth() + baseModel.getWidth())/5.0f ){
                            zombie.collision(baseModel);
                            baseModel.collision(zombie);
                    }
                }
            }
        }

    }
    public void addBaseModelToView(BaseModel baseModel) {
        if (baseModel instanceof Plant) {
            if (mapIndexIsUsed[baseModel.getMapIndex()] == true) {
                baseModel.setIsALive(false);
                return;
            } else {
                mapIndexIsUsed[baseModel.getMapIndex()] = true;
            }
        }
        addList.add(baseModel);

        Log.v("GameView","addList.size =="+addList.size());
    }


    public void removeBaseModelFromView(BaseModel baseModel) {

        if (baseModel instanceof Plant) {
            mapIndexIsUsed[baseModel.getMapIndex()] = false;
        }
        deadList.add(baseModel);

        Log.v("GameView", "deadList.size ==" + deadList.size());
    }

    public boolean isExistInMapIndex(Integer key) {
        return mapIndexIsUsed[key];
    }
}

