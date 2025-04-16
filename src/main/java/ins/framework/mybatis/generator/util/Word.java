//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.util;

import org.apache.commons.lang3.StringUtils;

class Word {
    private String key = "";
    private String name = "";
    private String desc = "";

    public Word(String key, String lineContext) {
        this.name = key;
        if (StringUtils.isNotEmpty(lineContext)) {
            this.desc = lineContext;
        }

        this.key = key.toLowerCase();
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "key=" + this.key + ",name=" + this.name + ",desc=" + this.desc;
    }
}
