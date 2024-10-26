package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserArgConfig implements Parser {
    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        while (input.available()) {
            int b = input.read();
            switch (b) {
                case 'L', 'l' -> {
                    String v = readIntString(input);
                    raw.setLineBreaks(v.isEmpty() ? 1 : Integer.parseInt(v));
                }
                case 'R', 'r' -> {
                    String v = readIntString(input);
                    raw.setRepeat(v.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(v));
                }
                case 'I', 'i' -> {
                    String v = readIntString(input);
                    raw.setIndents(v.isEmpty() ? 1 :Integer.parseInt(v));
                }
                case '}', ',' -> {
                    input.retract();
                    return raw;
                }
                case ' ', '\r', '\n' -> {}
                default -> {
                    input.retract();
                    throw input.errorAtForward("invalid output unit config flag");
                }
            }
        }
        return raw;
    }
}
