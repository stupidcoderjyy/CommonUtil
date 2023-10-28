package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.outunit.ComplexOut;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserComplexArg0Line implements Parser {

    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        ComplexOut cpx = (ComplexOut) raw;
        cpx.newLine();
        while (true) {
            cpx.append(InternalParsers.UNIT.parse(g, input, null));
            switch (approachNext(input)) {
                case '+' -> input.read();
                case ',', '}' -> {
                    return cpx;
                }
                default -> throw input.errorAtForward("unexpected symbol");
            }
        }
    }
}
