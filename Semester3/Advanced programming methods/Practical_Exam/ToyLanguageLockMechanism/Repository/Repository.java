package Repository;

import Model.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Repository implements  IRepository{
    List<PrgState> programSt;
    String logFilePath;

    public Repository(List<PrgState> programSt, String logFilePath) {
        this.programSt = programSt;
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(prg.toString());
            logFile.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgStates() {
        return this.programSt;
    }

    @Override
    public void setPrgStates(List<PrgState> l) {
        this.programSt = l;
    }

}
