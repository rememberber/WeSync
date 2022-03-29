package com.luoboduner.wesync.test;

import org.dom4j.DocumentHelper;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import sun.applet.Main;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author liweiqing
 * @date 2022/3/16 17:13
 * @description word转换zip包,并读取相应的 word目录下的document.xml 文件
 */

public class Test3 {

    public static void main(String[] args) throws Exception {
        File f1 = new File("C:\\Users\\Administrator\\Desktop\\temp3\\test.docx");
        // 目标文件
        String f2path="C:\\Users\\Administrator\\Desktop\\temp3\\test_new.zip";
        File f2 = new File(f2path);
        Test3.copyFile(f1, f2);
        String result=readZipDocumentFile(f2path);

        //TODO test 文件转移

        //1.创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        String s = "";
        //2.创建输入流
        InputStream is=null;
        java.io.Reader in = new StringReader(result);
        Document document = (new SAXBuilder()).build(in);

        //TODO 获取document中的  t标签中的内容
        Element rootElement = document.getRootElement();
        //5.获取子节点
        List<Element> children = rootElement.getChildren();

//        for (Element child : children) {
////            System.out.println("通过rollno获取属性值:"+child.getAttribute("rollno"));
//            List<Attribute> attributes = child.getAttributes();
//            //打印属性
//            for (Attribute attr : attributes) {
//                System.out.println(attr.getName()+":"+attr.getValue());
//            }
//            List<Element> childrenList = child.getChildren();
//            System.out.println("======获取子节点-start======");
//            for (Element o : childrenList) {
////                System.out.println("节点名:"+o.getName()+"---"+"节点值:"+o.getValue());
//                System.out.println("节点名:"+o.getName()+"---"+"节点值:"+Test.getValue(o));
//                //如果当前段落有文字
//                if(!Test.getValue(o).equals("")){
//
//                    //TODO 到内存中遍历查询是否有一样的段落 如果有一样的段落则高亮显示当前段落
//                    boolean ishighligh=false;
//                    for(String str:strList){
//                        if(Test.getValue(o).equals(str)){
//                            ishighligh=true;
//                            break;
//                        }
//                    }
//                    //如果需要高亮则进行段落标注
//                    if(ishighligh){
//                        Test.addContextByTagName(o,"rPr",null);
//                    }
//                }
//            }
//            System.out.println("======获取子节点-end======");
//        }


        //TODO 存入 新文件中 start
//        try {
//
//            FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\temp3\\newdocument.xml");
//
//            XMLOutputter outputter = new XMLOutputter();
//
//            outputter.setFormat(Format.getPrettyFormat());
//
//            outputter.output(document, writer);
//
//            outputter.output(document, System.out);
//
//            writer.close(); // close writer
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //TODO 存入 新文件中 end

    }


    public static String readZipDocumentFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        StringBuffer result = new StringBuffer("");
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
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
//                            System.out.println(line);
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

}
