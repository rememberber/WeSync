package com.luoboduner.wesync.test;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author liweiqing
 * @date 2022/3/16 17:13
 * @description 获取遗传字符中的 关键字出现的位置
 */
public class Test2 {

    public static void main(String[] args) {

//        for(int i=10;i<40;i++){
//            for(int j=1;j<5;j++){
//                if(i>20){
//                    break;
//                }
//                System.out.println("jjjjjjjjjjjjjjj_____"+j);
//            }
//            System.out.println("i____"+i);
//        }

        String str="(h)  Confidential Confidential a Confidential s Information shall  mean all information designated by a Party as confidential " +
                "and which is disclosed by UIH Confidential to the Distributor, is disclosed by the Distributor to UIH, or is embodied " +
                "in the Products, regardless of the form in which it is disclosed, relating to markets, customers, products, " +
                "patents, inventions, procedures which, methods, designs, strategies, plans, assets, liabilities, prices, costs, " +
                "revenues, profits, organizations, employees, agents, or, in the case of UIH’s information, the algorithms, " +
                "programs, user interfaces, source and object codes and organization of the Products.";

        // 单词索引
        Map<String, Map<String,String>> wordIndex = new LinkedHashMap<>();

        Map<String, String> wordcontent1 = new HashMap<>();
        wordcontent1.put("products","");
        wordcontent1.put("procedures","");

        Map<String, String> wordcontent2 = new HashMap<>();
        wordcontent2.put("information designated","");
        wordcontent2.put("inventions","");

        wordIndex.put("p",wordcontent1);
        wordIndex.put("i",wordcontent2);

        String strs[]=str.split(" ");

//        for (String s : strs) {
//            if(s.trim().)
//        }
        List<String> list = new ArrayList<>();
        list.add("a Confidential");
        list.add("information");
        list.add("which");

        //index,length
        Map<Integer,Integer> indexMap = new HashMap<>();
        for (String keyword : list) {
            findStrIndexOf(str,keyword,indexMap);
        }

        //TODO 根据已知的 索引

    }

    /**
     *  递归查询文本段落中关键词出现的位置并记录到map获取文本在段落中出现的位置索引并记录到 map中
     * @param str       待查询的字符串
     * @param keyword   待匹配的关键
     * @param indexMap  匹配结果索引map
     */
    public static void findStrIndexOf(String str,String keyword,Map<Integer,Integer> indexMap) {
        findStrIndexOf(str,keyword,indexMap,0,0);
    }

    /**
     *  递归查询文本段落中关键词出现的位置并记录到map获取文本在段落中出现的位置索引并记录到 map中
     * @param str
     * @param keyword
     * @param indexMap
     */
    private static void findStrIndexOf(String str,String keyword,Map<Integer,Integer> indexMap,int i,int previaIndex){
        if(str.indexOf(keyword)>-1){
            //检查当前关键词紧跟的后一个字符是否为英文字母，如果为英文字母，则认为不是该关键词
            int keywordIndex=str.indexOf(keyword);
            int realIndex=0;
            if(i>0){
                realIndex=keywordIndex+previaIndex+keyword.length();
                //如果下一个字符不是字母 则认为当前匹配的 关键词为完整单词
                if(!checkNextCharIsEn(str, keyword)){
                    indexMap.put(realIndex,keyword.length());
                    System.out.println("keyword:"+keyword+"出现位置："+realIndex);
                }
            }else{
                //首次匹配关键词
                i++;
                realIndex=keywordIndex;
                //如果下一个字符不是字母 则认为当前匹配的 关键词为完整单词
                if(!checkNextCharIsEn(str, keyword)){
                    indexMap.put(keywordIndex,keyword.length());
                    System.out.println("keyword:"+keyword+"出现位置："+realIndex);
                }
            }
            //递归调用该方法 获取该文本的其它位置
            findStrIndexOf(str.substring(keywordIndex+keyword.length()), keyword, indexMap,i,realIndex);
        }else {
            return;
        }
    }

    /**
     * 检查关键字的后一个字符是否为英文字母
     * @param str
     * @param keyword
     */
    private static boolean checkNextCharIsEn(String str, String keyword) {
        if(StringUtils.isEmpty(str)){
            return false;
        }
        int nextCharIndex= str.indexOf(keyword)+ keyword.length();
        String nextChar= str.substring(nextCharIndex,(nextCharIndex+1)>= str.length()? str.length():nextCharIndex+1);
        //匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。
        if(nextChar.matches("^[a-zA-Z]")){
            return true;
        }else{
            return false;
        }
    }

}
