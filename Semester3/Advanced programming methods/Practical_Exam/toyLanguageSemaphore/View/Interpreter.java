package View;

import Controller.Controller;
import Model.Adt.*;
import Model.Exp.*;
import Model.MyException;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Interpreter {
    public static void main(String[] args)
    {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        try {

            ex1.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l1 = new ArrayList<PrgState>();
            PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(), new SemaphoreTable(),
                    ex1);
            l1.add(prg1);
            IRepository r1 = new Repository(l1, "log1.txt");
            Controller c1 = new Controller(r1);
            menu.addCommand(new RunExample("1", ex1.toString(), c1));

        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+", new ValueExp(new IntValue(2)), new
                                ArithExp("*", new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp("+", new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        try {
            ex2.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l2 = new ArrayList<PrgState>();
            PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex2);
            l2.add(prg2);
            IRepository r2 = new Repository(l2, "log2.txt");
            Controller c2 = new Controller(r2);
            menu.addCommand(new RunExample("2", ex2.toString(), c2));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }


        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        try {
            ex3.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l3 = new ArrayList<PrgState>();
            PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex3);
            l3.add(prg3);
            IRepository r3 = new Repository(l3, "log3.txt");
            Controller c3 = new Controller(r3);
            menu.addCommand(new RunExample("3", ex3.toString(), c3));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.txt"))),
                    new CompStmt(new OpenRFile(new VarExp("varf")),
                    new CompStmt(new VarDeclStmt("varc", new IntType()),
                    new CompStmt(new ReadFile("varc",new VarExp("varf")),
                    new CompStmt(new PrintStmt(new VarExp("varc")),
                    new CompStmt(new ReadFile("varc", new VarExp("varf")),
                    new CompStmt(new PrintStmt(new VarExp("varc")),
                    new CloseRFile(new VarExp("varf"))))))))));

        try {
            ex4.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l4 = new ArrayList<PrgState>();
            PrgState prg4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex4);
            l4.add(prg4);
            IRepository r4 = new Repository(l4, "log4.txt");
            Controller c4 = new Controller(r4);
            menu.addCommand(new RunExample("4", ex4.toString(), c4));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                    new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))))),
                    new PrintStmt(new VarExp("v")))));

        try {
            ex5.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l5 = new ArrayList<PrgState>();
            PrgState prg5 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex5);
            l5.add(prg5);
            IRepository r5 = new Repository(l5, "log5.txt");
            Controller c5 = new Controller(r5);
            menu.addCommand(new RunExample("5", ex5.toString(), c5));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                    new CompStmt(new NewStmt("a", new VarExp("v")),
                    new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                 new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        try {
            ex6.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l6 = new ArrayList<PrgState>();
            PrgState prg6 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex6);
            l6.add(prg6);
            IRepository r6 = new Repository(l6, "log6.txt");
            Controller c6 = new Controller(r6);
            menu.addCommand(new RunExample("6", ex6.toString(), c6));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                    new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                    new CompStmt(new forkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                    new CompStmt(new PrintStmt(new VarExp("v")),
                    new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                    new CompStmt(new PrintStmt(new VarExp("v")),
                    new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        try {
            ex7.typecheck(new MyDictionary<String, Type>());
            ArrayList<PrgState> l7 = new ArrayList<PrgState>();
            PrgState prg7 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(),
                    new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(),  new SemaphoreTable(),
                    ex7);
            l7.add(prg7);
            IRepository r7 = new Repository(l7, "log7.txt");
            Controller c7 = new Controller(r7);
            menu.addCommand(new RunExample("7", ex7.toString(), c7));
        }
        catch (MyException e){
            System.out.println("EX 1 TYPE CHECK ERROR: " + e.getMessage());
        }
        menu.show();
    }
}
