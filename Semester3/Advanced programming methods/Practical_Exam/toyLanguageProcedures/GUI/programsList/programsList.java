package GUI.programsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

import java.util.Arrays;
import java.util.List;
import GUI.programController.*;
import javafx.util.Pair;

public class programsList {
    private programController programExecutorController;
    private IProcTable procTable;
    public void setProgramExecutorController(programController programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        this.procTable = new ProcTable();
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<String, Type>());
                ArrayList<PrgState> l1 = new ArrayList<PrgState>();
                MyIStack<MyIDictionary<String, Value>> symTableStack = new MyStack<>();
                symTableStack.push(new MyDictionary<>());
                PrgState prg1 = new PrgState(new MyStack<IStmt>(), symTableStack,
                        new MyList<Value>(), new FileTable<String, BufferedReader>(), new Heap(), procTable,
                        selectedStatement);
                l1.add(prg1);
                IRepository r1 = new Repository(l1, "log" + (id + 1) + ".txt");
                Controller c1 = new Controller(r1);
                programExecutorController.setController(c1);
            } catch (MyException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllStatements() {
        List<IStmt> allStatements = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        allStatements.add(ex1);

        IStmt sumProc = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp("+", new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varSum = new ArrayList<>();
        varSum.add("a");
        varSum.add("b");
        procTable.put("sum", new Pair<>(varSum, sumProc));

        IStmt prodProc = new CompStmt(
                new VarDeclStmt("v" , new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp("*", new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varProd = new ArrayList<>();
        varProd.add("a");
        varProd.add("b");
        procTable.put("product", new Pair<>(varProd, prodProc));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("w", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new CompStmt(
                                        new AssignStmt("w", new ValueExp(new IntValue(5))),
                                        new CompStmt(
                                                new CallProcedure("sum", new ArrayList<>(Arrays.asList(new ArithExp("*", new VarExp("v"), new ValueExp(new IntValue(10))), new VarExp("w")))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new forkStmt(
                                                                new CompStmt(
                                                                        new CallProcedure("product", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w")))),
                                                                        new forkStmt(
                                                                                new CallProcedure("sum", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w"))))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStatements.add(ex2);
        return FXCollections.observableArrayList(allStatements);
    }

}
