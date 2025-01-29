package com.rtl.customview;



public class ActualCoffeMachineInside implements CoffeMechine {

   public String Brew(){
        return "coffee powder";
    }

    String getMilk(){
        return "coffee powder";
    }

    String getTeaPowder(){
        return "Tea powder";
    }
    String getWater(){
        return "water";
    }
    String boilWater(){
        return "Boiled Water";
    }

    String heatWater(){
        return "luke Warm Water";
    }


    @Override
    public String getCoffee() {
        Brew();
        getMilk();
        return "Coffee";
    }

    @Override
    public String getTea() {
        getTeaPowder();
        getMilk();
        return "Tea";
    }

    @Override
    public String getHotWater() {
        getWater();
        boilWater();
        return "HotWater";
    }

    @Override
    public String getWormWater() {
        getWater();
        heatWater();
        return "WormWater";
    }
}
