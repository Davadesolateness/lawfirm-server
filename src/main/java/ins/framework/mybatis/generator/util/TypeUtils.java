//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.util;

public class TypeUtils {
    public TypeUtils() {
    }

    public static String processType(String type) {
        String result = "";
        if (type.endsWith(" identity")) {
            type = type.substring(0, type.length() - " identity".length());
        }

        switch (type.toLowerCase()) {
            case "nvarchar":
            case "varchar2":
            case "varchar":
            case "char":
            case "text":
            case "longtext":
                result = "String";
                break;
            case "double":
                result = "Double";
                break;
            case "float":
                result = "Float";
                break;
            case "id":
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
                result = "Integer";
                break;
            case "bigint":
                result = "Long";
                break;
            case "date":
            case "time":
            case "datetime":
            case "smalldatetime":
            case "year":
            case "timestamp":
                result = "Date";
                break;
            case "bit":
                result = "Boolean";
                break;
            case "number":
            case "numeric":
            case "decimal":
                result = "BigDecimal";
                break;
            case "binary":
            case "raw":
            case "blob":
                result = "byte[]";
                break;
            default:
                System.err.println("Unsupport type [" + type + "]");
        }

        return result;
    }
}
