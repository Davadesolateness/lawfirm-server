//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.schema;

public class Column {
    private String name;
    private String type;
    private int size;
    private String defaultValue;
    private String comment;
    private boolean nullable;

    public Column() {
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public int getSize() {
        return this.size;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getComment() {
        return this.comment;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Column)) {
            return false;
        } else {
            Column other = (Column) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                if (this.getSize() != other.getSize()) {
                    return false;
                } else {
                    Object this$defaultValue = this.getDefaultValue();
                    Object other$defaultValue = other.getDefaultValue();
                    if (this$defaultValue == null) {
                        if (other$defaultValue != null) {
                            return false;
                        }
                    } else if (!this$defaultValue.equals(other$defaultValue)) {
                        return false;
                    }

                    label45:
                    {
                        Object this$comment = this.getComment();
                        Object other$comment = other.getComment();
                        if (this$comment == null) {
                            if (other$comment == null) {
                                break label45;
                            }
                        } else if (this$comment.equals(other$comment)) {
                            break label45;
                        }

                        return false;
                    }

                    if (this.isNullable() != other.isNullable()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Column;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        result = result * 59 + this.getSize();
        Object $defaultValue = this.getDefaultValue();
        result = result * 59 + ($defaultValue == null ? 43 : $defaultValue.hashCode());
        Object $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        result = result * 59 + (this.isNullable() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "Column(name=" + this.getName() + ", type=" + this.getType() + ", size=" + this.getSize() + ", defaultValue=" + this.getDefaultValue() + ", comment=" + this.getComment() + ", nullable=" + this.isNullable() + ")";
    }
}
