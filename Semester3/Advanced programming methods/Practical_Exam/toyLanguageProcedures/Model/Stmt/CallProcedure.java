package Model.Stmt;

import Model.Adt.IHeap;
import Model.Adt.IProcTable;
import Model.Adt.MyDictionary;
import Model.Adt.MyIDictionary;
import Model.Exp.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

import java.util.List;

public class CallProcedure implements  IStmt{

    private String procedureName;
    private final List<Exp> expressions;

    public CallProcedure(String procedureName, List<Exp> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getTopSymTable();
        IHeap heap = state.getHeap();
        IProcTable procTable = state.getProcTable();
        if (procTable.isDefined(procedureName)) {
            List<String> variables = procTable.lookUp(procedureName).getKey();
            IStmt statement = procTable.lookUp(procedureName).getValue();
            MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
            for(String var: variables) {
                int ind = variables.indexOf(var);
                newSymTable.add(var, expressions.get(ind).eval(symTable, heap));
            }
            state.getSymTable().push(newSymTable);
            state.getExeStack().push(new Return());
            state.getExeStack().push(statement);
        } else {
            throw new MyException("Procedure not defined!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}
