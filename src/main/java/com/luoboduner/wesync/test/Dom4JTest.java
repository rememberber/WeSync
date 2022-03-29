package com.luoboduner.wesync.test;


import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.jdom2.Content;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author liweiqing
 * @date 2022/3/17 15:41
 * @description
 */
public class Dom4JTest {

    static int count=0;

    public static void main(String[] args) throws Exception {
        //原始文件
        String  f1path="C:\\Users\\Administrator\\Desktop\\temp3\\test.docx";
        // 目标文件
        String f2path="C:\\Users\\Administrator\\Desktop\\temp3\\test_check.zip";

        File f1 = new File(f1path);
        File f2 = new File(f2path);
        Test3.copyFile(f1, f2);
        String wordDocumentXml=readZipDocumentFile(f2path);

        Document doc = DocumentHelper.parseText(wordDocumentXml);

//        Element root = doc.getRootElement();
//        LinkedHashMap<Integer, Element> elementMap = new LinkedHashMap<>();
//        //整体坐标 和 当前节点 路径的对应关系
//        Map<Integer,String>  pathIndexRelation=new HashMap<>();
//        //升序的 需要处理的节点坐标集合
//        List<Integer> indexMap = new ArrayList<>();
//        //全局y坐标，拆分后的文本集合
//        Map<Integer, List<TranslationContent>> translationContentMap = new HashMap<>();
//
//
//        Dom4JTest.getNodes(root,0,elementMap,indexMap,"",pathIndexRelation,translationContentMap);
//
//
//        /**倒序获取到均为 t 标签，从下到上插入新节点，防止插入过程中 有同级标签导致插入后其它节点的索引对应不上,
//          *向上两级找到 p标签，并进行指定位置插入 拆分后的文本
//          */
//        for (int i = indexMap.size()-1; i >=0; i--) {
//            int index=indexMap.get(i);
//            Element e = elementMap.get(index);
//            String nodepath=pathIndexRelation.get(index);
//            String nodepathSp []=nodepath.split("_");          //层级关系 xxx ...... > p -> r -> t
//            List<TranslationContent> tclist=translationContentMap.get(index);
//            //上级 r标签
//            Element r_tag_ele= (Element) e.getParent().clone();
//            Element p_tag_ele=(Element) e.getParent().getParent().clone();
//            //获取t标签的上一级标签， 如果t的上级是r标签,上上级是p标签 按正常处理逻辑，否则记录到日志 用于后续完善代码处理其它情况。
//            if(r_tag_ele.getName().toLowerCase().equals("r") &&
//                    p_tag_ele.getName().toLowerCase().equals("p")){
//                // 当前r标签在p标签中的 位置
//                Element p_tag=e.getParent().getParent();
//                int r_temp_index=Integer.valueOf(nodepathSp[nodepathSp.length-2]);
//                //移除当前 r 标签 并根据文本内容进行重构操作 start
//                p_tag.elements().remove(r_temp_index-1);
//                for (int j = 0; j < tclist.size(); j++) {
//                    TranslationContent tc =  tclist.get(j);
//                    Element r_temp_tag=(Element)r_tag_ele.clone();
//                    //TODO 判断当前文本是否为易混淆词 是否需要高亮显示
//                        addContextByTagName(r_temp_tag,"rPr",tc);
//                    p_tag.elements().add(r_temp_index+j-1,r_temp_tag);
//                }
//                //移除当前 r 标签 并根据文本内容进行重构操作 end
//                //p_tag.elements().add(r_temp_index,r_tag_ele);
//            }
//
//        }

        System.out.println(doc.asXML());

        //TODO 存入 新文件中 end

    }

    /**
     *
     * @param str
     */
    private static List<TranslationContent> splitTranslationContent(String str) {
        List<TranslationContent> resultList = new ArrayList<>();
        // 关键词 init
        //TODO 单例模式 获取内存中易混淆词语列表
        List<String> keywordlist = new ArrayList<>();
        List<String> result;
        keywordlist.add("a Confidential");
        keywordlist.add("information");
        keywordlist.add("which");
        return Test2.splitTranslationContent(str,keywordlist);
    }


