package model;

public abstract class BaseEntity {

    protected int id;
    protected String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        setName(name);
    }

    public abstract String getEntityType();
    public abstract String getDetails();

    public String basicInfo() {
        return id + " - " + name;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }
}
