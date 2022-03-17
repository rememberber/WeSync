package com.luoboduner.wesync.test;

import com.sun.xml.internal.ws.util.StringUtils;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, JDOMException {
        System.out.println("测试");

     //历史数据xml文件载入缓存
        String historyFiledir="C:\\Users\\Administrator\\Desktop\\temp\\document历史.xml";
        String newFileDir="C:\\Users\\Administrator\\Desktop\\temp\\document原文.xml";
     List<String>  strList= Test.initHistoryData(historyFiledir);

    //1.创建SAXBuilder对象
    SAXBuilder saxBuilder = new SAXBuilder();
        String s = "";
    //2.创建输入流
    InputStream is=null;
    is = new FileInputStream(new File(newFileDir));
    //3.将输入流加载到build中
    Document document = saxBuilder.build(is);
    //4.获取根节点
    Element rootElement = document.getRootElement();
    //5.获取子节点
    List<Element> children = rootElement.getChildren();
        for (Element child : children) {
//            System.out.println("通过rollno获取属性值:"+child.getAttribute("rollno"));
            List<Attribute> attributes = child.getAttributes();
            //打印属性
            for (Attribute attr : attributes) {
                System.out.println(attr.getName()+":"+attr.getValue());
            }
            List<Element> childrenList = child.getChildren();
            System.out.println("======获取子节点-start======");
            for (Element o : childrenList) {
//                System.out.println("节点名:"+o.getName()+"---"+"节点值:"+o.getValue());
                System.out.println("节点名:"+o.getName()+"---"+"节点值:"+Test.getValue(o));
                //如果当前段落有文字
                if(!Test.getValue(o).equals("")){

                    //TODO 到内存中遍历查询是否有一样的段落 如果有一样的段落则高亮显示当前段落
                    boolean ishighligh=false;
                    for(String str:strList){
//                        if(Test.getValue(o).contains("The emergence of the Internet of Things (IoT) has made it possible to connect continuously to, and among, a much wider range of devices. Most such devices are consumer devices, such as lights, thermostats, and the like. More complex industrial ")){
//                            System.out.println("暂停");
//                        }
                        if(Test.getValue(o).equals(str)){
                            ishighligh=true;
                            break;
                        }
                    }
                    //如果需要高亮则进行段落标注
                    if(ishighligh){
                        Test.addContextByTagName(o,"rPr",null);
                    }
                }
            }
        System.out.println("======获取子节点-end======");
        }

        // 输出新的xml
//        File xmlFile = new File("products.xml");
        Document doc  = new Document();
//        Document document1 = doc.setContent(rootElement);
        try {

            FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\temp\\newdocument.xml");

            XMLOutputter outputter = new XMLOutputter();

            outputter.setFormat(Format.getPrettyFormat());

            outputter.output(document, writer);

            outputter.output(document, System.out);

            writer.close(); // close writer

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    //获取内容不为空的 字符
    public static String getValue(Element element) {
        StringBuilder buffer = new StringBuilder();
        Iterator i$ = element.getContent().iterator();

        while(true) {
            Content child;
            do {
                if (!i$.hasNext()) {
                    return buffer.toString().trim();
                }

                child = (Content)i$.next();
            } while(!(child instanceof Element) && !(child instanceof Text));

            buffer.append(child.getValue().replaceAll("\r|\n|\t", ""));

        }
    }

    /**
     * 为叶子节点名为 xxx的标签 添加新标签
     */

    /**
     * 递归向下查询 指定标签， 层级过多(10层以上) 不建议使用
     * 将所有为 名称为nodeName的标签 追加context
     * @param element
     * @param nodeName
     * @param context
     */
    public static void addContextByTagName(Element element,String nodeName,Element context){

        Iterator i$ = element.getContent().iterator();
        boolean comeon=true;
        c:while(comeon) {
            Content child;
            do {
                if (!i$.hasNext()) {
                    return;
                }
                child = (Content)i$.next();
            } while(!(child instanceof Element));
//            buffer.append(child.getValue().trim());
            Element ele=(Element)child;
            //递归查询下级标签
            addContextByTagName(ele,nodeName,context);
            //当前层级是否有nodeName
            if(ele.getName().equals(nodeName)){
                System.out.println("添加高亮显示");
                Element highlightElement=new Element("highlight") ;
                highlightElement.setNamespace(ele.getNamespace());
                highlightElement.setAttribute("val","yellow",ele.getNamespace());
                ele.addContent(highlightElement);
            }
        }

    }
    private static List<String> initHistoryData(String filedir) throws IOException, JDOMException {
        List<String> strlist = new ArrayList<>();
        //1.创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        //2.创建输入流
        InputStream is=null;
        is = new FileInputStream(new File(filedir));
        //3.将输入流加载到build中
        Document document = saxBuilder.build(is);
        //4.获取根节点
        Element rootElement = document.getRootElement();
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
//            System.out.println("通过rollno获取属性值:"+child.getAttribute("rollno"));
            List<Attribute> attributes = child.getAttributes();
            //打印属性
//            for (Attribute attr : attributes) {
//                System.out.println(attr.getName()+":"+attr.getValue());
//            }
            List<Element> childrenList = child.getChildren();
//            System.out.println("======获取子节点-start======");
            //将分散在不同标签同一个段落(p标签)的内容 进行组合，并加载到内存中
            for (Element o : childrenList) {
                if(!Test.getValue(o).equals("")){
                    strlist.add(Test.getValue(o));
                }
            }
//            System.out.println("======获取子节点-end======");
        }

        return strlist;
    }
}
