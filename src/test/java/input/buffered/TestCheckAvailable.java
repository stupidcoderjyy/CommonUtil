package input.buffered;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.readers.StringByteReader;

public class TestCheckAvailable {

    @Test
    public void test1() {
        BufferedInput input = new BufferedInput(new StringByteReader("12345678"), 4);
        input.read(8);
        Assertions.assertFalse(input.available());
    }

    @Test
    public void test2() {
        BufferedInput input = new BufferedInput(new StringByteReader(""), 4);
        Assertions.assertFalse(input.available());
    }
}
