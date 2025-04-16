//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

import ins.framework.mybatis.generator.executer.*;
import ins.framework.mybatis.generator.schema.Table;
import ins.framework.mybatis.generator.util.GeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Generator extends BaseGenerator {
    private static final Logger log = LoggerFactory.getLogger(Generator.class);
    private GenFileInfo voInfo;
    private GenFileInfo poInfo;
    private GenFileInfo daoInfo;
    private GenFileInfo baseMapperXmlInfo;
    private GenFileInfo mapperXmlInfo;

    public Generator() {
    }

    private String assemblePackage(String module, String catalog) {
        String result = this.genConfig.getBasePackage() + "." + module;
        if (catalog != null && catalog.trim().length() > 0) {
            result = result + "." + catalog;
        }

        return result;
    }

    private String assembleXmlPackage(String module) {
        String result = "";
        if (module != null && module.trim().length() > 0) {
            result = module;
        } else {
            result = "misc";
        }

        return result;
    }

    private void resetFileInfo(String beanNameIn, String module) {
        String saveDir = this.genConfig.getSaveDir();
        String beanName = beanNameIn;
        String ignoreTablePrefix;
        if (this.genConfig.getIgnoreTablePrefixs() != null) {
            String[] var5 = this.genConfig.getIgnoreTablePrefixs();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                ignoreTablePrefix = var5[var7];
                ignoreTablePrefix = ignoreTablePrefix.replace("_", "");
                if (beanName.toLowerCase().startsWith(ignoreTablePrefix.toLowerCase())) {
                    beanName = beanName.substring(ignoreTablePrefix.length());
                    break;
                }
            }
        }

        String name = beanName + "Vo";
        String packageName = this.assemblePackage(module, "vo");
        String path = getFilePath(this.genConfig.getSaveDirForVo(), getPathFromPackageName(packageName));
        this.voInfo = new GenFileInfo(name, packageName, path);
        name = beanName;
        if (this.genConfig.keepPrefixForPO) {
            name = beanNameIn;
        }

        packageName = this.assemblePackage(module, "po");
        path = getFilePath(saveDir, getPathFromPackageName(packageName));
        this.poInfo = new GenFileInfo(name, packageName, path);
        name = beanName + "Dao";
        packageName = this.assemblePackage(module, "dao");
        path = getFilePath(saveDir, getPathFromPackageName(packageName));
        this.daoInfo = new GenFileInfo(name, packageName, path);
        name = beanName + "BaseDao";
        packageName = this.assembleXmlPackage(module);
        ignoreTablePrefix = getFilePath(this.genConfig.getSaveDirForXml(), "base");
        path = getFilePath(ignoreTablePrefix, getPathFromPackageName(packageName));
        this.baseMapperXmlInfo = new GenFileInfo(name, packageName, path);
        name = beanName + "Dao";
        packageName = this.assembleXmlPackage(module);
        ignoreTablePrefix = getFilePath(this.genConfig.getSaveDirForXml(), "custom");
        path = getFilePath(ignoreTablePrefix, getPathFromPackageName(packageName));
        this.mapperXmlInfo = new GenFileInfo(name, packageName, path);
    }

    protected void run(Table table, String module) throws IOException {
        log.info("============处理表" + table.getName() + "==================");
        if (table.getPrimaryKeys().isEmpty()) {
            log.info("表" + table.getName() + "没有主键字段，忽略生成，请手工编写.");
        } else {
            String beanName = GeneratorUtils.getObjectName(table.getName());
            this.resetFileInfo(beanName, module);
            this.fileOvervide = false;
            if (this.containsGenType(GenType.VO) && this.validFile(this.voInfo.getPath(), this.voInfo.getName(), ".java")) {
                (new VoExecuter(this.genConfig, this.voInfo)).build(table);
            }

            this.fileOvervide = true;
            if (this.containsGenType(GenType.PO) && this.validFile(this.poInfo.getPath(), this.poInfo.getName(), ".java")) {
                (new PoExecuter(this.genConfig, this.poInfo)).build(table);
            }

            if (table.getPrimaryKeys().size() > 1) {
                log.info("表" + table.getName() + "为联合主键，忽略dao和mapper生成,请手工编写.");
            } else {
                this.fileOvervide = false;
                if (this.containsGenType(GenType.DAO) && this.validFile(this.daoInfo.getPath(), this.daoInfo.getName(), ".java")) {
                    (new DaoExecuter(this.genConfig, this.daoInfo, this.poInfo)).build(table);
                }

                this.fileOvervide = true;
                if (this.containsGenType(GenType.BASE_MAPPER_XML) && this.validFile(this.baseMapperXmlInfo.getPath(), this.baseMapperXmlInfo.getName(), ".xml")) {
                    (new MyBatisBaseXmlExecuter(this.genConfig, this.baseMapperXmlInfo, this.daoInfo, this.poInfo)).build(table);
                }

                this.fileOvervide = false;
                if (this.containsGenType(GenType.MAPPER_XML) && this.validFile(this.mapperXmlInfo.getPath(), this.mapperXmlInfo.getName(), ".xml")) {
                    (new MyBatisCustomXmlExecuter(this.genConfig, this.mapperXmlInfo, this.daoInfo)).build(table);
                }

            }
        }
    }
}
