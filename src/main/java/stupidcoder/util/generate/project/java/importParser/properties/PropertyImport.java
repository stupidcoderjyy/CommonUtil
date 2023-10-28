package stupidcoder.util.generate.project.java.importParser.properties;

import stupidcoder.util.Config;
import stupidcoder.util.compile.Production;
import stupidcoder.util.compile.property.IProperty;
import stupidcoder.util.compile.property.PropertyTerminal;
import stupidcoder.util.generate.project.java.JClassGen;
import stupidcoder.util.generate.project.java.JProjectBuilder;
import stupidcoder.util.generate.project.java.importParser.ImportParsingException;
import stupidcoder.util.generate.project.java.importParser.tokens.TokenId;

public class PropertyImport implements IProperty {
    private static final String internalPrefix = Config.getString(JProjectBuilder.FRIEND_PKG_PREFIX);
    private final JClassGen clazz;

    public PropertyImport(JClassGen clazz) {
        this.clazz = clazz;
    }

    //import → $import id . paths ;
    private void reduce0(
            PropertyTerminal p1,
            PropertyPaths p3) {
        TokenId t1 = p1.getToken();
        String prefix = t1.lexeme;
        if (prefix.equals(internalPrefix)) {
            try {
                if (p3.isPkg) {
                    clazz.addPkgImport(p3.path.toString());
                } else {
                    clazz.addInternalClazzImport(p3.postfix);
                }
            } catch (Exception e) {
                throw new ImportParsingException(clazz.toString(), e);
            }
        } else {
            clazz.addExternalImport(prefix + "." + p3.path.toString());
        }
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
                (PropertyTerminal)properties[1],
                (PropertyPaths)properties[3]
        );
    }
}
