package gosec.mylog;

public class Demo2 {
    public static void main(String[] args) {
        new Thread(() -> {
            Log.start();
            // ��ӡ����ջ
            Log.logInvokeStack();
            // ��ӡ����
            Log.add(1);
            Log.stopAdding();
            Log.logParameters();
        }).start();
        new Thread(() -> {
            Log.start();
            // ��ӡ����ջ
            Log.logInvokeStack();
            // ��ӡ����
            Log.add(1);
            Log.stopAdding();
            Log.logParameters();
        }).start();
    }
}
