package nit.history.data.mock;

import nit.history.data.Entity;
import nit.history.data.EntityGroup;

public class TestEntity implements Entity {

    String name;


    public TestEntity(String name) {
        this.name = name;
    }


    @Override
    public EntityGroup getGroup() {
        return null;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestEntity) {
            TestEntity ent = (TestEntity)obj;
            return ent.getName().equals(getName());
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return getName().hashCode();
    }


    @Override
    public String getID() {
        return name;
    }

}
