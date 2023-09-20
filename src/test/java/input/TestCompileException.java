package input;

import org.junit.jupiter.api.Test;
import stupidcoder.util.input.CompileException;

public class TestCompileException {
    @Test
    public void test1() {
        try {
            throw new CompileException("test message", 5, "goto[0][1] = 5;" ,"test.txt").setPos(4);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            throw new CompileException("test message", 5, "goto[0][1] = 5;" ,"test.txt").setPos(2,5);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }
}