    public static String readZipDocumentFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        StringBuffer result = new StringBuffer("");
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                System.out.println("-------------------file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
            } else {
                System.out.println("-------------------file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    if("word/document.xml".equals(ze.getName())){
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
        return  result.toString();
    }
    /**
     * 复制文件
     *
     * @param resource
     * @param target
     */

    public static void copyFile(File resource, File target) throws Exception {
        // 输入流 --> 从一个目标读取数据
        // 输出流 --> 向一个目标写入数据

        long start = System.currentTimeMillis();

        // 文件输入流并进行缓冲
        FileInputStream inputStream = new FileInputStream(resource);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // 文件输出流并进行缓冲
        FileOutputStream outputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // 缓冲数组
        // 大文件 可将 1024 * 2 改大一些，但是 并不是越大就越快
        byte[] bytes = new byte[1024 * 2];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, len);
        }
        // 刷新输出缓冲流
        bufferedOutputStream.flush();
        //关闭流
        bufferedInputStream.close();
        bufferedOutputStream.close();
        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        System.out.println("耗时：" + (end - start) / 1000 + " s");

    }


    /**
     *
     * @param node  当前节点，并需要继续该节点向下遍历
     * @param i     与path配合 用于绘制 当前节点的层级深度
     * @param elementMap    当前节点在 xml中 节点展开情况下 可视化顺序 序号，和节点信息
     * @param indexlist 记录需要进行拆分 标色的文本信息的 位置
     * @param path   与i配合 用于绘制 当前节点的层级深度
     * @param pathIndexRelation 顺序展开坐标与path的关系记录
     * @param translationContentMap 当前节点内容如果被拆分，记录全局位置及拆分后的集合
     */
    public static void getNodes(Element node, int i, LinkedHashMap<Integer,Element> elementMap,
                                List<Integer> indexlist,String path,Map<Integer,String>  pathIndexRelation,Map<Integer, List<TranslationContent>> translationContentMap){
        System.out.println("--------------------");

        //i 从0开始 0表示第一层
        //path 层级为0时
        if(path.equals("")){
            path=i+"";
        }else{
            path+="_"+i;
        }
         System.out.println("当前路径:"+path);
        //将每次遍历的节点存入 map 考虑用空间换时间的方式,安排后续要处理文本内容节点
        elementMap.put(count,node);
        pathIndexRelation.put(count,path);

        //当前节点的名称、文本内容和属性
        System.out.println("当前节点名称："+node.getName() +"   globalIndex:"+count);//当前节点名称
        System.out.println("当前节点的内容："+node.getTextTrim());//当前节点名称
//        System.out.println("namespace:"+node.getNamespacePrefix());
        List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list

        //如果当前是 t 标签,并且包含文本 products (当作是关键字)
        if(node.getName().equals("t")){
            if(!StringUtils.isEmpty(node.getTextTrim())){
                    //模拟当前文本为需要处理的特殊文本,并记录到 indexList
                    List<TranslationContent> tclist= splitTranslationContent(node.getText());
                    //如果当前节点翻译内容被拆分，则记录当前节点
                    if(tclist.size()>0){
                        indexlist.add(count);
                        translationContentMap.put(count,tclist);
                    }
            }
        }
        String nextNodeParentPath=path;
        for(Attribute attr:listAttr){//遍历当前节点的所有属性
            String name=attr.getName();//属性名称
            String value=attr.getValue();//属性的值
            System.out.println("属性名称："+name+"属性值："+value);
        }

        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();//所有一级子节点的list
        int j=1;
        for(Element e:listElement){//遍历所有一级子节点
            count++;
            getNodes(e,j,elementMap,indexlist,nextNodeParentPath,pathIndexRelation,translationContentMap);//递归
            j++;
        }
    }


    /**
     * 对 r标签下的 rpr添加高亮, t标签增加文本信息
     * @param node
     * @param nodeName
     */
    public static void addContextByTagName(Element node, String nodeName,TranslationContent translationContent){

        //当前操作仅针对 word/document.xml 中 rpr 标签 高亮
        if(node.getName().equals(nodeName)){
            System.out.println(translationContent.getContentEnum().getName());
        }
        if(node.getName().equals(nodeName) && translationContent.getContentEnum()==ContentEnum.KEYWORD) {
            Element highlightElement = DocumentHelper.createElement(new QName("highlight",node.getNamespace()));
            highlightElement.addAttribute(new QName("val",node.getNamespace()),"yellow");
            node.add(highlightElement);
            return ;
        }
        //t 标签 添加对应的文本
        if(node.getName().equals("t")) {
            node.setText(translationContent.getContent());
            return ;
        }
        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();//所有一级子节点的list
        for(Element e:listElement){//遍历所有一级子节点
            addContextByTagName(e,nodeName,translationContent);//递归
        }
    }
}
