package com.HuangQi.Game.global;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.HashMap;

/**
 * Created by Qi on 2015/5/3.
 */
public class Config {
    public static float scaleWidth;
    public static float scaleHeight;

    public static int deviceWidth;//屏幕宽度
    public static int deviceHeight;//屏幕高度

    public static int seedCardWidth;//卡片宽度
    public static int seedCardHeight;//卡片高度

    public static int plantCardWidth;
    public static int plantCardHeight;

    public static int sunCardWidth;
    public static int sunCardHeight;

    public static int groundCardWidth;
    public static int groundCardHeight;

    public static int seedCardPitch;//卡片间距(第一张开片到第二张开片的距离)
    public static int seedCardFristLocationX;//第一张种子卡片的宽度

    public  static Bitmap gameBK;
    public  static Bitmap seedBank;
    public static Bitmap seedFlower;
    public static Bitmap seedPea;

    public static Bitmap[] flowerFrames = new Bitmap[8];
    public static Bitmap[] peaFrames = new Bitmap[8];
    public static Bitmap[] zombieFrames = new Bitmap[7];

    public static HashMap<Integer,Point> plantPoint = new HashMap<>();//所有种植植物的地皮的中心点；

    public static Point plantFristCenterLocation = new Point(); // 左上角第一块种植植物的地皮的中心点
    public static Point plantFristLocationXY = new Point(); // 左上角第一块种植植物的地皮的左上角

    public static int[] raceWayLocationY = new int[5];

    public static Point emplacePlantFristLocation;

    public static Bitmap sun;
    public static Point sunCollecterCentrePoint = new Point();


    public static int zombieCardWith;
    public static int zombieCardHeight;
    public static Bitmap bullet;
    public static int bulletCardWidth;
    public static int bulletCardHeight;
    public static int sunCount = 50;
    public static Point sunCountLocation = new Point();
    public static Rect sunCountRect;


    public static int bulletShootSend;
    public static int bulletShootShoot;

}
