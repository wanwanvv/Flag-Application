package com.example.flagapplication.utils;

import com.example.flagapplication.R;

public class ColorUtils {
    public static int getColorFromStr(String s){
        int colorId = 0;
        switch (s) {
            case "Default color":
                colorId = R.color.moren;
                break;
            case "Green":
                colorId = R.color.luolelv;
                break;
            case "Yellow":
                colorId = R.color.yaoyanhuang;
                break;
            case "Tomato":
                colorId = R.color.fanqiehong;
                break;
            case "Grey":
                colorId = R.color.didiaohui;
                break;
            case "Orange":
                colorId = R.color.juzihong;
                break;
            case "Blue":
                colorId = R.color.shenkonglan;
                break;
        }
        return colorId;
    }
}
