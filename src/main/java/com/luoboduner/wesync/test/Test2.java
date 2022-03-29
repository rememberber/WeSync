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


        String str="(h) Confidential Confidential a Confidential s information shall  mean all " +
                "and which is which by UIH Confidential to which xxx";
        List<TranslationContent> resultList = new ArrayList<>();

        //index,length
        // 关键词 init
        List<String> keywordlist = new ArrayList<>();
        List<String> result;
        keywordlist.add("a Confidential");
        keywordlist.add("information");
        keywordlist.add("which");

        resultList=splitTranslationContent(str,keywordlist);
        for (TranslationContent translationContent : resultList) {
            System.out.println(translationContent);
        }
    }

    /**
     * 根据关键词 确定文本区域 并区分关键词与普通文本
     * @param str 待查询的文本
     * @param keywordlist 待匹配的关键词列表
     * return 按原字符返回有序的划分后的文本集合
     */
    public static List<TranslationContent> splitTranslationContent(String str,List<String> keywordlist) {
        List<TranslationContent> resultList = new ArrayList<>();
        Map<Integer,Integer> indexMap = new HashMap<>();
        Map<Integer, Integer> linkeMap;
//        Map<Integer, Integer> splitIndexMap = new LinkedHashMap<>();

        for (String keyword : keywordlist) {
            findStrIndexOf(str,keyword,indexMap);
        }

        //如果有搜索到关键词则 进行排序及连续区间的校验
        if (indexMap.size()>0){
            //对已检测到的关键词索引升序 并转换为有序的HashMap
            linkeMap=mapConvertDescLinkedMap(indexMap);

            /**
             * TODO 存在的隐患,后续与业务人员确定后再优化
             * 暂时不考虑区间重叠的特殊情况,因为当前的向后检索方式不会出现区间重叠问题.
             * 但是有可能因关键词之间出现包含关系并且在搜索的文本内,无法判定以哪个关键词进行断句
             * 如 an hour,hour 同时出现在关键词列表,又出现在待检索的文章内
             */
            //确定文本区域划分的坐标
            splitIndexTextMap(linkeMap, resultList, str);
        }else {
            //TODO 继续其它节点检查
            System.out.println("继续其它节点检查");
        }

        return resultList;
    }

    private static void splitIndexTextMap(Map<Integer, Integer> linkeMap, List<TranslationContent> translationContentList, String str) {
        int i=0;
        //上一个元素的 key
        int currentKey=0;
        int previousKeyIndeEnd=0;
        for (Map.Entry<Integer, Integer> entry : linkeMap.entrySet()) {
            currentKey=entry.getKey();
            //首元素的特殊情况处理
            if(i==0){
                if(currentKey==0){
//                    System.out.println("["+0+","+(currentKey+entry.getValue())+")");
                    translationContentList.add(new TranslationContent(
                            str.substring(0,currentKey+entry.getValue()),
                            ContentEnum.KEYWORD,0,currentKey+entry.getValue()));
                }else{
//                    System.out.println("[0,"+currentKey+")");
//                    System.out.println("["+currentKey+","+(currentKey+entry.getValue())+")");
                    translationContentList.add(new TranslationContent(
                            str.substring(0,currentKey),
                            ContentEnum.PLAIN_TEXT,0,currentKey));
                    translationContentList.add(new TranslationContent(
                            str.substring(currentKey,currentKey+entry.getValue()),
                            ContentEnum.KEYWORD,currentKey,currentKey+entry.getValue()));
                }
                //检查是否为末尾词汇..., 如果不是末尾词汇则补全后续索引区间
                if(i== linkeMap.size()-1 && (currentKey+entry.getValue())< str.length()){
//                    System.out.println("["+(currentKey+entry.getValue())+","+ str.length()+")");
                    translationContentList.add(new TranslationContent(
                            str.substring(currentKey+entry.getValue(), str.length()),
                            ContentEnum.PLAIN_TEXT,currentKey+entry.getValue(), str.length()));
                }
                i++;
                previousKeyIndeEnd=currentKey+entry.getValue();
                continue;
            }

            //TODO 中间元素的处理
            translationContentList.add(new TranslationContent(
                    str.substring(previousKeyIndeEnd,currentKey),
                    ContentEnum.PLAIN_TEXT,previousKeyIndeEnd,currentKey));
            translationContentList.add(new TranslationContent(
                    str.substring(currentKey,currentKey+entry.getValue()),
                    ContentEnum.KEYWORD,currentKey,currentKey+entry.getValue()));
            //TODO 最后一个元素的处理
            //检查是否为末尾词汇..., 如果不是末尾词汇则补全后续索引区间
            if(i== linkeMap.size()-1 && (currentKey+entry.getValue())< str.length()){
//                System.out.println("["+(currentKey+entry.getValue())+","+ str.length()+")");
                translationContentList.add(new TranslationContent(
                        str.substring(currentKey+entry.getValue(), str.length()),
                        ContentEnum.PLAIN_TEXT,currentKey+entry.getValue(), str.length()));
            }

            i++;
            previousKeyIndeEnd=currentKey+entry.getValue();
        }
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
                    System.out.println("keyword:"+keyword+"出现位置："+realIndex +",length:"+keyword.length());
                }
            }else{
                //首次匹配关键词
                i++;
                realIndex=keywordIndex;
                //如果下一个字符不是字母 则认为当前匹配的 关键词为完整单词
                if(!checkNextCharIsEn(str, keyword)){
                    indexMap.put(keywordIndex,keyword.length());
                    System.out.println("keyword:"+keyword+"出现位置："+realIndex+",length:"+keyword.length());
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

    /**
     * map转linkedHashMap 并排序
     * @param map
     * @return linkedHashMap
     */
    private static LinkedHashMap<Integer,Integer> mapConvertDescLinkedMap(Map<Integer, Integer> map) {
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        LinkedHashMap<Integer, Integer> linkedMap=new LinkedHashMap<>();
        //从小到大排序（从大到小将o1与o2交换即可）
        Collections.sort(list, (p1, p2) -> p1.getKey()-p2.getKey());
        //new LinkedHashMap，把排序后的List放入
        for (Map.Entry<Integer, Integer> entry : list) {
            linkedMap.put(entry.getKey(), entry.getValue());
        }
        //遍历输出
//        for (Map.Entry<Integer, Integer> entry : linkedMap.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
        return linkedMap;
    }
}
