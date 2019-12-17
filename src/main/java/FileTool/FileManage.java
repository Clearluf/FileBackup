package FileTool;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FileManage {
    public static void main(String[] args) {}
    /**
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(String sourceFilepath, String targetFilepath)
    throws IOException {
        File sourceFile = new File(sourceFilepath);
        File targetFile = new File(targetFilepath);
        File targetparent = new File(targetFile.getParent());
        if (!targetparent.exists()) {
            targetparent.mkdirs();
        }
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);
        byte[] b = new byte[1024];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        outBuff.flush();
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }
    public long getSize(File[] fileArr) {
        long size = 0;
        if (null == fileArr || fileArr.length <= 0) //
        {
            return 0;
        }
        for (File file: fileArr) {
            if (file.isFile()) {
                size += file.length();
            }
            if (file.isDirectory()) {
                getSize(file.listFiles());
            }
        }
        return size;
    }
    /**
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    //    public static void copyDirectiory(String sourceDir, String targetDir)  
    //            throws IOException {  
    //        // ÐÂ½¨Ä¿±êÄ¿Â¼   
    //        (new File(targetDir)).mkdirs();  
    //        // »ñÈ¡Ô´ÎÄ¼þ¼Ðµ±Ç°ÏÂµÄÎÄ¼þ»òÄ¿Â¼   
    //        File[] file = (new File(sourceDir)).listFiles();  
    //        for (int i = 0; i < file.length; i++) {  
    //            if (file[i].isFile()) {  
    //                // Ô´ÎÄ¼þ   
    //                File sourceFile=file[i];  
    //                // Ä¿±êÎÄ¼þ   
    //               File targetFile=new   
    //               File(new File(targetDir).getAbsolutePath()+File.separator+file[i].getName());  
    //                copyFile(sourceFile,targetFile);  
    //            }  
    //            if (file[i].isDirectory()) {  
    //                // ×¼±¸¸´ÖÆµÄÔ´ÎÄ¼þ¼Ð   
    //                String dir1=sourceDir + "/" + file[i].getName();  
    //                // ×¼±¸¸´ÖÆµÄÄ¿±êÎÄ¼þ¼Ð   
    //                String dir2=targetDir + "/"+ file[i].getName();  
    //                copyDirectiory(dir1, dir2);  
    //            }  
    //        }  
    //    } 
    /**
     * @param path
     * @return
     */
    public String getMD5(String path) {
        BigInteger b = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] mdb = md.digest();
            b = new BigInteger(1, mdb);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return b.toString(16);
    }
    /**
     * @param
     */
    public String getFileName(String filePath) {
        String[] fp = filePath.split("\\\\");
        int len = fp.length;
        return fp[len - 1];
    }
    /**
     * @param filePath
     * @return
     */
    public static Long getFileLastModifyTime(String filePath) {
        File file = new File(filePath);
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicview.readAttributes();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return file.lastModified();
        }
    }
    /**
     * @param filePath
     */
    public String getFileLastModifyTimeFormat(String filePath) {
        File file = new File(filePath);
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicview.readAttributes();
            long time = attr.creationTime().toMillis();
            String s = String.valueOf(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattime = sdf.format(new Date(Long.parseLong(s)));
            return formattime;
        } catch (Exception e) {
            e.printStackTrace();
            long time = file.lastModified();
            String s = String.valueOf(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattime = sdf.format(new Date(Long.parseLong(s)));
            return formattime;
        }
    }
    /**
     * @param path
     * @return
     */
    public long getfilesize(String path) {
        File f = new File(path);
        long l = f.length();
        return l;
        //		if(l<1024) {
        //			return l+"Byte";		
        //		}
        //		else if(l>=1024&&l<1024*1024) {
        //			String kb=String.format("%.2f", l/1024);
        //			return kb+"KB";	
        //		}
        //		else {
        //			String mb=String.format("%.2f", l/(1024*1024));
        //			return mb+"MB";	
        //		}
    }
    /**
     */
    public static String convert(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append(String.format("\\u%04x", Integer.valueOf(c)));
        }
        return unicode.toString();
    }
    /**
     */
    public static String revert(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }
}