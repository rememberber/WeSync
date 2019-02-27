package com.luoboduner.wesync.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 小工具类
 *
 * @author Bob
 */
public class Utils {

    /**
     * 获取系统当前时间yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取备份文件名(系统当前时间yyyy-MM-dd HHmmss)
     *
     * @return
     */
    public static String getCurrentTimeForBakName() {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        return df.format(new Date());
    }

    /**
     * 计算下一次同步时间
     *
     * @return
     */
    public static String getNextSyncTime() {

        // 1、获取当前时间，获取到的时间类型是long类型的，单位是毫秒
        long currentTime = System.currentTimeMillis();
        // 2、在这个基础上加上定时执行时间：
        String schedule = ConstantsTools.CONFIGER.getSchedule();
        if ("true,false,false,false,false,false,false".equals(schedule)) {
            currentTime += 5 * 60 * 1000;
        } else if ("false,true,false,false,false,false,false".equals(schedule)) {
            currentTime += 15 * 60 * 1000;
        } else if ("false,false,true,false,false,false,false".equals(schedule)) {
            currentTime += 30 * 60 * 1000;
        } else if ("false,false,false,true,false,false,false".equals(schedule)) {
            currentTime += 60 * 60 * 1000;
        } else if ("false,false,false,false,true,false,false".equals(schedule)) {
            currentTime += 24 * 60 * 60 * 1000;
        } else if ("false,false,false,false,false,true,false".equals(schedule)) {
            currentTime += 7 * 24 * 60 * 60 * 1000;
        } else if ("false,false,false,false,false,false,true".equals(schedule)) {
            return "明天 " + ConstantsTools.CONFIGER.getScheduleFixTime();
        }

        // 3、格式化时间，获取到的就是当前时间加上定时执行之后的时间
        Date date = new Date(currentTime);
        // 4、建立时间格式化对象：
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取某字符串在字符串数组中的index
     *
     * @param arr
     * @param key
     * @return 返回某字符串在字符串数组中的index，若找不到返回-1
     */
    public static int getStrArrIndex(String[] arr, String key) {

        for (int i = 0; i < arr.length; i++) {
            if (key.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 比较两个key完全相同的map内容是否完全一致
     *
     * @param map1
     * @param map2
     * @return
     */
    public static boolean mapCompare4PrimKey(Map<String, String> map1, Map<String, String> map2) {
        boolean ifEquals = true;
        for (String key : map1.keySet()) {
            if (!map1.get(key).equals(map2.get(key))) {
                ifEquals = false;
            }
        }

        return ifEquals;
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return
     */
    public static String getPingYin(String src) {

        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 获取linkedHashSet中元素的索引位置
     *
     * @param linkedHashSet
     * @param string
     * @return
     */
    public static int getIndexInLinkedHashSet(LinkedHashSet<String> linkedHashSet, String string) {
        int index = -1;
        Iterator<String> linkedSetStringIt = linkedHashSet.iterator();
        while (linkedSetStringIt.hasNext()) {
            index++;
            String temp = linkedSetStringIt.next();
            if (temp.equals(string)) {
                return index;
            }

        }

        return -1;
    }
}