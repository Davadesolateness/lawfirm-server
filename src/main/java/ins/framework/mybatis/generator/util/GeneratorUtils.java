//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.util;

import ins.framework.mybatis.generator.schema.Column;
import ins.framework.mybatis.generator.schema.PrimaryKey;
import ins.framework.mybatis.generator.schema.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GeneratorUtils {
    public GeneratorUtils() {
    }

    public static List<String> getTableColumnTypes(Table table) {
        List<String> types = new ArrayList();
        List<Column> columns = table.getColumns();
        int size = columns.size();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            types.add(column.getType());
        }

        return types;
    }

    public static List<Column> getTablePrimaryKeysColumns(Table table) {
        Map<String, Column> columnMap = new HashMap();
        List<Column> columns = table.getColumns();
        int size = columns.size();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            columnMap.put(column.getName().toLowerCase(), column);
        }

        List<Column> result = new ArrayList();
        List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
        size = primaryKeys.size();

        for (int i = 0; i < size; ++i) {
            PrimaryKey primaryKey = (PrimaryKey) primaryKeys.get(i);
            result.add(columnMap.get(primaryKey.getColumnName().toLowerCase()));
        }

        return result;
    }

    public static List<String> getTablePrimaryKeysCloumnNames(Table table) {
        List<String> result = new ArrayList();
        List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
        int size = primaryKeys.size();

        for (int i = 0; i < size; ++i) {
            PrimaryKey primaryKey = (PrimaryKey) primaryKeys.get(i);
            result.add(primaryKey.getColumnName().toLowerCase());
        }

        return result;
    }

    public static List<String> getTablePrimaryKeysTypes(Table table) {
        Map<String, String> columnMap = new HashMap();
        List<Column> columns = table.getColumns();
        int size = columns.size();

        for (int i = 0; i < size; ++i) {
            Column column = (Column) columns.get(i);
            columnMap.put(column.getName().toLowerCase(), column.getType());
        }

        List<String> types = new ArrayList();
        List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
        size = primaryKeys.size();

        for (int i = 0; i < size; ++i) {
            PrimaryKey primaryKey = (PrimaryKey) primaryKeys.get(i);
            types.add(columnMap.get(primaryKey.getColumnName().toLowerCase()));
        }

        return types;
    }

    public static boolean isDate(List<String> types) {
        Iterator var1 = types.iterator();

        String t;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            String type = (String) var1.next();
            t = type.toLowerCase();
        } while (!t.contains("date") && !t.contains("time"));

        return true;
    }

    public static boolean isDecimal(List<String> types) {
        Iterator var1 = types.iterator();

        String type;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            type = (String) var1.next();
        } while (!type.toLowerCase().contains("number") && !type.toLowerCase().contains("numeric") && !type.toLowerCase().contains("decimal"));

        return true;
    }

    public static String getInstanceName(String field) {
        return WordFileUtils.getBeautyInstanceName(field);
    }

    public static String getObjectName(String field) {
        return WordFileUtils.getBeautyObjectName(field);
    }
}
