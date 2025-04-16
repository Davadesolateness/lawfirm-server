//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

public class WordFileUtils {
    public static final String WORD_ROOT_FILE = "wordroot.properties";
    public static final String WORD_ROOT_TABLE_FILE = "wordroot-table.properties";
    private static final Logger log = LoggerFactory.getLogger(WordFileUtils.class);
    public static boolean hasShowHelp = false;
    private static Map<String, Word> wordMap = new HashMap();

    private WordFileUtils() {
    }

    public static synchronized void initWordMap() {
        if (!hasShowHelp) {
            log.info("**************************************************************");
            log.info("**开始加载词根表，词根优先级如下： ");
            log.info("**1：以下划线分隔的命名");
            log.info("**2：自带标准词根表(wordroot.properties)");
            log.info("**3：自带标准词根表业务表(wordroot-table.properties)");
            log.info("**4：自定义词根表文件(wordfile9.properties->wordfile9.properties)");
            log.info("**5：数据库中的大小写");
            log.info("**6：全部改成小写");
            log.info("**************************************************************");
            hasShowHelp = true;
        }

        try {
            for (int i = 9; i >= 1; --i) {
                init("wordfile" + i + ".properties");
            }

            init("wordroot-table.properties");
            init("wordroot.properties");
        } catch (IOException var1) {
            IOException e = var1;
            log.warn("{}", e);
        }

    }

    private static String getClassNameWithoutPackage(Class<?> cl) {
        String className = cl.getName();
        int pos = className.lastIndexOf(46) + 1;
        if (pos == -1) {
            pos = 0;
        }

        return className.substring(pos);
    }

    public static String getRealPathName(Class<?> cl) {
        URL url = cl.getResource(getClassNameWithoutPackage(cl) + ".class");
        return url != null ? url.getPath() : null;
    }

    private static void init(String fileName) throws IOException {
        InputStream is = WordFileUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (is != null) {
            try {
                log.info("Load wordfile " + fileName);
                PropertyResourceBundle bundle = new PropertyResourceBundle(is);
                Enumeration<String> ration = bundle.getKeys();

                while (ration.hasMoreElements()) {
                    String objKey = (String) ration.nextElement();
                    if (objKey != null) {
                        String name = objKey;
                        String key = name.toLowerCase();
                        String value = bundle.getString(name);
                        if (StringUtils.isNotEmpty(value)) {
                            value = new String(value.getBytes("UTF-8"));
                            Word word = new Word(name, value);
                            wordMap.put(key, word);
                        }
                    }
                }
            } finally {
                if (is != null) {
                    is.close();
                }

            }
        }

    }

    public static String getBeautyObjectName(String name) {
        if (name != null && !name.isEmpty()) {
            String key = name.toLowerCase();
            key = key.replace("_", "");
            String beautyName = null;
            if (name.contains("_")) {
                StringBuilder sb = new StringBuilder();
                String[] fields = name.split("_");
                sb.append(fields[0].toLowerCase());

                for (int i = 1; i < fields.length; ++i) {
                    String temp = fields[i];
                    sb.append(temp.substring(0, 1).toUpperCase());
                    sb.append(temp.substring(1).toLowerCase());
                }

                beautyName = sb.toString();
            } else if (wordMap.containsKey(key)) {
                beautyName = ((Word) wordMap.get(key)).getName();
                beautyName = upperCaseFirstChar(beautyName);
            } else if (!name.toLowerCase().equals(name) && !name.toUpperCase().equals(name)) {
                beautyName = name;
            } else {
                beautyName = name.toLowerCase();
            }

            beautyName = upperCaseFirstChar(beautyName);
            return beautyName;
        } else {
            throw new IllegalArgumentException("name must have value.");
        }
    }

    public static String getBeautyInstanceName(String name) {
        String beautyName = getBeautyObjectName(name);
        if (beautyName.length() <= 1 || !Character.isUpperCase(beautyName.charAt(1))) {
            beautyName = lowerCaseFirstChar(beautyName);
        }

        return beautyName;
    }

    public static String getBeautyDesc(String name) {
        String beautyDesc = "";
        String key = name.toLowerCase();
        key = key.replace("_", "");
        if (wordMap.containsKey(key)) {
            beautyDesc = ((Word) wordMap.get(key)).getDesc();
        } else {
            beautyDesc = name;
        }

        return beautyDesc;
    }

    public static String lowerCaseFirstChar(String iString) {
        String newString = iString.substring(0, 1).toLowerCase() + iString.substring(1);
        return newString;
    }

    public static String upperCaseFirstChar(String iString) {
        String newString = iString.substring(0, 1).toUpperCase() + iString.substring(1);
        return newString;
    }

    public static void printString(String value) {
        try {
            String[] codes = new String[]{"GBK", "ISO8859-1", "UTF-8"};

            int i;
            for (i = 0; i < codes.length; ++i) {
                for (int j = 0; j < codes.length; ++j) {
                    if (i != j) {
                        log.info(codes[i] + "--" + codes[j] + "====" + new String(value.getBytes(codes[i]), codes[j]));
                    }
                }
            }

            for (i = 0; i < codes.length; ++i) {
                log.info(codes[i] + "====" + new String(value.getBytes(codes[i])));
            }

            for (i = 0; i < codes.length; ++i) {
                log.info(codes[i] + "==----==" + new String(value.getBytes(), codes[i]));
            }
        } catch (Exception var4) {
            Exception e = var4;
            log.warn("{}", e);
        }

    }
}
