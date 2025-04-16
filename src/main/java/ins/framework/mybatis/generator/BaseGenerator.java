//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

import ins.framework.mybatis.generator.schema.Database;
import ins.framework.mybatis.generator.schema.Table;
import ins.framework.mybatis.generator.util.WordFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

abstract class BaseGenerator {
    protected static final String JAVA_SUFFIX = ".java";
    protected static final String XML_SUFFIX = ".xml";
    private static final Logger log = LoggerFactory.getLogger(BaseGenerator.class);
    protected GenConfig genConfig;
    protected List<GenParam> paramList;
    protected Database database;
    protected boolean fileOvervide = false;

    BaseGenerator() {
    }

    protected static String getPathFromPackageName(String packageName) {
        return StringUtils.isEmpty(packageName) ? "" : packageName.replace(".", File.separator);
    }

    protected static String getFilePath(String savePath, String segment) {
        File folder = new File(savePath, segment);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder.getPath();
    }

    protected abstract void run(Table var1, String var2) throws IOException;

    public void setGenConfig(GenConfig genConfig) {
        this.genConfig = genConfig;
    }

    public void setParamList(List<GenParam> paramList) {
        this.paramList = paramList;
    }

    protected boolean containsGenType(GenType genType) {
        GenType[] var2 = this.genConfig.getGenTypes();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            GenType gen = var2[var4];
            if (gen == genType) {
                return true;
            }
        }

        return false;
    }

    public void generate() {
        WordFileUtils.initWordMap();
        if (StringUtils.isBlank(this.genConfig.getInsertTimeForHisName())) {
            this.genConfig.setInsertTimeForHisName("insertTimeForHis");
        }

        this.genConfig.setInsertTimeForHisName(this.genConfig.getInsertTimeForHisName().toLowerCase().replace("_", ""));
        if (StringUtils.isBlank(this.genConfig.getOperateTimeForHisName())) {
            this.genConfig.setOperateTimeForHisName("operateTimeForHis");
        }

        this.genConfig.setOperateTimeForHisName(this.genConfig.getOperateTimeForHisName().toLowerCase().replace("_", ""));
        if (StringUtils.isBlank(this.genConfig.getVersionName())) {
            this.genConfig.setVersionName("Version");
        }

        this.genConfig.setVersionName(this.genConfig.getVersionName().toLowerCase().replace("_", ""));
        if (StringUtils.isBlank(this.genConfig.getSaveDirForVo())) {
            this.genConfig.setSaveDirForVo(this.genConfig.getSaveDir());
        }

        if (StringUtils.isBlank(this.genConfig.getSaveDirForXml())) {
            this.genConfig.setSaveDirForXml((new File(this.genConfig.getSaveDir(), "../resources/mapper")).getAbsolutePath());
        }

        if (this.containsGenType(GenType.VO) && StringUtils.isBlank(this.genConfig.getSaveDirForVo())) {
            this.genConfig.setSaveDirForVo(this.genConfig.getSaveDir());
            log.info("未设置SaveDirForVo参数,使用默认位置");
        }

        if (this.containsGenType(GenType.MAPPER_XML) && StringUtils.isBlank(this.genConfig.getSaveDirForXml())) {
            throw new IllegalArgumentException("生成Mapper XML时需要设置SaveDirForXml参数");
        } else if (this.containsGenType(GenType.BASE_MAPPER_XML) && StringUtils.isBlank(this.genConfig.getSaveDirForXml())) {
            throw new IllegalArgumentException("生成Base Mapper XML时需要设置SaveDirForXml参数");
        } else {
            try {
                Class.forName(this.genConfig.getDbDriverName());
                Properties props = new Properties();
                props.setProperty("user", this.genConfig.getDbUser());
                props.setProperty("password", this.genConfig.getDbPassword());
                props.setProperty("remarks", "true");
                props.setProperty("useInformationSchema", "true");
                Connection conn = DriverManager.getConnection(this.genConfig.getDbUrl(), props);
                Throwable var3 = null;

                try {
                    DatabaseUtils databaseUtils = DatabaseUtils.getInstance(conn, this.genConfig.getDbSchema());
                    this.database = databaseUtils.database;
                    String dbName = this.database.getProductName().toLowerCase();
                    if (StringUtils.isBlank(this.genConfig.getDbSchema()) && dbName.indexOf("oracle") != -1) {
                        this.genConfig.setDbSchema(this.genConfig.getDbUser().toUpperCase());
                    }

                    if (StringUtils.isBlank(this.genConfig.getOperateTimeForHisFunc())) {
                        String dateFunc = "";
                        if (dbName.indexOf("informix") != -1) {
                            dateFunc = "current";
                        } else if (dbName.indexOf("oracle") != -1) {
                            dateFunc = "sysdate";
                        } else if (dbName.indexOf("postgresql") != -1) {
                            dateFunc = "now()";
                        } else if (dbName.indexOf("mysql") != -1) {
                            dateFunc = "now()";
                        } else if (dbName.indexOf("sinoregal") != -1) {
                            dateFunc = "current";
                        } else if (dbName.indexOf("argon") != -1) {
                            dateFunc = "current";
                        } else if (dbName.indexOf("ibm informix") != -1) {
                            dateFunc = "current";
                        } else if (dbName.indexOf("dynamic server") != -1) {
                            dateFunc = "current";
                        } else if (dbName.indexOf("sql server") != -1) {
                            dateFunc = "GetDate()";
                        }

                        this.genConfig.setOperateTimeForHisFunc(dateFunc);
                    }

                    if (StringUtils.isBlank(this.genConfig.getOperateTimeForHisFunc())) {
                        log.warn("请自行设置setOperateTimeForHisFunc");
                    }

                    Map<String, String> tableNamesMap = databaseUtils.getAllTableNamesMap();
                    if (tableNamesMap.size() != 0) {
                        Iterator var7 = this.paramList.iterator();

                        while (var7.hasNext()) {
                            GenParam genParam = (GenParam) var7.next();
                            String[] tableNames = genParam.getTables();

                            for (int i = 0; i < tableNames.length; ++i) {
                                String tableName = tableNames[i].toLowerCase();
                                if (!tableNamesMap.containsKey(tableName)) {
                                    log.warn("Can't find table {}", tableName);
                                } else {
                                    Table table = databaseUtils.getTableInfo((String) tableNamesMap.get(tableName));
                                    this.run(table, genParam.getModule());
                                }
                            }
                        }

                        return;
                    }
                } catch (Throwable var22) {
                    var3 = var22;
                    throw var22;
                } finally {
                    if (conn != null) {
                        if (var3 != null) {
                            try {
                                conn.close();
                            } catch (Throwable var21) {
                                var3.addSuppressed(var21);
                            }
                        } else {
                            conn.close();
                        }
                    }

                }

            } catch (Exception var24) {
                Exception e = var24;
                log.warn("{}", e);
            }
        }
    }

    protected boolean validFile(String dirPath, String beanName, String suffix) {
        File file = new File(dirPath, beanName + suffix);
        return !file.exists() || this.fileOvervide;
    }

    protected void openDir() {
        try {
            String osName = System.getProperty("os.name");
            if (osName != null) {
                if (osName.contains("Mac")) {
                    Runtime.getRuntime().exec("open " + this.genConfig.getSaveDir());
                } else if (osName.contains("Windows")) {
                    Runtime.getRuntime().exec("cmd /c start " + this.genConfig.getSaveDir());
                } else {
                    log.error("save dir:" + this.genConfig.getSaveDir());
                }
            }
        } catch (IOException var2) {
            IOException e = var2;
            log.warn("{}", e);
        }

    }
}
