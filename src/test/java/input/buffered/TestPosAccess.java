package input.buffered;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;

public class TestPosAccess {

    @Test
    public void test_crlf_1() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf.txt");
        input.skip(4);
        Assertions.assertEquals("123", input.currentLine());
    }

    @Test
    public void test_crlf_2() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf.txt");
        input.skipLine();
        input.skip(3);
        Assertions.assertEquals("456", input.currentLine());
    }

    @Test
    public void test_crlf_3() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf.txt");
        input.skip(4);
        Assertions.assertEquals("123", input.currentLine());
    }

    @Test
    public void test_crlf_4() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf.txt");
        input.skip(5);
        Assertions.assertEquals("", input.currentLine());
    }

    @Test
    public void test_crlf_5() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf_2.txt", 4);
        input.ignoreLexemeLengthLimit();
        input.skipLine();
        input.skip(3);
        Assertions.assertEquals("56", input.currentLine());
    }

    @Test
    public void test_crlf_6() {
        BufferedInput input = BufferedInput.fromResource("/buffered/test_pos_crlf_3.txt", 4);
        input.ignoreLexemeLengthLimit();
        input.skipLine();
        input.skip(2);
        Assertions.assertEquals("56", input.currentLine());
    }
}
