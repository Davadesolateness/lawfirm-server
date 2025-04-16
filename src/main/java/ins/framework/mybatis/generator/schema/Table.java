//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ins.framework.mybatis.generator.schema;

import java.util.List;

public class Table {
    private String name;
    private String comment;
    private List<Column> columns;
    private List<PrimaryKey> primaryKeys;

    public Table() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<PrimaryKey> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Table)) {
            return false;
        } else {
            Table other = (Table) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59:
                {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label59;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label59;
                    }

                    return false;
                }

                Object this$comment = this.getComment();
                Object other$comment = other.getComment();
                if (this$comment == null) {
                    if (other$comment != null) {
                        return false;
                    }
                } else if (!this$comment.equals(other$comment)) {
                    return false;
                }

                Object this$columns = this.getColumns();
                Object other$columns = other.getColumns();
                if (this$columns == null) {
                    if (other$columns != null) {
                        return false;
                    }
                } else if (!this$columns.equals(other$columns)) {
                    return false;
                }

                Object this$primaryKeys = this.getPrimaryKeys();
                Object other$primaryKeys = other.getPrimaryKeys();
                if (this$primaryKeys == null) {
                    if (other$primaryKeys != null) {
                        return false;
                    }
                } else if (!this$primaryKeys.equals(other$primaryKeys)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Table;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        Object $columns = this.getColumns();
        result = result * 59 + ($columns == null ? 43 : $columns.hashCode());
        Object $primaryKeys = this.getPrimaryKeys();
        result = result * 59 + ($primaryKeys == null ? 43 : $primaryKeys.hashCode());
        return result;
    }

    public String toString() {
        return "Table(name=" + this.getName() + ", comment=" + this.getComment() + ", columns=" + this.getColumns() + ", primaryKeys=" + this.getPrimaryKeys() + ")";
    }
}
