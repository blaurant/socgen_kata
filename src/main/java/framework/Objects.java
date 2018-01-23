package framework;

public class Objects {

    public static <T> T requireNotNull(T obj) {
        return requireNotNull(obj, "object is null");
    }

    public static <T> T requireNotNull(T obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
        return obj;
    }

}
