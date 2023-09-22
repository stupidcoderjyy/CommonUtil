package input.compile;

import org.junit.jupiter.api.Test;
import stupidcoder.util.input.CompilerInput;

public class TestError {

    @Test
    public void test_point() {
        try (CompilerInput input = CompilerInput.fromResource("/input/compile/test_error.txt")){
            input.approach('$');
            throw input.errorAtForward("test error:$");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (CompilerInput input = CompilerInput.fromResource("/input/compile/test_error.txt")) {
            input.approach('$');
            input.mark();
            input.approach('#');
            throw input.errorAtMark("test error:$");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (CompilerInput input = CompilerInput.fromResource("/input/compile/test_error.txt")) {
            input.approach('#');
            input.mark();
            throw input.errorAtMark("test error:#");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_range() {
        try (CompilerInput input = CompilerInput.fromResource("/input/compile/test_error.txt")) {
            input.approach('=');
            input.mark();
            throw input.errorMarkToMark("test ranged error");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (CompilerInput input = CompilerInput.fromResource("/input/compile/test_error.txt")) {
            input.read();
            input.mark();
            input.read();
            input.mark();
            throw input.errorMarkToMark("test ranged error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
