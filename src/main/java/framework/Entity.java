package framework;

public class Entity<ID> {

    private ID id;
    private int version;

    public Entity(ID id, int version) {
        if (id == null)
            throw new IllegalArgumentException("id is null");
        this.id = id;
        if (version < 0)
            throw new IllegalArgumentException("version is < 0");
        this.version = version;
    }

    public Entity(ID id) {
        this(id, 0);
    }

    public ID getId() {
        return id;
    }

    public String getIdAsString() {
        return id.toString();
    }

    protected void hack_forceId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id.equals(entity.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getVersion() {
        return version;
    }

    public void incrVersion() {
        this.version = this.version + 1;
    }

    public void rollbackVersion() {
        this.version = this.version - 1;
    }

    protected void forceVersion(int version) {
        this.version = version;
    }
}
