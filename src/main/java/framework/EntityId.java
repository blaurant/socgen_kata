package framework;


import static framework.Objects.requireNotNull;

public class EntityId<T> {

    private T id;

    public EntityId(T id) {
        this.id = requireNotNull(id, "id is null");
    }

    public T get() {
        return id;
    }

    public String asString() {
        return id.toString();
    }

    @Override
    public String toString() {
        return "EntityId{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId<?> entityId = (EntityId<?>) o;
        return id.equals(entityId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
