package stupidcoder.util.generate.project.java.importParser.properties;

import stupidcoder.util.compile.Production;
import stupidcoder.util.compile.property.IProperty;

public class PropertyImportList implements IProperty {
    //importList → import
    //importList → importList import
    @Override
    public void onReduced(Production p, IProperty... properties) {
    }
}
