//package gosec.mylog;
//
//import java.io.*;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class Writer {
//    public static String packageName = "com.cns.mc.activity";
//        public static String rootPath = "./";
////    public static String rootPath = File.separator + "data" + File.separator + "data" + File.separator;
//    public static String fileName = "data.txt";
//    public static FileWriter bufferedWriter;
//    public static String dataLogFileName;
//    private static final int bufSize = 1024;
//
//
//    static {
//        dataLogFileName = String.format(rootPath + "%s/%s", packageName, fileName);
//        File dataLogFile = new File(dataLogFileName);
//        // 创建data.txt
//        if (!dataLogFile.getParentFile().exists()) {
//            boolean result = dataLogFile.getParentFile().mkdirs();
//            if (!result) {
//                System.out.println("创建失败");
//            }
//        }
//        if (dataLogFile.exists()) {
//            deleteFileByFilePath(dataLogFileName);
//        }
//        // 创建文件写入流
//        try {
////            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
////                    new FileOutputStream(dataLogFile, true)), bufSize);
//            bufferedWriter = new FileWriter(dataLogFile, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //flush
////        new Timer("writer looper").schedule(new TimerTask() {
////            @Override
////            public void run() {
////                try {
////                    System.out.println("into flush");
////                    bufferedWriter.flush();
////                    System.out.println("over flush");
//////                    System.out.println("flush");
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, 1000, 1000);
//    }
//
//    public synchronized static void write(String hash, String content) {
////        try {
////            bufferedWriter.write(hashHead + content);
////            fileWriter.flush();
////            System.out.println(path + " " + hash);
////            fileWriter.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        try {
//            String hashHead = String.format("Hash: %s\n", hash);
//            bufferedWriter.write(hashHead + content);
//            System.out.println(hashHead);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedWriter.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void deleteFileByFilePath(String filePath) {
//        try {
//            File file = new File(filePath);
//            System.out.println(file.exists());
//            System.out.println(file.isFile());
//            if (file.exists() && file.isFile()) {
//                if (file.delete()) {
//                    System.out.println("successfully delete file: " + file.toString());
//                } else {
//                    System.out.println("delete error: " + file.toString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

package gosec.mylog;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class Writer {
    public static String packageName;
    public static String fileName;

    static {
        packageName = "com.cns.mc.activity";
        fileName = "data.txt";
    }

    public static ConcurrentHashMap<String, BufferedWriter> writerMap = new ConcurrentHashMap<>();

    // bufferWriter
    public static void write(String hash, String content) {
//        System.out.println(content);
//        Path path = Paths.get("/data/data/", packageName, fileName);
        String path = String.format("/data/data/%s/%s", packageName, fileName);
        File file = new File(path.toString());
        if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
            }
        }
        BufferedWriter bufferedWriter = writerMap.getOrDefault(path, null);
        try {
            if (bufferedWriter == null) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file, true)), 1024);
                writerMap.put(path, bufferedWriter);
            }
            String hashHead = String.format("Hash: %s\n", hash);
            bufferedWriter.write(hashHead + content);
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
