package com.rtl.customview.customViews;

import android.util.Log;

import com.rtl.customview.ActualCoffeMachineInside;
import com.rtl.customview.CoffeMechine;

public class test {


    public void run(CoffeMechine coffeMechine){
        Log.d("Sathish_SSS", "run: "+coffeMechine.getCoffee());

        Log.d("Sathish_SSS", "run: "+coffeMechine.getTea());

        Log.d("Sathish_SSS", "run: "+coffeMechine.getHotWater());

        Log.d("Sathish_SSS", "run: "+coffeMechine.getWormWater());
    }



}
