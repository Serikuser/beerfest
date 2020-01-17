package by.siarhei.beerfest.entity;

public abstract class Entity implements Cloneable {
    protected long id;

    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
