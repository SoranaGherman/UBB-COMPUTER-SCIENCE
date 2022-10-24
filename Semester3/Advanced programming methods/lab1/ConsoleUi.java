package View;

import Controller.Controller;
import Controller.ControllerException;
import java.util.Scanner;
import Model.Animals;

public class ConsoleUi {
    private Controller ctrl;
    public ConsoleUi(Controller ctrl)
    {
        this.ctrl = ctrl;
    }
    public void run()
    {
        while(true)
        {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter option: ");
            int opt = scanner.nextInt();

            if(opt == 1)
                add();
            else if(opt == 2)
                remove();
            else if(opt == 3)
                printAll(ctrl.getAll());
            else if(opt == 4)
                filter();
            else if(opt == 0)
                return;
            else
                System.out.println("Invalid option!");
        }
    }


    public void printMenu(){
        System.out.println("1.Add object\n"+
                "2.Delete object\n"+
                "3.Print all objects\n"+
                "4.Filter objects by weight(weight greater than 3 kg)\n"+
                "0.Exit\n"+
                "");
    }

    private void add() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter type [flock | cow | pig]: ");

        String type = scanner.nextLine();
        AnimalTypes animal_type;

        switch (type) {
            case "flock":
                animal_type = AnimalTypes.FLOCK;
                break;
            case "cow":
                animal_type = AnimalTypes.COW;
                break;
            case "pig":
                animal_type = AnimalTypes.PIG;
                break;
            default:
                System.out.println("Invalid animal type.");
                return;
        }

        System.out.print("Enter id: ");
        int id = scanner.nextInt();
        System.out.print("Enter weight: ");
        int weight = scanner.nextInt();

        try {
            ctrl.add(animal_type, id, weight);
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void remove()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the animal you want to remove:");
        int id = scanner.nextInt();
        try{
            ctrl.remove(id);
        }catch (ControllerException e){
            System.out.println(e.getMessage());
        }
    }

    private void printAll(Animals[] animals)
    {
        for (Animals a : animals) {
            if (a == null) break;
            System.out.println(a);
        }
    }

    private void filter()
    {
        printAll(ctrl.getAnimalsWithCertainWeight());
    }

}
