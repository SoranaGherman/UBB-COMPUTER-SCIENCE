package Model.Stmt;

import Model.Adt.IFileTable;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.Adt.IHeap;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    String var_name;
    Exp exp;

    public ReadFile(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable().peek();
        IFileTable<String, BufferedReader> fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        if (symTable.isDefined(var_name)) {
            Value val = symTable.lookup(var_name);
            Type type = val.getType();
            if (type.equals(new IntType())) {
                StringValue value = (StringValue) exp.eval(symTable, heap);
                String file = value.getVal();

                BufferedReader fr = fileTable.lookup(file);
                try {
                    String line = fr.readLine();
                    Value v;
                    if (line == null)
                        v = new IntValue(0);
                    else
                        v = new IntValue(Integer.parseInt(line));
                    symTable.update(var_name, v);
                } catch (IOException ex) {
                    System.out.println("Could not find file" + file);
                }
            } else throw new MyException("Invalid type.");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type exp_type = exp.typecheck(typeEnv);
        Type var_type = typeEnv.lookup(var_name);

        if (!exp_type.equals(new StringType()))
            throw new MyException("Expression in statement '%s' must be a string!");
        if (!var_type.equals(new IntType()))
            throw new MyException("Type of variable in statement '%s' must be integer!");

        return typeEnv;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "ReadFile{" +
                "var_name='" + var_name + '\'' +
                ", exp=" + exp +
                '}';
    }
}
