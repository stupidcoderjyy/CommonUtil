package stupidcoder.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final int STRING_T = 0, BOOL_T = 1, INT_T = 2, PATH_T = 3;
    private static final int ID_BITS = 16;
    public static final int GLOBAL_OUTPUT_DIR;
    public static final int GLOBAL_TEMP_OUT;
    public static final int GLOBAL_SRC_DIR;

    private static final Config INSTANCE;
    private final Map<Integer, Object> configs = new HashMap<>();
    private final int[] maxId = new int[4];

    static {
        INSTANCE = new Config();
        GLOBAL_OUTPUT_DIR = register(PATH_T, "build/out");
        GLOBAL_TEMP_OUT = register(PATH_T, "build/out");
        GLOBAL_SRC_DIR = register(STRING_T, "");
    }

    public static int register(int type, Object val) {
        return INSTANCE.register0(type, val);
    }

    public static void set(int flag, Object val) {
        INSTANCE.set0(flag, val);
    }

    public static int getInt(int flag) {
        return (int) INSTANCE.get0(flag, INT_T);
    }

    public static String getString(int flag) {
        return (String) INSTANCE.get0(flag, STRING_T);
    }

    public static boolean getBool(int flag) {
        return (boolean) INSTANCE.get0(flag, BOOL_T);
    }

    private int createId(int type) {
        if (type < 0 || type >= maxId.length) {
            throw new IllegalArgumentException("invalid data type:" + type + " (max = " + maxId.length + ")");
        }
        return maxId[type]++ | (type << ID_BITS);
    }

    private Object get0(int flag, int type) {
        if (flag >> ID_BITS != type) {
            throw new IllegalArgumentException("unmatched type");
        }
        Object res = configs.get(flag);
        if (res == null) {
            throw new IllegalArgumentException("flag not found: 0x" + Integer.toHexString(flag));
        }
        return res;
    }

    private void set0(int flag, Object val) {
        if (!configs.containsKey(flag)) {
            throw new IllegalArgumentException("flag not found: 0x" + Integer.toHexString(flag));
        }
        int type = flag >> ID_BITS;
        checkType(type, val);
        if (type == PATH_T) {
            new File((String) val).mkdirs();
        }
        configs.put(flag, val);
    }

    private int register0(int type, Object val) {
        int flag = createId(type);
        checkType(type, val);
        if (type == PATH_T) {
            new File((String) val).mkdirs();
        }
        configs.put(flag, val);
        return flag;
    }

    private void checkType(int type, Object val) {
        boolean match;
        switch (type) {
            case STRING_T, PATH_T -> match = val instanceof String;
            case BOOL_T -> match = val instanceof Boolean;
            case INT_T -> match = val instanceof Integer;
            default -> match = false;
        }
        if (!match) {
            throw new IllegalArgumentException("unmatched data type");
        }
    }

    public static String outputPath(String child) {
        return INSTANCE.configs.get(GLOBAL_OUTPUT_DIR) + "/" + child;
    }

    public static String tempOutPath(String child) {
        return INSTANCE.configs.get(GLOBAL_TEMP_OUT) + "/" + child;
    }

    public static String resourcePath(String child) {
        return INSTANCE.configs.get(GLOBAL_SRC_DIR) + "/" + child;
    }
}
