package input.buffered;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.readers.StringByteReader;

public class TestApproach {

    @Test
    public void test1() {
        BufferedInput input = new BufferedInput(new StringByteReader("####\r\n"));
        Assertions.assertEquals('\r', input.approach('\r', '\n'));
        Assertions.assertEquals('\r', input.read());
        input.close();
    }

    @Test
    public void test2() {
        BufferedInput input = new BufferedInput(new StringByteReader("####"));
        Assertions.assertEquals(-1, input.approach('\r', '\n'));
        Assertions.assertFalse(input.available());
        input.close();
    }
}
