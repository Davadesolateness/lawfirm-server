//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.schema;

public class Database {
    private String productName;
    private String productVersion;

    public Database() {
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Database)) {
            return false;
        } else {
            Database other = (Database) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$productName = this.getProductName();
                Object other$productName = other.getProductName();
                if (this$productName == null) {
                    if (other$productName != null) {
                        return false;
                    }
                } else if (!this$productName.equals(other$productName)) {
                    return false;
                }

                Object this$productVersion = this.getProductVersion();
                Object other$productVersion = other.getProductVersion();
                if (this$productVersion == null) {
                    if (other$productVersion != null) {
                        return false;
                    }
                } else if (!this$productVersion.equals(other$productVersion)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Database;
    }

    public int hashCode() {
        int result = 1;
        Object $productName = this.getProductName();
        result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
        Object $productVersion = this.getProductVersion();
        result = result * 59 + ($productVersion == null ? 43 : $productVersion.hashCode());
        return result;
    }

    public String toString() {
        return "Database(productName=" + this.getProductName() + ", productVersion=" + this.getProductVersion() + ")";
    }
}
