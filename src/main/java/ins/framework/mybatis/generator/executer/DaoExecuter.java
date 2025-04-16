//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.executer;

import ins.framework.mybatis.generator.GenConfig;
import ins.framework.mybatis.generator.GenFileInfo;
import ins.framework.mybatis.generator.schema.Column;
import ins.framework.mybatis.generator.schema.PrimaryKey;
import ins.framework.mybatis.generator.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class DaoExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(DaoExecuter.class);
    private GenFileInfo daoInfo;
    private GenFileInfo poInfo;

    public DaoExecuter(GenConfig genConfig, GenFileInfo daoInfo, GenFileInfo poInfo) {
        super(genConfig);
        this.daoInfo = daoInfo;
        this.poInfo = poInfo;
    }

    public void build(Table table) throws IOException {
        List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
        if (primaryKeys.size() != 1) {
            throw new IllegalArgumentException("目前只支持单一主键的表");
        } else {
            PrimaryKey primaryKey = (PrimaryKey) primaryKeys.get(0);
            Column idColumn = null;
            List<Column> columns = table.getColumns();
            int size = columns.size();

            for (int i = 0; i < size; ++i) {
                Column column = (Column) columns.get(i);
                if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
                    idColumn = column;
                    break;
                }
            }

            if (idColumn == null) {
                throw new IllegalArgumentException("找不到主键名对应的字段");
            } else {
                File mapperFile = new File(this.daoInfo.getPath(), this.daoInfo.getName() + ".java");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
                Throwable var9 = null;

                try {
                    bw.write("package " + this.daoInfo.getPackageName() + ";");
                    bw.newLine();
                    bw.newLine();
                    bw.write("import org.apache.ibatis.annotations.Mapper;");
                    bw.newLine();
                    bw.newLine();
                    bw.write("import " + this.poInfo.getPackageName() + "." + this.poInfo.getName() + ";");
                    bw.newLine();
                    bw.write("import ins.framework.mybatis.MybatisBaseDao;");
                    bw.newLine();
                    buildClassComment(bw, "", "表" + this.getName(table.getName()) + "对应的基于MyBatis实现的Dao接口<br/>\r\n * 在其中添加自定义方法");
                    bw.newLine();
                    bw.write("@Mapper");
                    bw.newLine();
                    bw.write("public interface " + this.daoInfo.getName() + " extends MybatisBaseDao<" + this.poInfo.getName() + ", " + processType(idColumn) + "> {");
                    bw.newLine();
                    bw.newLine();
                    bw.write("}");
                    bw.flush();
                } catch (Throwable var18) {
                    var9 = var18;
                    throw var18;
                } finally {
                    if (bw != null) {
                        if (var9 != null) {
                            try {
                                bw.close();
                            } catch (Throwable var17) {
                                var9.addSuppressed(var17);
                            }
                        } else {
                            bw.close();
                        }
                    }

                }

                log.info("Generate Dao file " + mapperFile.getAbsolutePath());
            }
        }
    }
}
