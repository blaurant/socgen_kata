package framework;

public abstract class SimpleValueObject<T> {

    public final T value;

    public SimpleValueObject(T value) {
        Objects.requireNotNull(value, "cannot be null");
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), String.valueOf(value));
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SimpleValueObject<?> other = (SimpleValueObject<?>) obj;
        return java.util.Objects.equals(value, other.value);
    }

    public T value() {
        return value;
    }


}
