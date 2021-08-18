package com.tianjin.beichentiyu.utils;

public class DistanceUtil {

    public static String distanceUtil(String disStr) {
        try{
            double num1 = Double.valueOf(disStr);
            if(num1 < 1000){
                int num2 = (int) num1;
                disStr = num2 + " 米";
            }else{
                int num2 = (int) num1/10;
                disStr = num2 / 100f + " 千米";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return disStr;
    }
}
/*      try{
              Double num1 = Double.valueOf(disStr);
              BigDecimal bigDecimal = new BigDecimal(num1);
              bigDecimal = new BigDecimal(num1.toString());
              System.out.println(bigDecimal);
              //bigDecimal = new BigDecimal("6.214822313132341212666E+18");
              System.out.println(bigDecimal.toPlainString());
              Double dis = Double.valueOf(bigDecimal.toPlainString());
              if(dis < 1000){
        Double num2 =  dis;
        disStr = num2 + " 米";
        }else{
        Double num2 =  dis/10;
        disStr = num2 / 100f + " 千米";
        }
        }catch (Exception e){
        e.printStackTrace();
        }*/
