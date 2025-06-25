package utils;

import java.util.List;
import java.util.Random;

public class RandomHelper {
    private static final Random random = new Random();

    public static <T> T getRandomElement(List<T> list) {
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
