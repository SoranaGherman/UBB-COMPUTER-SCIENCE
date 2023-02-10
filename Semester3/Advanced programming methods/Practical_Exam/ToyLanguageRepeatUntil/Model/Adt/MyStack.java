package Model.Adt;

import Model.Adt.MyIStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stk;

    public MyStack(Stack<T> stk) {
        this.stk = stk;
    }

    public MyStack() {
        this.stk = new Stack<T>();
    }

    @Override
    public boolean isEmpty() {
       return stk.empty();
    }

    @Override
    public T pop() {
        return stk.pop();
    }

    @Override
    public void push(T v) {
        stk.push(v);
    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) stk.toArray());
        Collections.reverse(list);
        return list;
    }

    public Stack<T> getStk() {
        return stk;
    }

    public void setStk(Stack<T> stk) {
        this.stk = stk;
    }


    @Override
    public String toString() {
        return stk.toString();
    }
}
