package Controller;

import Model.Adt.IHeap;
import Model.Adt.MyIDictionary;
import Model.MyException;
import Model.Adt.MyIStack;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public IRepository getRepo() {
        return repo;
    }

    public void setRepo(IRepository repo) {
        this.repo = repo;
    }
    private ExecutorService executor;
    private List<Integer> getAddressFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    private List<Integer> getAddressFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heap)
    {
        List<Integer> heapTableAddresses = getAddressFromHeap(heap.values());
        return heap.entrySet().stream()
                .filter(e-> (symTableAddresses.contains(e.getKey()) || heapTableAddresses.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgStates());
        while (prgList.size() > 0){
            // ----------- safe garbage collector ----------//
            IHeap sharedHeap = prgList.get(0).getHeap();
            List<MyIDictionary<String, Value> >allSymTables = prgList.stream().map(PrgState::getSymTable).collect(Collectors.toList());
            List<Integer> addressesFromAllSymTables = new ArrayList<Integer>();
            allSymTables.stream().map(table -> getAddressFromSymTable(table.getContent().values())).forEach(addressesFromAllSymTables::addAll);
            sharedHeap.setContent((HashMap<Integer, Value>) safeGarbageCollector(addressesFromAllSymTables, sharedHeap.getContent()));
            // --------------------------------------------//
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgStates());
        }
        executor.shutdownNow();
        repo.setPrgStates(prgList);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p->p.NotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        // before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        // run concurrently one step for each of the existing PrgStates

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p)-> (Callable<PrgState>)(()->{return p.oneStep();}))
                .collect(Collectors.toList());

        // start the execution of callables
        //it returns the list of new created PrgStates (namely thread)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {try {return future.get();} catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                }).filter(p->p!=null).collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //after the execution, print the prgState List into the log file
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        //save the current programs in the repo
        repo.setPrgStates(prgList);
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddressFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, p.getHeap().getContent())));
    }

    public void oneStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repo.getPrgStates());
        oneStepForAllPrg(programStates);
        conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }

    public List<PrgState> getProgramStates() {
        return repo.getPrgStates();
    }

    public void setProgramStates(List<PrgState> prgStates) {
        repo.setPrgStates(prgStates);
    }
}
