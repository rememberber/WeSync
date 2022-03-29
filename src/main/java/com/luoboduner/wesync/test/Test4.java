package com.luoboduner.wesync.test;

import java.util.*;

public class Test4 {

    public static void main(String[] args) {
        String str="(h) Confidential Confidential a Confidential s information shall  mean all " +
                "and which is which by UIH Confidential to which";

        String keyword = "Confidential";
//        System.out.println(str.indexOf("Confidential"));
//        System.out.println(str.substring(str.indexOf("Confidential")+12));
        //4+12+1
//        int nextCharIndex=str.indexOf(keyword)+keyword.length();
//        String nextChar=str.substring(nextCharIndex,(nextCharIndex+1)>=str.length()?str.length():nextCharIndex+1);
//        System.out.println("nextChar:"+nextChar);
//        if(nextChar.matches("^[a-zA-Z]")){
//            System.out.println("是字母");
//        }


//        /**
//         * keyword:a Confidential出现位置：30,length:14
//         * keyword:information出现位置：47,length:11
//         * keyword:which出现位置：79,length:5
//         * keyword:which出现位置：88,length:5
//         * keyword:which出现位置：117,length:5
//         */
//
//        System.out.println(str.substring(0,0));
//        System.out.println(str.substring(0,30));
//        System.out.println(str.substring(30,44));
//        System.out.println(str.substring(44,47));
//        System.out.println(str.substring(47,58));
//        System.out.println(str.substring(58,79));
//        System.out.println(str.substring(79,84));
//        System.out.println(str.substring(84,88));
//        System.out.println(str.substring(88,93));
//        System.out.println(str.substring(93,117));
//        System.out.println(str.substring(117,122));

        //首个元素的处理
        //中间元素的处理
        //最后一个元素的处理

//        Map<Integer, Integer> map = new HashMap<>();
//        LinkedHashMap<Integer, Integer> map2 = new LinkedHashMap<>();
//        extracted(map, map2);

        int[][] a = new int[][]{};

    }

    private static void extracted(Map<Integer, Integer> map, LinkedHashMap<Integer, Integer> map2) {
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        //从小到大排序（从大到小将o1与o2交换即可）
        Collections.sort(list, (p1, p2) -> p1.getKey()-p2.getKey());

        //新建一个LinkedHashMap，把排序后的List放入

        for (Map.Entry<Integer, Integer> entry : list) {
            map2.put(entry.getKey(), entry.getValue());
        }

        //遍历输出
        for (Map.Entry<Integer, Integer> entry : map2.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
