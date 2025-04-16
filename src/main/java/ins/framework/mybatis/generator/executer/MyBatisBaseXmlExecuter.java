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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisBaseXmlExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(MyBatisBaseXmlExecuter.class);
    private GenFileInfo baseMapperXmlInfo;
    private GenFileInfo daoInfo;
    private GenFileInfo poInfo;

    public MyBatisBaseXmlExecuter(GenConfig genConfig, GenFileInfo baseMapperXmlInfo, GenFileInfo daoInfo, GenFileInfo poInfo) {
        super(genConfig);
        this.baseMapperXmlInfo = baseMapperXmlInfo;
        this.daoInfo = daoInfo;
        this.poInfo = poInfo;
    }

    public void build(Table table) throws IOException {
        List<Column> columns = table.getColumns();
        File mapperXmlFile = new File(this.baseMapperXmlInfo.getPath(), this.baseMapperXmlInfo.getName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        Throwable var5 = null;

        try {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- =======通过ins-framework-mybatis工具自动生成，请勿手工修改！======= -->");
            bw.newLine();
            bw.write("<!-- =======本配置文件中定义的节点可在自定义配置文件中直接使用！       ======= -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<mapper namespace=\"" + this.daoInfo.getPackageName() + "." + this.daoInfo.getName() + "\">");
            if (this.genConfig.isDefaultCache()) {
                bw.newLine();
                bw.write("\t<!-- 默认开启二级缓存,使用Least Recently Used（LRU，最近最少使用的）算法来收回 -->");
                bw.newLine();
                bw.write("\t<cache/>");
            }

            bw.newLine();
            List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
            PrimaryKey primaryKey = (PrimaryKey) primaryKeys.get(0);
            this.buildBaseDaoBaseResultMap(bw, table, primaryKey, columns);
            this.buildBaseDaoBaseColumnList(bw, table, primaryKey, columns);
            this.buildBaseDaoBaseSelectByEntityWhere(bw, table, primaryKey, columns);
            this.buildBaseDaoBaseSelectByEntity(bw, table, primaryKey, columns);
            this.buildBaseDaoSelectByPrimaryKey(bw, table, primaryKey, columns);
            this.buildBaseDaoSelectBatchByPrimaryKeys(bw, table, primaryKey, columns);
            this.buildBaseDaoSelectPage(bw, table, primaryKey, columns);
            this.buildBaseDaoDeleteByPrimaryKey(bw, table, primaryKey, columns);
            this.buildBaseDaoDeleteBatchByPrimaryKeys(bw, table, primaryKey, columns);
            this.buildBaseDaoInsert(bw, table, primaryKey, columns);
            this.buildBaseDaoInsertSelective(bw, table, primaryKey, columns);
            this.buildBaseDaoUpdateSelectiveByPrimaryKey(bw, table, primaryKey, columns);
            this.buildBaseDaoUpdateByPrimaryKey(bw, table, primaryKey, columns);
            bw.write("</mapper>");
            bw.flush();
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (bw != null) {
                if (var5 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    bw.close();
                }
            }

        }

        log.info("Generate BaseXml file " + mapperXmlFile.getAbsolutePath());
    }

    protected void buildBaseDaoBaseResultMap(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 通用查询结果对象-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"BaseResultMap\" type=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
                bw.write("\t\t <id ");
            } else {
                bw.write("\t\t <result ");
            }

            bw.write("column=\"" + this.getName(column.getName()) + "\" ");
            bw.write("property=\"" + getInstanceName(column.getName()) + "\"/> ");
            bw.newLine();
        }

        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoBaseColumnList(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 通用查询结果列-->");
        bw.newLine();
        bw.write(this.getName("\t<sql id=\"Base_Column_List\">"));
        bw.newLine();
        bw.write("\t\t");

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            bw.write(" " + this.getName(column.getName()));
            if (column.getName().contains("_")) {
                bw.write(" AS " + getInstanceName(column.getName()));
            }

            if (i != size - 1) {
                bw.write(",");
            }

            if (i % 5 == 4) {
                bw.newLine();
                bw.write("\t\t");
            }
        }

        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoBaseSelectByEntityWhere(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 按对象查询记录的WHERE部分 -->");
        bw.newLine();
        bw.write(this.getName("\t<sql id=\"Base_Select_By_Entity_Where\">"));
        bw.newLine();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                bw.write("\t\t\tand " + this.getName(column.getName()) + " = '0'");
                bw.newLine();
            } else {
                bw.write("\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
                bw.newLine();
                bw.write("\t\t\tand " + this.getName(column.getName()) + " = #{" + getInstanceName(column.getName()) + "}");
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
        }

        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoBaseSelectByEntity(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按对象查询记录的SQL部分 -->");
        bw.newLine();
        bw.write(this.getName("\t<sql id=\"Base_Select_By_Entity\">"));
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write(this.getName("\t\t\t<include refid=\"Base_Column_List\" />"));
        bw.newLine();
        bw.write("\t\tfrom " + this.getName(table.getName()));
        bw.newLine();
        bw.write("\t\t<where>");
        bw.newLine();
        bw.write(this.getName("\t\t\t<include refid=\"Base_Select_By_Entity_Where\" />"));
        bw.newLine();
        bw.write("\t\t</where>");
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoSelectByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键查询一条记录 -->");
        bw.newLine();
        bw.write("\t<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write(this.getName("\t\t\t<include refid=\"Base_Column_List\" />"));
        bw.newLine();
        bw.write("\t\tfrom " + this.getName(table.getName()));
        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " = #{param1}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoSelectBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键List查询多条记录 -->");
        bw.newLine();
        bw.write("\t<select id=\"selectBatchByPrimaryKeys\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write(this.getName("\t\t\t<include refid=\"Base_Column_List\" />"));
        bw.newLine();
        bw.write("\t\tfrom " + this.getName(table.getName()));
        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " in");
        bw.newLine();
        bw.write("\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t#{item}");
        bw.newLine();
        bw.write("\t\t</foreach>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoSelectOne(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按对象查询一条记录 -->");
        bw.newLine();
        bw.write("\t<select id=\"selectOne\" resultMap=\"BaseResultMap\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write(this.getName("\t\t<include refid=\"Base_Select_By_Entity\" />"));
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoSelectPage(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按对象查询一页记录（多条记录） -->");
        bw.newLine();
        bw.write("\t<select id=\"selectPage\" resultMap=\"BaseResultMap\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write(this.getName("\t\t<include refid=\"Base_Select_By_Entity\" />"));
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }

    protected boolean containsDeleteFlagField(Table table) {
        List<Column> columns = table.getColumns();
        int size = columns.size();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                return true;
            }
        }

        return false;
    }

    protected void buildBaseDaoDeleteByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键删除一条记录 -->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"map\">");
        bw.newLine();
        if (this.genConfig.isDeletedFlagMode() && this.containsDeleteFlagField(table)) {
            bw.write("\t\tupdate " + this.getName(table.getName()));
            bw.newLine();
            bw.write("\t\t<set>");
            bw.newLine();
            int size = columns.size();

            for (int i = 0; i < size; ++i) {
                Column column = (Column) columns.get(i);
                if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("= '1',");
                    bw.newLine();
                }

                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.getName(column.getName()));
                    bw.write("+1,");
                    bw.newLine();
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.genConfig.getOperateTimeForHisFunc());
                    bw.write(",");
                    bw.newLine();
                }
            }

            bw.write("\t\t</set>");
        } else {
            bw.write("\t\tdelete from " + this.getName(table.getName()));
        }

        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " = #{param1}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoDeleteBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键List删除多条记录 -->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteBatchByPrimaryKeys\" parameterType=\"map\">");
        bw.newLine();
        if (this.genConfig.isDeletedFlagMode() && this.containsDeleteFlagField(table)) {
            bw.write("\t\tupdate " + this.getName(table.getName()));
            bw.newLine();
            bw.write("\t\t<set>");
            bw.newLine();
            int size = columns.size();

            for (int i = 0; i < size; ++i) {
                Column column = (Column) columns.get(i);
                if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("= '1',");
                    bw.newLine();
                }

                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.getName(column.getName()));
                    bw.write("+1,");
                    bw.newLine();
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.genConfig.getOperateTimeForHisFunc());
                    bw.write(",");
                    bw.newLine();
                }
            }

            bw.write("\t\t</set>");
        } else {
            bw.write("\t\tdelete from " + this.getName(table.getName()));
        }

        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " in ");
        bw.newLine();
        bw.write("\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t#{item}");
        bw.newLine();
        bw.write("\t\t</foreach>");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoInsert(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 完整插入一条记录-->");
        bw.newLine();
        bw.write("\t<insert id=\"insert\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tinsert into " + this.getName(table.getName()) + " (");
        StringBuilder sb = new StringBuilder();

        int i;
        Column column;
        for (i = 0; i < size; ++i) {
            column = (Column) columns.get(i);
            if (!this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(this.getName(column.getName())))) {
                sb.append(this.getName(column.getName()));
                if (i != size - 1) {
                    sb.append(", ");
                }

                if (i % 5 == 4) {
                    sb.append("\r\n\t\t\t");
                }
            }
        }

        if (sb.toString().endsWith(", \r\n\t\t\t")) {
            sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
        }

        bw.write(sb.toString());
        bw.write(")");
        bw.newLine();
        bw.write("\t\tvalues(");
        sb.setLength(0);

        for (i = 0; i < size; ++i) {
            column = (Column) columns.get(i);
            if (!this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    sb.append(this.genConfig.getOperateTimeForHisFunc());
                } else if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    sb.append("1");
                } else if ("deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                    sb.append("'0'");
                } else {
                    sb.append("#{" + getInstanceName(column.getName()));
                    sb.append("}");
                }

                if (i != size - 1) {
                    sb.append(", ");
                }

                if (i % 5 == 4) {
                    sb.append("\r\n\t\t\t");
                }
            }
        }

        if (sb.toString().endsWith(", \r\n\t\t\t")) {
            sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
        }

        bw.write(sb.toString());
        bw.write(")");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoInsertSelective(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 插入一条记录(为空的字段不操作) -->");
        bw.newLine();
        bw.write("\t<insert id=\"insertSelective\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tinsert into " + this.getName(table.getName()) + "");
        bw.newLine();
        bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        int i;
        Column column;
        for (i = 0; i < size; ++i) {
            column = (Column) columns.get(i);
            if (!this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t" + this.getName(column.getName()) + ",");
                    bw.newLine();
                } else if (this.genConfig.isDeletedFlagMode() && "deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t" + this.getName(column.getName()) + ",");
                    bw.newLine();
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write("\t\t\t" + this.getName(column.getName()) + ",");
                    bw.newLine();
                } else {
                    bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
                    bw.newLine();
                    bw.write("\t\t\t\t" + this.getName(column.getName()) + ",");
                    bw.newLine();
                    bw.write("\t\t\t</if>");
                    bw.newLine();
                }
            }
        }

        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t\tvalues <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        for (i = 0; i < size; ++i) {
            column = (Column) columns.get(i);
            if (!this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t1,");
                    bw.newLine();
                } else if (this.genConfig.isDeletedFlagMode() && "deletedFlag".equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t'0',");
                    bw.newLine();
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write("\t\t\t");
                    bw.write(this.genConfig.getOperateTimeForHisFunc());
                    bw.write(",");
                    bw.newLine();
                } else {
                    bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
                    bw.newLine();
                    bw.write("\t\t\t\t#{" + getInstanceName(column.getName()) + "},");
                    bw.newLine();
                    bw.write("\t\t\t</if>");
                    bw.newLine();
                }
            }
        }

        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoUpdateSelectiveByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 更新一条记录(为空的字段不操作) -->");
        bw.newLine();
        bw.write("\t<update id=\"updateSelectiveByPrimaryKey\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tupdate " + this.getName(table.getName()));
        bw.newLine();
        bw.write("\t\t<set>");
        bw.newLine();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            if (!column.getName().equalsIgnoreCase(primaryKey.getColumnName()) && !this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.getName(column.getName()));
                    bw.write("+1,");
                    bw.newLine();
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write("\t\t\t");
                    bw.write(this.getName(column.getName()));
                    bw.write("=");
                    bw.write(this.genConfig.getOperateTimeForHisFunc());
                    bw.write(",");
                    bw.newLine();
                } else {
                    bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
                    bw.newLine();
                    bw.write("\t\t\t\t" + this.getName(column.getName()) + "=#{" + getInstanceName(column.getName()) + "},");
                    bw.newLine();
                    bw.write("\t\t\t</if>");
                    bw.newLine();
                }
            }
        }

        bw.write("\t\t</set>");
        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " = #{" + getInstanceName(primaryKey.getColumnName()) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
    }

    protected void buildBaseDaoUpdateByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 完整更新一条记录 -->");
        bw.newLine();
        bw.write("\t<update id=\"updateByPrimaryKey\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tupdate " + this.getName(table.getName()));
        bw.newLine();
        bw.write("\t\tset ");

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            if (!column.getName().equalsIgnoreCase(primaryKey.getColumnName()) && !this.genConfig.getInsertTimeForHisName().equalsIgnoreCase(getObjectName(column.getName()))) {
                if (this.genConfig.getVersionName().equalsIgnoreCase(getObjectName(column.getName()))) {
                    bw.write(column.getName() + "=" + getInstanceName(column.getName()) + "+1");
                } else if (this.genConfig.getOperateTimeForHisName().equalsIgnoreCase(getObjectName(column.getName())) && StringUtils.isNotBlank(this.genConfig.getOperateTimeForHisFunc())) {
                    bw.write(this.getName(column.getName()) + "=" + this.genConfig.getOperateTimeForHisFunc());
                } else {
                    bw.write(this.getName(column.getName()) + "=#{" + getInstanceName(column.getName()) + "}");
                }

                if (i != size - 1) {
                    bw.write(",");
                    bw.newLine();
                    bw.write("\t\t\t");
                }
            }
        }

        bw.newLine();
        bw.write("\t\twhere " + this.getName(primaryKey.getColumnName()) + " = #{" + getInstanceName(primaryKey.getColumnName()) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
    }
}
