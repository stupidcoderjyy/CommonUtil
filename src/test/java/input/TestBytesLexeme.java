package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;

import java.nio.charset.StandardCharsets;

public class TestBytesLexeme {

    @Test
    public void test1() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_retract.txt");
        input.skip(10);
        String l = input.lexeme();
        input.retract(10);
        input.skip(10);
        Assertions.assertEquals(l, new String(input.bytesLexeme(), StandardCharsets.UTF_8));
    }
}
