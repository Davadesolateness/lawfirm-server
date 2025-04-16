//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

import ins.framework.mybatis.generator.schema.Column;
import ins.framework.mybatis.generator.schema.Database;
import ins.framework.mybatis.generator.schema.PrimaryKey;
import ins.framework.mybatis.generator.schema.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DatabaseUtils {
    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);
    private Connection conn = null;
    private String schema;
    Database database = null;

    private DatabaseUtils() {
    }

    public static DatabaseUtils getInstance(Connection conn, String schema) {
        DatabaseUtils obj = new DatabaseUtils();

        try {
            DatabaseMetaData metaData = conn.getMetaData();
            Database database = new Database();
            database.setProductName(metaData.getDatabaseProductName());
            database.setProductVersion(metaData.getDatabaseProductVersion());
            obj.conn = conn;
            obj.database = database;
            obj.schema = schema;
        } catch (Exception var5) {
            Exception e = var5;
            log.warn("{}", e);
        }

        return obj;
    }

    public Map<String, String> getAllTableNamesMap() {
        ResultSet rs = null;
        Map<String, String> result = new HashMap();

        try {
            DatabaseMetaData metaData = this.conn.getMetaData();
            rs = metaData.getTables((String) null, this.schema, (String) null, new String[]{"TABLE"});

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                result.put(tableName.toLowerCase(), tableName);
            }

            rs.close();
        } catch (SQLException var5) {
            SQLException e = var5;
            log.warn("{}", e);
        }

        return result;
    }

    public Table getTableInfo(String tableName) {
        Table table = new Table();
        table.setName(tableName);
        ResultSet rs = null;

        try {
            DatabaseMetaData metaData = this.conn.getMetaData();
            rs = metaData.getTables((String) null, this.schema, tableName, new String[]{"TABLE"});
            if (rs.next()) {
                table.setComment(rs.getString("REMARKS"));
            }

            rs.close();
        } catch (SQLException var5) {
            SQLException e = var5;
            log.warn("{}", e);
        }

        table.setColumns(this.getTableColumns(tableName));
        table.setPrimaryKeys(this.getTablePrimaryKeys(tableName));
        return table;
    }

    private List<Column> getTableColumns(String tableName) {
        List<Column> columns = new ArrayList();
        ResultSet rs = null;

        try {
            DatabaseMetaData metaData = this.conn.getMetaData();
            rs = metaData.getColumns((String) null, this.schema, tableName, (String) null);

            while (rs.next()) {
                Column column = new Column();
                column.setName(rs.getString("COLUMN_NAME"));
                column.setType(rs.getString("TYPE_NAME"));
                column.setSize(rs.getInt("COLUMN_SIZE"));
                column.setComment(rs.getString("REMARKS"));
                column.setNullable(rs.getBoolean("NULLABLE"));
                column.setDefaultValue(rs.getString("COLUMN_DEF"));
                columns.add(column);
            }

            rs.close();
        } catch (SQLException var6) {
            SQLException e = var6;
            log.warn("{}", e);
        }

        return columns;
    }

    private List<PrimaryKey> getTablePrimaryKeys(String tableName) {
        List<PrimaryKey> primaryKeys = new ArrayList();
        ResultSet rs = null;

        try {
            DatabaseMetaData metaData = this.conn.getMetaData();
            rs = metaData.getPrimaryKeys((String) null, this.schema, tableName);

            while (rs.next()) {
                PrimaryKey obj = new PrimaryKey();
                obj.setColumnName(rs.getString("COLUMN_NAME"));
                obj.setKeySeq(rs.getInt("KEY_SEQ"));
                obj.setPkName(rs.getString("PK_NAME"));
                primaryKeys.add(obj);
            }

            rs.close();
        } catch (SQLException var6) {
            SQLException e = var6;
            log.warn("{}", e);
        }

        return primaryKeys;
    }
}
