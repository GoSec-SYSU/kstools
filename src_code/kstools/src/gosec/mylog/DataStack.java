package gosec.mylog;

import java.util.Stack;

public class DataStack {
    Stack<Integer> dataLenStack = new Stack<>();
    Stack<Object> dataStack = new Stack<>();
    int len = 0;
    public void addData(Object obj) {
        len++;
        dataStack.add(obj);
    }
    public void stopAdding() {
        dataLenStack.add(len);
        len = 0;
    }
    public Stack<Object> getData() {
        Stack<Object> resStack = new Stack<>();
        if(dataLenStack.size()==0 || dataStack.size()==0 || dataStack.size()<dataLenStack.firstElement())   return resStack;
        int curLen = dataLenStack.pop();
        for(int i=0; i<curLen; i++) resStack.add(dataStack.pop());
        return resStack;
    }
}
