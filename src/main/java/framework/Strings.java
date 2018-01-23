package framework;

public class Strings {

    public static String requireNotEmpty(String str, String msg) {
        if (str == null) throw new IllegalArgumentException(msg);
        if (str.trim().isEmpty()) throw new IllegalArgumentException(msg);
        return str;
    }

}
