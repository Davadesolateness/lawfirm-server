package ins.framework.mybatis;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import java.util.Collection;

public class Page<E> extends PageList<E> {
    private static final long serialVersionUID = 1L;

    public Page() {
    }

    public Page(Collection<? extends E> c) {
        super(c);
    }

    public Page(Collection<? extends E> c, Paginator p) {
        super(c, p);
    }

    public Page(Paginator p) {
        super(p);
    }

    public int getPageSize() {
        Paginator paginator = super.getPaginator();
        return paginator != null ? paginator.getLimit() : 0;
    }

    public int getPageNo() {
        Paginator paginator = super.getPaginator();
        return paginator != null ? paginator.getPage() : 0;
    }

    public int getTotalCount() {
        Paginator paginator = super.getPaginator();
        return paginator != null ? paginator.getTotalCount() : 0;
    }
}
