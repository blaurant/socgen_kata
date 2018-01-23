package framework;

public class Integers {

    public static Integer requirePositifOrZero(Integer number, String msg) {
        if (number < 0)
            throw new IllegalArgumentException(msg);
        return number;
    }
}
