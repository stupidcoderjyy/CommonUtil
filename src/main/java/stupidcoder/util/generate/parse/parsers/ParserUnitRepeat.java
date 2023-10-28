package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.outunit.RepeatOut;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserUnitRepeat implements Parser {

    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        RepeatOut rpt = new RepeatOut();
        rpt.unit = InternalParsers.UNIT.parse(g, input, rpt);
        while (true) {
            if (approachNext(input) == '%') {
                InternalParsers.ARG_COMMON_OUTPUT_FIELD.parse(g, input, rpt);
            } else {
                InternalParsers.ARG_COMMON_OUTPUT_CONFIG.parse(g, input, rpt);
            }
            if (checkArgInterval(input)) {
                return rpt;
            }
        }
    }
}
