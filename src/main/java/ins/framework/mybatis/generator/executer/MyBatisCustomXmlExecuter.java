//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.executer;

import ins.framework.mybatis.generator.GenConfig;
import ins.framework.mybatis.generator.GenFileInfo;
import ins.framework.mybatis.generator.schema.Table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisCustomXmlExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(MyBatisCustomXmlExecuter.class);
    private GenFileInfo mapperXmlInfo;
    private GenFileInfo daoInfo;

    public MyBatisCustomXmlExecuter(GenConfig genConfig, GenFileInfo mapperXmlInfo, GenFileInfo daoInfo) {
        super(genConfig);
        this.mapperXmlInfo = mapperXmlInfo;
        this.daoInfo = daoInfo;
    }

    public void build(Table table) throws IOException {
        File mapperXmlFile = new File(this.mapperXmlInfo.getPath(), this.mapperXmlInfo.getName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        Throwable var4 = null;

        try {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ================可直接使用Base配置文件中定义的节点！================ -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<mapper namespace=\"" + this.daoInfo.getPackageName() + "." + this.daoInfo.getName() + "\">");
            bw.newLine();
            bw.write("  <!-- 请在下方添加自定义配置-->");
            bw.newLine();
            bw.newLine();
            bw.newLine();
            bw.write("</mapper>");
            bw.flush();
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (bw != null) {
                if (var4 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    bw.close();
                }
            }

        }

        log.info("Generate Xml file " + mapperXmlFile.getAbsolutePath());
    }
}
