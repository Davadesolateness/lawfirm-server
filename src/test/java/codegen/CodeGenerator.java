package codegen;

import ins.framework.mybatis.generator.GenConfig;
import ins.framework.mybatis.generator.GenParam;
import ins.framework.mybatis.generator.GenType;
import ins.framework.mybatis.generator.Generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {

        /**
         * 一、每一个表创建两个，一个带下划线的一个不带下划线的。
         * 二、用有下划线的表生成所有相关文件，PO、VO、DAO、XML等。
         * 三、生成的BASE中的文件不要进行修改（但可以自行添加序列的赋值语句）。
         **/

        createBaseFiles("user", "users");
//        createBaseFiles("user", "corporateclient");
//        createBaseFiles("user", "individualclient");
//        createBaseFiles("user", "administrator");
//        createBaseFiles("user", "customerServiceInfo");
//
//        createBaseFiles("lawyer", "lawyer");
//        createBaseFiles("lawyer", "lawyerspecialtyrelation");
//        createBaseFiles("lawyer", "lawyerspecialty");
//        createBaseFiles("lawer", "lawyerServiceStat");

//        createBaseFiles("order", "orders");
//        createBaseFiles("order", "orderTime");
//        createBaseFiles("order", "welfareDistributionCustomer");

//        createBaseFiles("chat", "chatSession");
//        createBaseFiles("chat", "chatMessage");

    }


    /**
     * 通用设置，请不要改动
     */
    private static void createBaseFiles(String packageName, String tabName) {
        List<GenParam> paramList = new ArrayList<GenParam>();
        /**
         * 请保持生成参数的完整，不要注释
         */
        // paramList.add(new GenParam("commondata", new String[] {
        // "Prp_D_Maim_Item" }));
        paramList.add(new GenParam(packageName, new String[]{tabName}));
        GenConfig gc = new GenConfig();
        gc.setBasePackage("com.lawfirm.lawfirmserver");
        // 设置要忽略的表名前缀，默认空
        gc.setIgnoreTablePrefixs(new String[]{"prpl", "prpd"});
        // 设置PO是否保留前缀(设置忽略表名前缀时)，默认true
        gc.setKeepPrefixForPO(true);
        // 设置取操作时间函数，默认数据库自适应
        // gc.setOperateTimeForHisFunc("sysdate");
        // 设置是否默认开启二级缓存（影响base中的MapperXML），默认false
        gc.setDefaultCache(false);
        /// 是否启用删除标志模式（不真删除，只是设置标志deleted_flag字段为1），默认false
        gc.setDeletedFlagMode(true);
        /** 设置忽略下划线，默认为false */
        gc.setDbColumnUnderline(false);
        // mysql 数据库相关配置
        // 设置基本保存目录（Java源代码根目录）
        String strDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        strDir = "E:\\workspace\\lawfirm\\mybatis\\java";
        //strDir = strDir.substring(1).replace("/target/test-classes/", "/src/main/java");
        gc.setSaveDir(strDir);
        // 可代码指定vo和xml的位置
//		 gc.setSaveDirForVo(new File(gc.getSaveDir(),
//		 "../../../../misc-vo/src/main/java").getAbsolutePath());
        gc.setSaveDirForXml(new File(gc.getSaveDir(),
                "../resources/mapper").getAbsolutePath());

        gc.setDbDriverName("com.mysql.jdbc.Driver");
        gc.setDbUser("lawfirm");
//		gc.setDbSchema("nclaim_dev");// Oracle的schema必需设置，且为大写
        gc.setDbPassword("zxc@2025");
//		gc.setDbUrl("jdbc:oracle:thin:@192.168.10.215:1521:orcl");
        gc.setDbUrl("jdbc:mysql://localhost:3306/lawfirmdb?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false");

        // 支持生成的文件类型:生成PO、BASE_MAPPER_XML（自动覆盖）、Dao、VO、MapperXML（不覆盖）
        gc.setGenTypes(
                new GenType[]{GenType.VO, GenType.PO, GenType.DAO, GenType.BASE_MAPPER_XML, GenType.MAPPER_XML});
        Generator generator = new Generator();
        generator.setGenConfig(gc);
        generator.setParamList(paramList);
        generator.generate();
    }
}
