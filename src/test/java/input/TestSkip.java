package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.StringInput;

public class TestSkip {
    private final String strTest1 = "-+--++-$";

    @Test
    public void test1() {
        StringInput input = new StringInput(strTest1);
        input.skip('-');
        Assertions.assertEquals('+', input.read());
    }

    @Test
    public void test2() {
        StringInput input = new StringInput(strTest1);
        Assertions.assertEquals('-', input.skip('-', '+'));
        Assertions.assertEquals('$', input.read());
    }

    @Test
    public void test3() {
        StringInput input = new StringInput("-+--++-");
        Assertions.assertEquals('-', input.skip('-', '+'));
        Assertions.assertFalse(input.available());
    }

    @Test
    public void test4() {
        StringInput input = new StringInput("");
        Assertions.assertEquals(-1, input.skip('-', '+'));
        Assertions.assertFalse(input.available());
    }
}
