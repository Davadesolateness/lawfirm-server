//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.executer;

import ins.framework.mybatis.generator.GenConfig;
import ins.framework.mybatis.generator.schema.Column;
import ins.framework.mybatis.generator.schema.Table;
import ins.framework.mybatis.generator.util.TypeUtils;
import ins.framework.mybatis.generator.util.WordFileUtils;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class BaseExecuter {
    protected static final String JAVA_SUFFIX = ".java";
    protected static final String XML_SUFFIX = ".xml";
    protected GenConfig genConfig;

    public BaseExecuter(GenConfig genConfig) {
        this.genConfig = genConfig;
    }

    protected static void buildClassComment(BufferedWriter bw, String prefix, String text) throws IOException {
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" *");
        bw.newLine();
        if (prefix != null && prefix.trim().length() > 0) {
            bw.write(" * " + prefix);
            bw.newLine();
        }

        bw.write(" * " + text);
        bw.newLine();
        bw.write(" *");
        bw.newLine();
        bw.write(" */");
    }

    protected static String processType(Column column) {
        String type = column.getType();
        return TypeUtils.processType(type);
    }

    protected static String getInstanceName(String field) {
        return WordFileUtils.getBeautyInstanceName(field);
    }

    protected static String getObjectName(String field) {
        return WordFileUtils.getBeautyObjectName(field);
    }

    public abstract void build(Table var1) throws IOException;

    public String getName(String name) {
        String result = name;
        if (this.genConfig.isIgnoreUnderline()) {
            result = name.replace("_", "");
        }

        return result;
    }
}
