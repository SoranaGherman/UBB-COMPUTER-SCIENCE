package Model.Stmt;

import Model.Adt.IHeap;
import Model.Adt.IFileTable;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    private Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IFileTable<String, BufferedReader> fileTbl = state.getFileTable();
        IHeap heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        if (val.getType().equals(new StringType())){
            StringValue strV = (StringValue) val;
            String file = strV.getVal();

            try{
                BufferedReader f = fileTbl.lookup(file);
                f.close();}
            catch(IOException ex){
                System.out.println("File " + ex + "could not be closed");
            }

            fileTbl.delete(file);

        }else throw new MyException("Invalid type");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;
        throw new MyException("Expression from statement '%s' must have string type!");
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "CloseRFile{" +
                "exp=" + exp +
                '}';
    }
}
