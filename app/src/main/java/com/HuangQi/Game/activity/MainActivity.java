package com.HuangQi.Game.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.HuangQi.Game.R;
import com.HuangQi.Game.global.Config;
import com.HuangQi.Game.tools.DeviceTools;
import com.HuangQi.Game.view.GameView;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;


/**
 * Created by Qi on 2015/5/2.
 */
public class MainActivity extends Activity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        //imageView.
        //initBugly();
        init();

        gameView = new GameView(this);
        setContentView(gameView);
    }

    private void initBugly() {
        //Bugly配置
        Context appContext =  this.getApplicationContext();
        String appId = "900003230";   //上Bugly(bugly.qq.com)注册产品获取的AppId
        boolean isDebug = true ;  //true代表App处于调试阶段，false代表App发布阶段
        //CrashReport.initCrashReport(appContext, appId, isDebug);  //初始化SDK

        //自定义策略
        UserStrategy strategy = new UserStrategy(getApplicationContext()); //App的策略Bean
        strategy.setAppChannel( "Development");     //设置渠道
        strategy.setAppVersion("Development_v3.0");      //App的版本
        strategy.setAppReportDelay(1000);  //设置SDK处理延时，毫秒
        CrashReport.initCrashReport(appContext, appId, isDebug, strategy); //自定义策略生效，必须在初始化SDK前调用

        CrashReport. setUserId("HuangQi");  //设置用户的唯一标识

        //模拟Java Crash方法：
        //CrashReport.testJavaCrash ();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameView.onTouchEvent(event);
    }
    private void init(){
        Config.deviceWidth = DeviceTools.getDeviceInfo(this)[0];
        Config.deviceHeight = DeviceTools.getDeviceInfo(this)[1];

        Config.gameBK = BitmapFactory.decodeResource(getResources(), R.drawable.bk);

        Config.scaleWidth = Config.deviceWidth /(float) Config.gameBK.getWidth();
        Config.scaleHeight = Config.deviceHeight / (float)Config.gameBK.getHeight();

        Config.gameBK = DeviceTools.resizeBitmap(Config.gameBK);
        Config.seedBank = BitmapFactory.decodeResource(getResources(), R.drawable.seedbank);
        Config.seedBank = DeviceTools.resizeBitmap(Config.seedBank);

        Config.emplacePlantFristLocation =new Point((int)((Config.deviceWidth - Config.seedBank.getWidth())/ (float)2 //种子放置栏左上角位置
                + Config.seedCardPitch //每张卡片加间距的宽度
                + (int)(Config.seedCardWidth / (float)3)),(int)(Config.seedBank.getHeight() / (float)5));

        //2.
        initGroundCard();
        //3.
        initSeedCard();
        //4.
        initPlantCard();
        //5.
        initSunCard();
        //6.
        initZombieCard();
        //7.
        initBulletCard();
        //8.
        initSunCount();

    }



    private  void initSeedCard(){

        Config.seedCardHeight = (int)(Config.seedBank.getHeight() - Config.seedBank.getHeight() / (float)5);
        Config.seedCardWidth = (int)(Config.seedBank.getWidth() / (float)8);
        Config.seedCardPitch = (int)(Config.seedBank.getWidth() / (float)7) ;

        Config.seedCardFristLocationX = (int)((Config.deviceWidth - Config.seedBank.getWidth())/ (float)2) //种子放置栏左上角位置
                + Config.seedCardPitch //每张卡片加间距的宽度
                + (int)(Config.seedCardWidth / (float)3);

        Config.seedFlower = BitmapFactory.decodeResource(getResources(), R.drawable.seed_flower);
        Config.seedFlower = DeviceTools.resizeBitmap(Config.seedFlower, (int) Config.seedCardWidth, (int) Config.seedCardHeight);

        Config.seedPea = BitmapFactory.decodeResource(getResources(), R.drawable.seed_pea);
        Config.seedPea = DeviceTools.resizeBitmap(Config.seedPea, (int) Config.seedCardWidth, (int) Config.seedCardHeight);

    }
    private void initGroundCard() {
        //每块种植物的地皮单元格的宽度
        //Config.groundCardWidth = Config.deviceWidth / 11;
        Config.groundCardWidth = (int)(Config.deviceWidth * 740/ 900.0 / (float)9);
        //每块种植物的地皮单元格的高度
        //Config.groundCardHeight = Config.deviceHeight / 6;
        Config.groundCardHeight = (int)(Config.deviceHeight * 500 / 600.0 / (float)5);


        //左上角第一块种植物的地皮种植植物时植物的左上角的坐标
        //Config.plantFristCenterLocation = new Point((int) (Config.deviceWidth * 1.5), (int)(Config.deviceHeight));
        //Config.plantFristCenterLocation = new Point((int) (Config.deviceWidth * 120 / (float)900 + (Config.groundCardWidth-Config.plantCardWidth) /(float)2),
        //        (int)(Config.deviceHeight * 80 / (float)600 + (Config.groundCardHeight-Config.plantCardHeight) / (float)2  - Config.plantCardHeight / (float)4));

        //左上角第一块种植物的地皮种植植物时植物的重心点的坐标
        Config.plantFristCenterLocation = new Point((int) (Config.deviceWidth * 120 / 900.0 + Config.groundCardWidth / 2.0),(int)(Config.deviceHeight * 80 / (float)600.0 + Config.groundCardHeight / 2.0));


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Config.plantPoint.put(i * 10 + j,
                        new Point(j * Config.groundCardWidth + Config.plantFristCenterLocation.x, i * Config.groundCardHeight + Config.plantFristCenterLocation.y));
                if (j == 0)
                    Config.raceWayLocationY[i] = i * Config.groundCardHeight + Config.plantFristCenterLocation.y;
            }

        }
    }
    private  void  initPlantCard(){

        Config.flowerFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_01);
        Config.flowerFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_02);
        Config.flowerFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_03);
        Config.flowerFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_04);
        Config.flowerFrames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_05);
        Config.flowerFrames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_06);
        Config.flowerFrames[6] = BitmapFactory.decodeResource(getResources(), R.drawable.p_1_07);
        Config.flowerFrames[7] = BitmapFactory.decodeResource(getResources(),R.drawable.p_1_08);

        Config.peaFrames[0] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_01);
        Config.peaFrames[1] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_02);
        Config.peaFrames[2] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_03);
        Config.peaFrames[3] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_04);
        Config.peaFrames[4] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_05);
        Config.peaFrames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.p_2_06);
        Config.peaFrames[6] = BitmapFactory.decodeResource(getResources(),R.drawable.p_2_07);
        Config.peaFrames[7] = BitmapFactory.decodeResource(getResources(), R.drawable.p_2_08);

        //植物的宽度
        //Config.plantCardWidth = (int)(Config.groundCardWidth * 3/(float)4);
        Config.plantCardWidth = (int)(Config.groundCardWidth);
        //植物的高度
        //Config.plantCardHeight = (int)(Config.groundCardHeight * 3/(float)4);
        Config.plantCardHeight = Config.plantCardWidth;

        for (int i = 0; i < Config.flowerFrames.length;i ++){
            //Config.flowerFrames[i] = DeviceTools.resizeBitmap(Config.flowerFrames[i],(int)Config.seedCardWidth,(int)Config.seedCardHeight);
            //Config.peaFrames[i] = DeviceTools.resizeBitmap(Config.peaFrames[i],(int)Config.seedCardWidth,(int)Config.seedCardHeight);
            Config.flowerFrames[i] = DeviceTools.resizeBitmap(Config.flowerFrames[i],(int)Config.plantCardWidth,(int)Config.plantCardHeight);
             Config.peaFrames[i] = DeviceTools.resizeBitmap(Config.peaFrames[i],(int)Config.plantCardWidth,(int)Config.plantCardHeight);
        }

    }

    private void initSunCard() {
        Config.sunCardWidth = Config.plantCardWidth;
        Config.sunCardHeight = Config.plantCardHeight;

        Config.sun = BitmapFactory.decodeResource(getResources(),R.drawable.sun);
        Config.sun = DeviceTools.resizeBitmap(Config.sun, Config.sunCardWidth, Config.sunCardHeight);
        Config.sunCollecterCentrePoint.set((int)((Config.deviceWidth - Config.seedBank.getWidth())/(float)2 + Config.seedBank.getWidth() * 39/(float)446),(int)(Config.seedBank.getHeight() *33/(float)87));
    }

    private void initZombieCard() {
        Config.zombieFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_01);
        Config.zombieFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_02);
        Config.zombieFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_03);
        Config.zombieFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_04);
        Config.zombieFrames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_05);
        Config.zombieFrames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_06);
        Config.zombieFrames[6] = BitmapFactory.decodeResource(getResources(), R.drawable.z_1_07);
        //90*130
        //900*600;
        //Config.zombieCardWith = (int)((Config.deviceWidth * 90 /900.0)/2);
        //Config.zombieCardHeight = (int)((Config.deviceHeight * 130/600.0)/2);
        Config.zombieCardWith = (int)(Config.plantCardWidth * 90.0 * 3/4 / 80.0);
        Config.zombieCardHeight = (int)(Config.zombieCardWith * 130.0 / 90.0);

        for (int i = 0; i < Config.zombieFrames.length;i ++){
            Config.zombieFrames[i] = DeviceTools.resizeBitmap(Config.zombieFrames[i],Config.zombieCardWith,Config.zombieCardHeight);
        }

    }

    private void initBulletCard(){

        Config.bullet = BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
        Config.bulletCardWidth = 28 * Config.gameBK.getWidth() / 900  ;
        Config.bulletCardHeight = Config.bulletCardWidth;
        Config.bullet = DeviceTools.resizeBitmap(Config.bullet, Config.bulletCardWidth, Config.bulletCardHeight);

    }
    private void initSunCount() {
        Config.sunCount = 50;
        Config.sunCountLocation = new Point();
        float widthScale =  Config.seedBank.getWidth() / 446.0f;
        float heightScale =  Config.seedBank.getHeight() / 87.0f;
       // Config.sunCountLocation.x = (int)(20 * Config.seedBank.getWidth() / 446.0f + (Config.deviceWidth - Config.seedBank.getWidth()) /2.0);
        //Config.sunCountLocation.y = (int)(65 * Config.seedBank.getHeight() / 87.0f) + 20;
        Config.sunCountRect = new Rect((int)(12 *widthScale),(int)(61*heightScale),(int)(65*widthScale),(int)(83*heightScale));
        Config.sunCountRect.left += (Config.deviceWidth - Config.seedBank.getWidth()) /2.0;
        Config.sunCountRect.right += (Config.deviceWidth - Config.seedBank.getWidth()) /2.0;
    }
}
