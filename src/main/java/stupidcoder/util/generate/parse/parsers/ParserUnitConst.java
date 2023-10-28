package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.outunit.ConstOut;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserUnitConst implements Parser {

    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        ConstOut cst = new ConstOut(readStringArg(input));
        if (checkArgInterval(input)) {
            return cst;
        }
        InternalParsers.ARG_COMMON_OUTPUT_CONFIG.parse(g, input, cst);
        return cst;
    }
}
