//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

import java.util.Arrays;

public class GenParam {
    private String module;
    private String[] tables;

    public GenParam(String module, String[] tables) {
        this.tables = tables;
        this.module = module;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String[] getTables() {
        return this.tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenParam)) {
            return false;
        } else {
            GenParam other = (GenParam) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$module = this.getModule();
                Object other$module = other.getModule();
                if (this$module == null) {
                    if (other$module == null) {
                        return Arrays.deepEquals(this.getTables(), other.getTables());
                    }
                } else if (this$module.equals(other$module)) {
                    return Arrays.deepEquals(this.getTables(), other.getTables());
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GenParam;
    }

    public int hashCode() {
        int result = 1;
        Object $module = this.getModule();
        result = result * 59 + ($module == null ? 43 : $module.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getTables());
        return result;
    }

    public String toString() {
        return "GenParam(module=" + this.getModule() + ", tables=" + Arrays.deepToString(this.getTables()) + ")";
    }
}
