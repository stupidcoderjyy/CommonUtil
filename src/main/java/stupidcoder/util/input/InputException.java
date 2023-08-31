package stupidcoder.util.input;

public class InputException extends RuntimeException{
    private final IInput input;

    public InputException(IInput input) {
        this.input = input;
    }

    public InputException(IInput input, String message) {
        super(message);
        this.input = input;
    }

    public InputException(IInput input, Throwable cause) {
        super(cause);
        this.input = input;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        if (input instanceof AbstractDebugInput debugInput) {
            System.err.println("======DEBUG INFO======");
            System.err.println("\ntype: " + debugInput.type() + "\n");
            System.err.println("\npos:\n");
            debugInput.printPos();
            System.err.println("\ndata:\n");
            debugInput.printData();
            System.err.println("\n======================\n");
        }
    }

    public void printStackTraceAndClose() {
        printStackTrace();
        if (input != null) {
            input.close();
        }
    }
}
