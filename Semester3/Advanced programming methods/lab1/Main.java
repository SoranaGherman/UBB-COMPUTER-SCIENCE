import Controller.Controller;
import Repository.Repositoy;
import Repository.IRepository;
import View.ConsoleUi;

public class Main {
    public static void main(String[] args) {
        IRepository repository = new Repositoy();
        Controller controller = new Controller(repository);
        ConsoleUi console = new ConsoleUi(controller);
        console.run();
    }
}
