//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

import java.util.Arrays;

public class GenConfig {
    private String basePackage;
    protected String saveDir;
    protected String saveDirForVo;
    protected String saveDirForXml;
    private GenType[] genTypes;
    private String insertTimeForHisName;
    private String operateTimeForHisName;
    private String operateTimeForHisFunc;
    private String versionName;
    protected String[] ignoreTablePrefixs = null;
    protected boolean keepPrefixForPO = true;
    protected boolean deletedFlagMode = false;
    protected boolean ignoreUnderline = false;
    protected String[] tableNames = null;
    protected boolean defaultCache = false;
    protected boolean fileOverride = true;
    protected boolean dbPrefix = false;
    protected boolean dbColumnUnderline = false;
    protected String dbDriverName;
    protected String dbUser;
    protected String dbPassword;
    protected String dbUrl;
    protected String dbSchema;

    public GenConfig() {
    }

    public String getBasePackage() {
        return this.basePackage;
    }

    public String getSaveDir() {
        return this.saveDir;
    }

    public String getSaveDirForVo() {
        return this.saveDirForVo;
    }

    public String getSaveDirForXml() {
        return this.saveDirForXml;
    }

    public GenType[] getGenTypes() {
        return this.genTypes;
    }

    public String getInsertTimeForHisName() {
        return this.insertTimeForHisName;
    }

    public String getOperateTimeForHisName() {
        return this.operateTimeForHisName;
    }

