package com.luoboduner.wesync.test;

public class Test4 {

    public static void main(String[] args) {
        String str="(h) Confidential";/*d Confidential Confidential Information shall  mean all information designated by a Party as confidential " +
                "and which is disclosed by UIH to the Distributor, is disclosed by the Distributor to UIH, or is embodied " +
                "in the Products, regardless of the form in which it is disclosed, relating to markets, customers, products, " +
                "patents, inventions, procedures which, methods, designs, strategies, plans, assets, liabilities, prices, costs, " +
                "revenues, profits, organizations, employees, agents, or, in the case of UIH’s information, the algorithms, " +
                "programs, user interfaces, source and object codes and organization of the Products.";*/

        String keyword = "Confidential";
//        System.out.println(str.indexOf("Confidential"));
//        System.out.println(str.substring(str.indexOf("Confidential")+12));
        //4+12+1
        int nextCharIndex=str.indexOf(keyword)+keyword.length();
        String nextChar=str.substring(nextCharIndex,(nextCharIndex+1)>=str.length()?str.length():nextCharIndex+1);
        System.out.println("nextChar:"+nextChar);
        if(nextChar.matches("^[a-zA-Z]")){
            System.out.println("是字母");
        }
    }
}
