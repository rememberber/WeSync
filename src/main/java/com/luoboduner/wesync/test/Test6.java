package com.luoboduner.wesync.test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author liweiqing
 * @date 2022/3/21 16:16
 * @description
 */
public class Test6 {
    public static void main(String[] args) throws Exception {
        String file="C:\\Users\\Administrator\\Desktop\\temp3\\test.docx";
        String path="C:\\Users\\Administrator\\Desktop\\temp3\\temp";
        String zipPath = "C:\\Users\\Administrator\\Desktop\\temp3\\test_new.zip";
//        String file="C:\\Users\\Administrator\\Desktop\\temp3\\t1.zip";
//        String path="C:\\Users\\Administrator\\Desktop\\temp3\\temp";
//        String zipPath = "C:\\Users\\Administrator\\Desktop\\temp3\\t1_1.zip";
        /** 测试压缩方法1  */
        FileOutputStream fos1 = new FileOutputStream(new File(zipPath));
        Test6.toZip(path, fos1,true);

//        Test6.readZipDocumentFile(file,zipPath);
    }

    public ByteArrayOutputStream parse(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    public static void readZipDocumentFile(String file,String newZipPath) throws Exception {

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        FileOutputStream fos1 = new FileOutputStream(new File(newZipPath));
        ZipOutputStream zos = new ZipOutputStream(fos1);
//        File sourceFile = new File(srcDir);
//        compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);

        byte[] buf = new byte[2048];
        while ((ze = zin.getNextEntry()) != null) {
            if(!ze.isDirectory()){
                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
                System.out.println("bbb:"+ze.getName());
                System.out.println("文件处理："+ze.getName());
                zos.putNextEntry(new ZipEntry(ze.getName()));
                // copy文件到zip输出流中
                int len=0;
                ZipFile zf = new ZipFile(file);
                InputStream in2 = zf.getInputStream(ze);
                while ((len = in2.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                // Complete the entry
                zos.closeEntry();
                in2.close();
            } else {
//                File[] listFiles = sourceFile.listFiles();
//                if(listFiles == null || listFiles.length == 0){
                    // 需要保留原来的文件结构时,需要对空文件夹进行处理
                    if(true){
//                        // 空文件夹的处理
                        System.out.println("文件夹处理:"+ze.getName()+"/");
                        zos.putNextEntry(new ZipEntry(ze.getName().substring(0,ze.getName().length()-1)));
                        // 没有文件，不需要文件的copy
                        zos.closeEntry();
                    }
            }
        }
        zin.closeEntry();
    }

    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos		 zip输出流
     * @param name		 压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        int  BUFFER_SIZE = 2 * 1024;
        byte[] buf = new byte[BUFFER_SIZE];

        //取消根目录文件夹

        if(name.contains("temp/")){
            name=name.replace("temp/","");
        }

        System.out.println(name);
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                System.out.println("处理空文件夹");
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }

                }
            }
        }
    }

    public static void copyFile(File file, File target) throws Exception {
        // 输入流 --> 从一个目标读取数据
        // 输出流 --> 向一个目标写入数据

        long start = System.currentTimeMillis();
        //
        ZipFile zf = new ZipFile(file);

        // 文件输入流并进行缓冲
//        FileInputStream inputStream = new FileInputStream("");
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // 文件输出流并进行缓冲
        FileOutputStream outputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // 缓冲数组
        // 大文件 可将 1024 * 2 改大一些，但是 并不是越大就越快
        byte[] bytes = new byte[256];
        int len = 0;

//        while ((len = inputStream.read(bytes)) != -1) {
//            bufferedOutputStream.write(bytes, 0, len);
//        }
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
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(zf.getInputStream(ze)));
                    InputStream inputStream=zf.getInputStream(ze);
                    String line;
//                    if("word/document.xml".equals(ze.getName())){
//
////                        while ((line = br.readLine()) != null) {
////                            result.append(line);
//////                            System.out.println(line);
////                        }
//                    }else{
                        while ((len = inputStream.read(bytes)) != -1) {
                            bufferedOutputStream.write(bytes, 0, len);
                        }

//                    }
                    inputStream.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();


        // 刷新输出缓冲流
        bufferedOutputStream.flush();
        //关闭流
//        bufferedInputStream.close();
        bufferedOutputStream.close();
//        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        System.out.println("耗时：" + (end - start) / 1000 + " s");

    }
}
