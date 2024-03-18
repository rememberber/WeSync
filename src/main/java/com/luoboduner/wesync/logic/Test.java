package com.luoboduner.wesync.logic;

/**
 * @author liweiqing
 * @date 2023/11/27 18:17
 * @description
 */
public class Test {
    public static void main(String[] args) {
        String outputForder = "C:\\Users\\Administrator\\Desktop\\vivoReport";
        if(outputForder.trim().lastIndexOf("\\")==outputForder.trim().length()-1){
            outputForder=outputForder.substring(0,outputForder.length()-1)+"\\"+"vivo report.xlsx";
        }else{
            outputForder=outputForder.substring(0,outputForder.length())+"\\"+"vivo report.xlsx";
        }
        System.out.println(outputForder);
    }
}