    public String getOperateTimeForHisFunc() {
        return this.operateTimeForHisFunc;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public String[] getIgnoreTablePrefixs() {
        return this.ignoreTablePrefixs;
    }

    public boolean isKeepPrefixForPO() {
        return this.keepPrefixForPO;
    }

    public boolean isDeletedFlagMode() {
        return this.deletedFlagMode;
    }

    public boolean isIgnoreUnderline() {
        return this.ignoreUnderline;
    }

    public String[] getTableNames() {
        return this.tableNames;
    }

    public boolean isDefaultCache() {
        return this.defaultCache;
    }

    public boolean isFileOverride() {
        return this.fileOverride;
    }

    public boolean isDbPrefix() {
        return this.dbPrefix;
    }

    public boolean isDbColumnUnderline() {
        return this.dbColumnUnderline;
    }

    public String getDbDriverName() {
        return this.dbDriverName;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public String getDbSchema() {
        return this.dbSchema;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public void setSaveDirForVo(String saveDirForVo) {
        this.saveDirForVo = saveDirForVo;
    }

    public void setSaveDirForXml(String saveDirForXml) {
        this.saveDirForXml = saveDirForXml;
    }

    public void setGenTypes(GenType[] genTypes) {
        this.genTypes = genTypes;
    }

    public void setInsertTimeForHisName(String insertTimeForHisName) {
        this.insertTimeForHisName = insertTimeForHisName;
    }

    public void setOperateTimeForHisName(String operateTimeForHisName) {
        this.operateTimeForHisName = operateTimeForHisName;
    }

    public void setOperateTimeForHisFunc(String operateTimeForHisFunc) {
        this.operateTimeForHisFunc = operateTimeForHisFunc;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setIgnoreTablePrefixs(String[] ignoreTablePrefixs) {
        this.ignoreTablePrefixs = ignoreTablePrefixs;
    }

    public void setKeepPrefixForPO(boolean keepPrefixForPO) {
        this.keepPrefixForPO = keepPrefixForPO;
    }

    public void setDeletedFlagMode(boolean deletedFlagMode) {
        this.deletedFlagMode = deletedFlagMode;
    }

    public void setIgnoreUnderline(boolean ignoreUnderline) {
        this.ignoreUnderline = ignoreUnderline;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public void setDefaultCache(boolean defaultCache) {
        this.defaultCache = defaultCache;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public void setDbPrefix(boolean dbPrefix) {
        this.dbPrefix = dbPrefix;
    }

    public void setDbColumnUnderline(boolean dbColumnUnderline) {
        this.dbColumnUnderline = dbColumnUnderline;
    }

    public void setDbDriverName(String dbDriverName) {
        this.dbDriverName = dbDriverName;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenConfig)) {
            return false;
        } else {
            GenConfig other = (GenConfig) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label207:
                {
                    Object this$basePackage = this.getBasePackage();
                    Object other$basePackage = other.getBasePackage();
                    if (this$basePackage == null) {
                        if (other$basePackage == null) {
                            break label207;
                        }
                    } else if (this$basePackage.equals(other$basePackage)) {
                        break label207;
                    }

                    return false;
                }

                Object this$saveDir = this.getSaveDir();
                Object other$saveDir = other.getSaveDir();
                if (this$saveDir == null) {
                    if (other$saveDir != null) {
                        return false;
                    }
                } else if (!this$saveDir.equals(other$saveDir)) {
                    return false;
                }

                Object this$saveDirForVo = this.getSaveDirForVo();
                Object other$saveDirForVo = other.getSaveDirForVo();
                if (this$saveDirForVo == null) {
                    if (other$saveDirForVo != null) {
                        return false;
                    }
                } else if (!this$saveDirForVo.equals(other$saveDirForVo)) {
                    return false;
                }

                label186:
                {
                    Object this$saveDirForXml = this.getSaveDirForXml();
                    Object other$saveDirForXml = other.getSaveDirForXml();
                    if (this$saveDirForXml == null) {
                        if (other$saveDirForXml == null) {
                            break label186;
                        }
                    } else if (this$saveDirForXml.equals(other$saveDirForXml)) {
                        break label186;
                    }

                    return false;
                }

                if (!Arrays.deepEquals(this.getGenTypes(), other.getGenTypes())) {
                    return false;
                } else {
                    Object this$insertTimeForHisName = this.getInsertTimeForHisName();
                    Object other$insertTimeForHisName = other.getInsertTimeForHisName();
                    if (this$insertTimeForHisName == null) {
                        if (other$insertTimeForHisName != null) {
                            return false;
                        }
                    } else if (!this$insertTimeForHisName.equals(other$insertTimeForHisName)) {
                        return false;
                    }

                    label171:
                    {
                        Object this$operateTimeForHisName = this.getOperateTimeForHisName();
                        Object other$operateTimeForHisName = other.getOperateTimeForHisName();
                        if (this$operateTimeForHisName == null) {
                            if (other$operateTimeForHisName == null) {
                                break label171;
                            }
                        } else if (this$operateTimeForHisName.equals(other$operateTimeForHisName)) {
                            break label171;
                        }

                        return false;
                    }

                    Object this$operateTimeForHisFunc = this.getOperateTimeForHisFunc();
                    Object other$operateTimeForHisFunc = other.getOperateTimeForHisFunc();
                    if (this$operateTimeForHisFunc == null) {
                        if (other$operateTimeForHisFunc != null) {
                            return false;
                        }
                    } else if (!this$operateTimeForHisFunc.equals(other$operateTimeForHisFunc)) {
                        return false;
                    }

                    Object this$versionName = this.getVersionName();
                    Object other$versionName = other.getVersionName();
                    if (this$versionName == null) {
                        if (other$versionName != null) {
                            return false;
                        }
                    } else if (!this$versionName.equals(other$versionName)) {
                        return false;
                    }

                    if (!Arrays.deepEquals(this.getIgnoreTablePrefixs(), other.getIgnoreTablePrefixs())) {
                        return false;
                    } else if (this.isKeepPrefixForPO() != other.isKeepPrefixForPO()) {
                        return false;
                    } else if (this.isDeletedFlagMode() != other.isDeletedFlagMode()) {
                        return false;
                    } else if (this.isIgnoreUnderline() != other.isIgnoreUnderline()) {
                        return false;
                    } else if (!Arrays.deepEquals(this.getTableNames(), other.getTableNames())) {
                        return false;
                    } else if (this.isDefaultCache() != other.isDefaultCache()) {
                        return false;
                    } else if (this.isFileOverride() != other.isFileOverride()) {
                        return false;
                    } else if (this.isDbPrefix() != other.isDbPrefix()) {
                        return false;
                    } else if (this.isDbColumnUnderline() != other.isDbColumnUnderline()) {
                        return false;
                    } else {
                        label137:
                        {
                            Object this$dbDriverName = this.getDbDriverName();
                            Object other$dbDriverName = other.getDbDriverName();
                            if (this$dbDriverName == null) {
                                if (other$dbDriverName == null) {
                                    break label137;
                                }
                            } else if (this$dbDriverName.equals(other$dbDriverName)) {
                                break label137;
                            }

                            return false;
                        }

                        label130:
                        {
                            Object this$dbUser = this.getDbUser();
                            Object other$dbUser = other.getDbUser();
                            if (this$dbUser == null) {
                                if (other$dbUser == null) {
                                    break label130;
                                }
                            } else if (this$dbUser.equals(other$dbUser)) {
                                break label130;
                            }

                            return false;
                        }

                        Object this$dbPassword = this.getDbPassword();
                        Object other$dbPassword = other.getDbPassword();
                        if (this$dbPassword == null) {
                            if (other$dbPassword != null) {
                                return false;
                            }
                        } else if (!this$dbPassword.equals(other$dbPassword)) {
                            return false;
                        }

                        Object this$dbUrl = this.getDbUrl();
                        Object other$dbUrl = other.getDbUrl();
                        if (this$dbUrl == null) {
                            if (other$dbUrl != null) {
                                return false;
                            }
                        } else if (!this$dbUrl.equals(other$dbUrl)) {
                            return false;
                        }

                        Object this$dbSchema = this.getDbSchema();
                        Object other$dbSchema = other.getDbSchema();
                        if (this$dbSchema == null) {
                            if (other$dbSchema != null) {
                                return false;
                            }
                        } else if (!this$dbSchema.equals(other$dbSchema)) {
                            return false;
                        }

                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GenConfig;
    }

    public int hashCode() {
        int result = 1;
        Object $basePackage = this.getBasePackage();
        result = result * 59 + ($basePackage == null ? 43 : $basePackage.hashCode());
        Object $saveDir = this.getSaveDir();
        result = result * 59 + ($saveDir == null ? 43 : $saveDir.hashCode());
        Object $saveDirForVo = this.getSaveDirForVo();
        result = result * 59 + ($saveDirForVo == null ? 43 : $saveDirForVo.hashCode());
        Object $saveDirForXml = this.getSaveDirForXml();
        result = result * 59 + ($saveDirForXml == null ? 43 : $saveDirForXml.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getGenTypes());
        Object $insertTimeForHisName = this.getInsertTimeForHisName();
        result = result * 59 + ($insertTimeForHisName == null ? 43 : $insertTimeForHisName.hashCode());
        Object $operateTimeForHisName = this.getOperateTimeForHisName();
        result = result * 59 + ($operateTimeForHisName == null ? 43 : $operateTimeForHisName.hashCode());
        Object $operateTimeForHisFunc = this.getOperateTimeForHisFunc();
        result = result * 59 + ($operateTimeForHisFunc == null ? 43 : $operateTimeForHisFunc.hashCode());
        Object $versionName = this.getVersionName();
        result = result * 59 + ($versionName == null ? 43 : $versionName.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getIgnoreTablePrefixs());
        result = result * 59 + (this.isKeepPrefixForPO() ? 79 : 97);
        result = result * 59 + (this.isDeletedFlagMode() ? 79 : 97);
        result = result * 59 + (this.isIgnoreUnderline() ? 79 : 97);
        result = result * 59 + Arrays.deepHashCode(this.getTableNames());
        result = result * 59 + (this.isDefaultCache() ? 79 : 97);
        result = result * 59 + (this.isFileOverride() ? 79 : 97);
        result = result * 59 + (this.isDbPrefix() ? 79 : 97);
        result = result * 59 + (this.isDbColumnUnderline() ? 79 : 97);
        Object $dbDriverName = this.getDbDriverName();
        result = result * 59 + ($dbDriverName == null ? 43 : $dbDriverName.hashCode());
        Object $dbUser = this.getDbUser();
        result = result * 59 + ($dbUser == null ? 43 : $dbUser.hashCode());
        Object $dbPassword = this.getDbPassword();
        result = result * 59 + ($dbPassword == null ? 43 : $dbPassword.hashCode());
        Object $dbUrl = this.getDbUrl();
        result = result * 59 + ($dbUrl == null ? 43 : $dbUrl.hashCode());
        Object $dbSchema = this.getDbSchema();
        result = result * 59 + ($dbSchema == null ? 43 : $dbSchema.hashCode());
        return result;
    }

    public String toString() {
        return "GenConfig(basePackage=" + this.getBasePackage() + ", saveDir=" + this.getSaveDir() + ", saveDirForVo=" + this.getSaveDirForVo() + ", saveDirForXml=" + this.getSaveDirForXml() + ", genTypes=" + Arrays.deepToString(this.getGenTypes()) + ", insertTimeForHisName=" + this.getInsertTimeForHisName() + ", operateTimeForHisName=" + this.getOperateTimeForHisName() + ", operateTimeForHisFunc=" + this.getOperateTimeForHisFunc() + ", versionName=" + this.getVersionName() + ", ignoreTablePrefixs=" + Arrays.deepToString(this.getIgnoreTablePrefixs()) + ", keepPrefixForPO=" + this.isKeepPrefixForPO() + ", deletedFlagMode=" + this.isDeletedFlagMode() + ", ignoreUnderline=" + this.isIgnoreUnderline() + ", tableNames=" + Arrays.deepToString(this.getTableNames()) + ", defaultCache=" + this.isDefaultCache() + ", fileOverride=" + this.isFileOverride() + ", dbPrefix=" + this.isDbPrefix() + ", dbColumnUnderline=" + this.isDbColumnUnderline() + ", dbDriverName=" + this.getDbDriverName() + ", dbUser=" + this.getDbUser() + ", dbPassword=" + this.getDbPassword() + ", dbUrl=" + this.getDbUrl() + ", dbSchema=" + this.getDbSchema() + ")";
    }
}
