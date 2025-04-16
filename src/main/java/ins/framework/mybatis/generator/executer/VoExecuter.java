//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.executer;

import ins.framework.mybatis.generator.GenConfig;
import ins.framework.mybatis.generator.GenFileInfo;
import ins.framework.mybatis.generator.schema.Column;
import ins.framework.mybatis.generator.schema.Table;
import ins.framework.mybatis.generator.util.GeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class VoExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(VoExecuter.class);
    private GenFileInfo voInfo;

    public VoExecuter(GenConfig genConfig, GenFileInfo voInfo) {
        super(genConfig);
        this.voInfo = voInfo;
    }

    public void build(Table table) throws IOException {
        if (table.getPrimaryKeys().size() == 1) {
            this.buildVo(table);
        } else {
            this.buildUnionKeyVo(table);
            this.buildUnionVo(table);
        }

    }

    private void buildClassHeader(BufferedWriter bw, Table table, List<String> types, String className, String info) throws IOException {
        bw.write("package " + this.voInfo.getPackageName() + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import java.io.Serializable;");
        bw.newLine();
        if (GeneratorUtils.isDate(types)) {
            bw.write("import java.util.Date;");
            bw.newLine();
        }

        if (GeneratorUtils.isDecimal(types)) {
            bw.write("import java.math.BigDecimal;");
            bw.newLine();
        }

        bw.newLine();
        bw.write("import lombok.Data;");
        bw.newLine();
        String classComment = "对应表名：" + this.getName(table.getName());
        if (table.getComment() != null && table.getComment().trim().length() > 0) {
            classComment = classComment + ",备注：" + table.getComment().trim();
        }

        buildClassComment(bw, String.format("通过ins-framework-mybatis工具自动生成，表%s的%s<br/>", this.getName(table.getName()), info), classComment);
        bw.newLine();
        bw.write("@Data");
        bw.newLine();
        bw.write("public class " + className + " implements Serializable {");
        bw.newLine();
        bw.write("\tprivate static final long serialVersionUID = 1L;");
        bw.newLine();
    }

    private void buildFieldGetterSetter(BufferedWriter bw, Column column) throws IOException {
        String field = getInstanceName(column.getName());
        bw.write("\t/** 对应字段：" + this.getName(column.getName()));
        String comment = column.getComment();
        if (comment != null && comment.trim().length() > 0) {
            bw.write(",备注：" + comment.trim());
        }

        bw.write(" */");
        bw.newLine();
        bw.write("\tprivate " + processType(column) + " " + field + ";");
        bw.newLine();
    }

    public void buildVo(Table table) throws IOException {
        List<String> types = GeneratorUtils.getTableColumnTypes(table);
        File beanFile = new File(this.voInfo.getPath(), this.voInfo.getName() + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        Throwable var5 = null;

        try {
            this.buildClassHeader(bw, table, types, this.voInfo.getName(), "VO对象");
            List<Column> columns = table.getColumns();
            int size = columns.size();
            int i = 0;

            while (true) {
                if (i >= size) {
                    bw.newLine();
                    bw.write("}");
                    bw.newLine();
                    bw.flush();
                    break;
                }

                Column column = (Column) columns.get(i);
                this.buildFieldGetterSetter(bw, column);
                ++i;
            }
        } catch (Throwable var17) {
            var5 = var17;
            throw var17;
        } finally {
            if (bw != null) {
                if (var5 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var16) {
                        var5.addSuppressed(var16);
                    }
                } else {
                    bw.close();
                }
            }

        }

        log.info("Generate VO file " + beanFile.getAbsolutePath());
    }

    public void buildUnionKeyVo(Table table) throws IOException {
        List<String> types = GeneratorUtils.getTablePrimaryKeysTypes(table);
        String keyVoName = this.voInfo.getName().substring(0, this.voInfo.getName().length() - 2) + "KeyVo";
        File beanFile = new File(this.voInfo.getPath(), keyVoName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        Throwable var6 = null;

        try {
            this.buildClassHeader(bw, table, types, keyVoName, "复合主键VO主键对象");
            List<Column> columns = GeneratorUtils.getTablePrimaryKeysColumns(table);
            int size = columns.size();
            int i = 0;

            while (true) {
                if (i >= size) {
                    bw.newLine();
                    bw.write("}");
                    bw.newLine();
                    bw.flush();
                    break;
                }

                Column column = (Column) columns.get(i);
                this.buildFieldGetterSetter(bw, column);
                ++i;
            }
        } catch (Throwable var18) {
            var6 = var18;
            throw var18;
        } finally {
            if (bw != null) {
                if (var6 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var17) {
                        var6.addSuppressed(var17);
                    }
                } else {
                    bw.close();
                }
            }

        }

        log.info("Generate VO Key file " + beanFile.getAbsolutePath());
    }

    public void buildUnionVo(Table table) throws IOException {
        List<String> types = GeneratorUtils.getTableColumnTypes(table);
        String keyVoName = this.voInfo.getName().substring(0, this.voInfo.getName().length() - 2) + "KeyVo";
        List<String> primaryKeysCloumnNames = GeneratorUtils.getTablePrimaryKeysCloumnNames(table);
        File beanFile = new File(this.voInfo.getPath(), this.voInfo.getName() + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        Throwable var7 = null;

        try {
            this.buildClassHeader(bw, table, types, this.voInfo.getName(), "复合主键VO对象");
            bw.write("\t/** 对应复合主键 */");
            bw.newLine();
            bw.write("\tprivate " + keyVoName + " " + getInstanceName(keyVoName) + ";");
            bw.newLine();
            List<Column> columns = table.getColumns();
            int size = columns.size();
            int i = 0;

            while (true) {
                if (i >= size) {
                    bw.newLine();
                    bw.write("}");
                    bw.newLine();
                    bw.flush();
                    break;
                }

                Column column = (Column) columns.get(i);
                if (!primaryKeysCloumnNames.contains(column.getName().toLowerCase())) {
                    this.buildFieldGetterSetter(bw, column);
                }

                ++i;
            }
        } catch (Throwable var19) {
            var7 = var19;
            throw var19;
        } finally {
            if (bw != null) {
                if (var7 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var18) {
                        var7.addSuppressed(var18);
                    }
                } else {
                    bw.close();
                }
            }

        }

        log.info("Generate VO file " + beanFile.getAbsolutePath());
    }
}
