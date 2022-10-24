package Controller;

import Model.Animals;
import Model.Flock;
import Model.Cow;
import Model.Pig;
import Repository.IRepository;
import Repository.RepositoryException;
import View.AnimalTypes;

public class Controller {
    private IRepository repo;
    public Controller(IRepository repo){
        this.repo = repo;
    }
    public Animals[] getAnimalsWithCertainWeight()
    {
        Animals[] data = this.repo.getEntities();
        Animals[] animals = new Animals[this.repo.size()];
        int cnt = 0;
        for(Animals a: data)
        {
            if(a == null)
                break;

            if(a.getWeight() > 3)
                animals[cnt++] = a;
        }
        return animals;
    }

    public void add(AnimalTypes type, int id, int weight) throws ControllerException {
        Animals a = getAnimalFromData(type, id, weight);

        try {
            repo.add(a);
        } catch (RepositoryException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void remove(int id) throws ControllerException {
        try {
            repo.remove(id);
        } catch (RepositoryException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    private Animals getAnimalFromData(AnimalTypes type, int id, int weight) throws ControllerException {
        switch (type) {
            case FLOCK:
                return new Flock(id, weight);
            case COW:
                return new Cow(id, weight);
            case PIG:
                return new Pig(id, weight);
            default:
                throw new ControllerException("Invalid animal type.");
        }
    }

    public Animals[] getAll() //throws RepositoryException
    {
        return this.repo.getEntities();
    }

    public int getSize()
    {
        return this.repo.size();
    }

}
