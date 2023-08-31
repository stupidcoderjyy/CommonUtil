package input;

import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.InputException;
import stupidcoder.util.input.StringInput;

public class InputDebugTest {

    @Test
    public void testBufferedInput() {
        try(BufferedInput input = BufferedInput.fromResource("/test.txt")) {
            input.skip(10);
            throw new InputException(input);
        } catch (InputException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringInput() {
        try(StringInput input = new StringInput("dwadwadwadwadwa达瓦达瓦达瓦wadwadwadwa")) {
            input.skip(10);
            throw new InputException(input);
        } catch (InputException e) {
            e.printStackTrace();
        }
    }
}
