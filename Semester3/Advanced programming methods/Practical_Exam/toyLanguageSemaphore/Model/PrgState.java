package Model;

import Model.Adt.*;
import Model.Stmt.IStmt;
import Model.Value.Value;
import Model.Adt.IFileTable;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    IFileTable<String, BufferedReader> fileTable;
    MyIList<Value> out;
    IHeap heap;

    ISemaphoreTable semaphoreTable;
    private int id;
    private static int currentId = 0;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTable, MyIList<Value> list, IFileTable
                    <String, BufferedReader> fileTable, IHeap heap, ISemaphoreTable semaphoreTable, IStmt originalProgram) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = list;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphoreTable = semaphoreTable;
        this.exeStack.push(originalProgram);
        this.id = setId();
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "Program State " + "id = " + id + ":\n" +
                "exeStack=" + exeStack +
                "\nsymTable=" + symTable +
                "\nout=" + out + "\n" +
                "\nfileTable=" + fileTable + "\n" +
                "\n heap=" + heap + "\n";
    }

    public IFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IFileTable<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public void setHeap(IHeap heap) {
        this.heap = heap;
    }

    public int getId() {
        return this.id;
    }

    public synchronized int setId() {
        currentId++;
        return currentId;
    }

    public boolean NotCompleted(){
        if (exeStack.isEmpty()) return false;
        return true;
    }

    public PrgState oneStep() throws MyException{
        if(exeStack.isEmpty()) throw new MyException("Program state stack is empty!");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public ISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }

    public void setSemaphoreTable(ISemaphoreTable semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }
}
