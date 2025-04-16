//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.schema;

public class PrimaryKey {
    private String pkName;
    private int keySeq;
    private String columnName;

    public PrimaryKey() {
    }

    public String getPkName() {
        return this.pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public int getKeySeq() {
        return this.keySeq;
    }

    public void setKeySeq(int keySeq) {
        this.keySeq = keySeq;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PrimaryKey)) {
            return false;
        } else {
            PrimaryKey other = (PrimaryKey) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label39:
                {
                    Object this$pkName = this.getPkName();
                    Object other$pkName = other.getPkName();
                    if (this$pkName == null) {
                        if (other$pkName == null) {
                            break label39;
                        }
                    } else if (this$pkName.equals(other$pkName)) {
                        break label39;
                    }

                    return false;
                }

                if (this.getKeySeq() != other.getKeySeq()) {
                    return false;
                } else {
                    Object this$columnName = this.getColumnName();
                    Object other$columnName = other.getColumnName();
                    if (this$columnName == null) {
                        if (other$columnName != null) {
                            return false;
                        }
                    } else if (!this$columnName.equals(other$columnName)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PrimaryKey;
    }

    public int hashCode() {
        int result = 1;
        Object $pkName = this.getPkName();
        result = result * 59 + ($pkName == null ? 43 : $pkName.hashCode());
        result = result * 59 + this.getKeySeq();
        Object $columnName = this.getColumnName();
        result = result * 59 + ($columnName == null ? 43 : $columnName.hashCode());
        return result;
    }

    public String toString() {
        return "PrimaryKey(pkName=" + this.getPkName() + ", keySeq=" + this.getKeySeq() + ", columnName=" + this.getColumnName() + ")";
    }
}
