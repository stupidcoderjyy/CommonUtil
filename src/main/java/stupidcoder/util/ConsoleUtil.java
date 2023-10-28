package stupidcoder.util;

public class ConsoleUtil {
    public static final int BLACK = 30, RED = 31, GREEN = 32, YELLOW = 33, BLUE = 34, PURPLE = 35, CYAN = 36;
    public static final int BG_BLACK = 40, BG_RED = 41,  BG_GREEN = 42,
            BG_YELLOW = 43,  BG_BLUE = 44,  BG_PURPLE = 45,  BG_CYAN = 46;
    
    public static void begin(int style, int fg, int bg) {
        beginPrefix();
        appendStyle(style);
        appendStyle(fg);
        System.out.print(bg);
        endPrefix();
    }

    public static void begin(int style, int fg) {
        beginPrefix();
        appendStyle(style);
        System.out.print(fg);
        endPrefix();
    }
    
    public static void begin(int style) {
        beginPrefix();
        System.out.print(style);
        endPrefix();
    }

    public static void end() {
        System.out.print("\33[0m");
    }

    public static void printRed(Object info) {
        printNormal(RED, info);
    }

    public static void printYellow(Object info) {
        printNormal(YELLOW, info);
    }

    public static void printPurple(Object info) {
        printNormal(PURPLE, info);
    }

    public static void printGreen(Object info) {
        printNormal(GREEN, info);
    }

    public static void printBlue(Object info) {
        printNormal(BLUE, info);
    }

    public static void printCyan(Object info) {
        printNormal(CYAN, info);
    }

    public static void printHighlightWhite(Object info) {
        begin(7);
        System.out.print(info);
        end();
    }

    public static void printHighlightPurple(Object info) {
        printComplex(0, BLACK, BG_PURPLE, info);
    }

    public static void printHighlightRed(Object info) {
        printComplex(0, BLACK, BG_RED, info);
    }

    public static void printHighlightYellow(Object info) {
        printComplex(0, BLACK, BG_YELLOW, info);
    }

    public static void printHighlightBlue(Object info) {
        printComplex(0, BLACK, BG_BLUE, info);
    }

    public static void printHighlightGreen(Object info) {
        printComplex(0, BLACK, BG_GREEN, info);
    }

    public static void printHighlightCyan(Object info) {
        printComplex(0, BLACK, BG_CYAN, info);
    }

    private static void beginPrefix() {
        System.out.print("\033[");
    }

    private static void appendStyle(int style) {
        System.out.print(style + ";");
    }

    private static void endPrefix() {
        System.out.print("m");
    }

    public static void printNormal(int fg, Object info) {
        printStyled(0, fg, info);
    }
    
    public static void printStyled(int style, int fg, Object info) {
        begin(style, fg);
        System.out.print(info);
        end();
    }

    public static void printComplex(int style, int fg, int bg, Object info) {
        begin(style, fg, bg);
        System.out.print(info);
        end();
    }
}
