package com.choxsu.utils.kit;

import java.io.*;
import java.text.ParseException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFilesKit {

    public static void compress(File f, String baseDir, ZipOutputStream zos) {
        if (!f.exists()) {
            System.out.println("待压缩的文件目录或文件" + f.getName() + "不存在");
            return;
        }
        File[] fs = f.listFiles();
        BufferedInputStream bis = null;
        byte[] bufs = new byte[1024 * 10];
        FileInputStream fis = null;
        try {
            for (File f1 : fs) {
                String fName = f1.getName();
                System.out.println("压缩：" + baseDir + fName);
                if (f1.isFile()) {
                    ZipEntry zipEntry = new ZipEntry(baseDir + fName);
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(f1);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    //如果需要删除源文件，则需要执行下面2句
                    fis.close();
                    f1.delete();
                } else if (f1.isDirectory()) {
                    compress(f1, baseDir + fName + "/", zos);
                }
            }//end for
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bis)
                    bis.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void deleteDir(File fs) {
        if (fs.isFile()) {
            fs.delete();
        } else {
            File[] files = fs.listFiles();
            if (files == null) {
                fs.delete();
            } else {
                for (File file : files) {
                    deleteDir(file);
                }
                fs.delete();
            }
        }
    }

    public static void main(String[] args) throws ParseException {

        String sourceFilePath = "C:\\Users\\22835\\IdeaProjects\\choxsu\\blog\\src\\main\\webapp\\gen";
        String outFilePath = "C:\\Users\\22835\\Desktop\\genFile";
        File sourceDir = new File(sourceFilePath);
        File zipFile = new File(outFilePath + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String baseDir = "genFil/";
            compress(sourceDir, baseDir, zos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (zos != null)
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
