package Repository;
import Model.Animals;

public class Repositoy implements IRepository{
    private final Animals[] entities;
    private int length;
    public Repositoy()
    {
        entities = new Animals[3];
        length = 0;
    }

    public void add(Animals a) throws RepositoryException
    {
        for(Animals animal : entities)
            if(animal != null && animal.equals(a))
                throw new RepositoryException(String.format(("Animal %s was already added!"), a.toString()));
        entities[length++] = a;

    }

    public void remove(int index) throws  RepositoryException
    {
        int ind = -1;
        for (int i = 0; i < length; i++) {
            if (entities[i].getId() == index) {
                ind = i;
                break;
            }
        }

        if (ind == -1)
            throw new RepositoryException(String.format("Animal with id '%d' was not found!", ind));
        else {
            for (int i = ind; i < length; i++)
                entities[i] = entities[i + 1];
            length--;
        }

    }

    public int size()
    {
        return entities.length;
    }

    public Animals[] getEntities() //throws RepositoryException
    {
        return entities;
    }
}
