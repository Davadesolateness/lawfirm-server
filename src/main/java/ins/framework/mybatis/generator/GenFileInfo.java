//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator;

public class GenFileInfo {
    private String name;
    private String packageName;
    private String path;

    public GenFileInfo(String name, String packageName, String path) {
        this.name = name;
        this.packageName = packageName;
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenFileInfo)) {
            return false;
        } else {
            GenFileInfo other = (GenFileInfo) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label47;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label47;
                    }

                    return false;
                }

                Object this$packageName = this.getPackageName();
                Object other$packageName = other.getPackageName();
                if (this$packageName == null) {
                    if (other$packageName != null) {
                        return false;
                    }
                } else if (!this$packageName.equals(other$packageName)) {
                    return false;
                }

                Object this$path = this.getPath();
                Object other$path = other.getPath();
                if (this$path == null) {
                    if (other$path != null) {
                        return false;
                    }
                } else if (!this$path.equals(other$path)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GenFileInfo;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $packageName = this.getPackageName();
        result = result * 59 + ($packageName == null ? 43 : $packageName.hashCode());
        Object $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        return result;
    }

    public String toString() {
        return "GenFileInfo(name=" + this.getName() + ", packageName=" + this.getPackageName() + ", path=" + this.getPath() + ")";
    }
}
