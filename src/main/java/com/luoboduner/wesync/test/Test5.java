package com.luoboduner.wesync.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liweiqing
 * @date 2022/3/17 17:42
 * @description
 */
public class Test5 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int index=1;
        list.add("111");
        list.add("222");
        list.add("333");
        list.remove(index);
        list.add(1,"999");
        list.add(2,"999_2");
        list.add(3,"999_3");


        for (String s1 : list) {
            System.out.println(s1);
        }

    }
}
