package gosec.mylog;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Writer {
    public static String packageName;
    public static String fileName;
    public static BufferedWriter bufferedWriter;

    static {
        packageName = "com.cns.mc.activity";
        fileName = "data.txt";
        String path = String.format("/data/data/%s/%s", packageName, fileName);
//        String path = String.format("./data.txt");
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
            }
        }
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)), (int) Math.pow(1024, 2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new Timer("writer looper").schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);
    }

//    public static ConcurrentHashMap<String, BufferedWriter> writerMap = new ConcurrentHashMap<>();

    // bufferWriter
    public static void write(String content) {
//        System.out.println(content);
        try {
            bufferedWriter.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void write(String content) {
////        System.out.println(content);
////        Path path = Paths.get("/data/data/", packageName, fileName);
//
//        File file = new File(path.toString());
//        if (!file.getParentFile().exists()) {
//            boolean result = file.getParentFile().mkdirs();
//            if (!result) {
//                System.out.println("创建失败");
//            }
//        }
//        BufferedWriter bufferedWriter = writerMap.getOrDefault(path, null);
//        try {
//            if (bufferedWriter == null) {
//                bufferedWriter = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream(file, true)), (int) Math.pow(1024, 2));
//                writerMap.put(path, bufferedWriter);
//            }
//            bufferedWriter.write(content);
//            bufferedWriter.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
