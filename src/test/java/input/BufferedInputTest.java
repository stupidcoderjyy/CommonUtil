package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.ReflectionUtil;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.InputException;

import java.lang.reflect.Field;

public class BufferedInputTest {
    @Test
    public void testBARetract() {
            BufferedInput input = BufferedInput.fromResource("/input/test.txt", 16);
            input.ignoreLexemeLengthLimit();
            input.skip(17);
            input.retract(2);
            input.skip(2);
            int fillCount = getFillCount(input);
            int res = input.read();
            Assertions.assertEquals(2, fillCount);
            Assertions.assertEquals('8', (char) res);
    }

    @Test
    public void testABRetract1() {
        try {
            BufferedInput input = BufferedInput.fromResource("/input/test.txt", 16);
            input.ignoreLexemeLengthLimit();
            input.skip(2);
            input.retract(4);
            throw new RuntimeException("failed to throw an error");
        } catch (InputException ignored) {

        }
    }

    @Test
    public void testABRetract2() {
        BufferedInput input = BufferedInput.fromResource("/input/test.txt", 16);
        input.ignoreLexemeLengthLimit();
        input.skip(32);
        input.retract(2);
        input.skip(2);
        int fillCount = getFillCount(input);
        int res = input.read();
        Assertions.assertEquals(3, fillCount);
        Assertions.assertEquals('3', (char) res);
    }

    @Test
    public void testABRetract3() {
        try {
            BufferedInput input = BufferedInput.fromResource("/input/test.txt", 16);
            input.ignoreLexemeLengthLimit();
            input.skip(32);
            input.retract(100);
            throw new RuntimeException("failed to throw an error");
        } catch (InputException ignored) {

        }
    }

    private static int getForward(BufferedInput input) {
        try {
            Field f = BufferedInput.class.getDeclaredField("forward");
            f.setAccessible(true);
            return f.getInt(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int getFillCount(BufferedInput input) {
        return ReflectionUtil.getIntField(input, "fillCount");
    }
}
