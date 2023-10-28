import org.junit.jupiter.api.Test;
import stupidcoder.util.ConsoleUtil;

public class TestPrint {

    @Test
    public void testPrintColored() {
        ConsoleUtil.printBlue("blue");
        ConsoleUtil.printGreen("green");
        ConsoleUtil.printRed("red");
        ConsoleUtil.printPurple("purple");
        ConsoleUtil.printYellow("yellow");
        ConsoleUtil.printCyan("cyan");
        ConsoleUtil.printHighlightWhite("bgWhite");
        ConsoleUtil.printHighlightPurple("bgPurple");
        ConsoleUtil.printHighlightRed("bgRed");
        ConsoleUtil.printHighlightYellow("bgYellow");
        ConsoleUtil.printHighlightBlue("bgBlue");
        ConsoleUtil.printHighlightCyan("bgCyan");
    }
}
