package gosec.mylog;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");//设置日期格式
    protected static final String longLine = "****************************************************************************************************************";
    protected static final String line = "--------------------------------------------------------------------------------------";
    private static final Set<String> primitiveTypeSet = new HashSet<>();

    static {
        primitiveTypeSet.add("java.lang.Boolean");
        primitiveTypeSet.add("java.lang.Byte");
        primitiveTypeSet.add("java.lang.Character");
        primitiveTypeSet.add("java.lang.Double");
        primitiveTypeSet.add("java.lang.Float");
        primitiveTypeSet.add("java.lang.Integer");
        primitiveTypeSet.add("java.lang.Long");
        primitiveTypeSet.add("java.lang.Short");

        primitiveTypeSet.add("Z");
        primitiveTypeSet.add("B");
        primitiveTypeSet.add("C");
        primitiveTypeSet.add("D");
        primitiveTypeSet.add("F");
        primitiveTypeSet.add("I");
        primitiveTypeSet.add("J");
        primitiveTypeSet.add("S");
    }

    protected static String getCurrentTimeMillis(){
        return Long.toString(System.currentTimeMillis());
    }
    protected static String getCurrentTimeFormat(){
        return df.format(System.currentTimeMillis());
    }

    protected static String head() {
        StringBuffer sb = new StringBuffer();
        // 当前时间
        sb.append("Start Time: " + System.currentTimeMillis() + " " + df.format(System.currentTimeMillis()) + "\n");
        // 当前的线程名
        String curThreadName = Thread.currentThread().toString();
        sb.append("Thread name: " + curThreadName + "\n");
        // 类名
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        sb.append("class and method name: " + new Throwable().getStackTrace()[2] + "\n");
        return sb.toString();
    }

    protected static String indent(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) sb.append(" ");
        return sb.toString();
    }

    protected static String endLine(String str) {
        return str +
                "End Time: " + System.currentTimeMillis() + " " + df.format(System.currentTimeMillis()) + "\n" +
                line + "\n";
    }

    protected static String logData(String type, Object... objs) {
        StringBuffer sb = new StringBuffer();
        // 参数
        sb.append(handleParas(type, objs));
        return sb.toString();
    }

    protected static String handleParas(String type, Object... objs) {
        StringBuilder sb = new StringBuilder();
        if (type == null) sb.append(indent(4) + "type can not be null!!!\n");
        else if (objs == null || objs.length == 0) sb.append(indent(4) + "no parameter\n");
        else {
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                if (obj == null) sb.append(indent(4) + type + " " + i + " is null\n");
                else
                    sb.append(indent(4) + type + " " + i + ": " + obj.getClass().getName() + " -> " + handlePara(obj)+"\n");
            }
        }
        return sb.toString();
    }

    protected static String handlePara(Object obj) {
        if (obj == null) return "null";
        // 防止toString里面也插了这玩意，循环调用toString，然后死循环
        String objName = obj.getClass().getName();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        for(int i=2; i<=6 && i<stackElements.length; i++){
            StackTraceElement stackTraceElement = stackElements[i];
            if(stackTraceElement.getClassName().equals(objName) && stackTraceElement.getMethodName().equals("toString"))
                return "Endless loop";
        }
        // 处理返回
        String ans = "";
        try{
            ans = obj.toString();
        }catch (Exception e){
            ans = e.toString();
        }
        return ans;
    }
}


















