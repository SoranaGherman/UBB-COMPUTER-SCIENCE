package Repository;

import Model.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState prgState) throws MyException;
    List<PrgState> getPrgStates();
    void setPrgStates(List<PrgState> l);
}
