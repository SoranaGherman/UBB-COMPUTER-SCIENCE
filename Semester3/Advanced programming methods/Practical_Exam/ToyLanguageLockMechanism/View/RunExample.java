package View;

import Controller.Controller;
import Model.MyException;

class RunExample extends Command{
    private Controller ctrl;
    public RunExample(String key, String desc, Controller ctr){
        super(key,desc);
        this.ctrl = ctr;
    }

    @Override
    public void execute() {
        try {
            ctrl.allSteps();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
