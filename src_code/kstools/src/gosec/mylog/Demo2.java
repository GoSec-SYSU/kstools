package gosec.mylog;

public class Demo2 {
    public static void main(String[] args) {
        new Thread(() -> {
            Log.start();
            // 打印调用栈
            Log.logInvokeStack();
            // 打印参数
            Log.add(1);
            Log.stopAdding();
            Log.logParameters();
        }).start();
        new Thread(() -> {
            Log.start();
            // 打印调用栈
            Log.logInvokeStack();
            // 打印参数
            Log.add(1);
            Log.stopAdding();
            Log.logParameters();
        }).start();
    }
}
