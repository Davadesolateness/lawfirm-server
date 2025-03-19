package ins.framework.mybatis;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public class PageParam extends PageBounds {
    private static final long serialVersionUID = 1L;
    private int totalCount;

    public PageParam() {
    }

    public PageParam(RowBounds rowBounds) {
        super(rowBounds);
    }

    public PageParam(int limit) {
        super(limit);
    }

    public PageParam(int page, int limit) {
        super(page, limit);
    }

    public PageParam(int page, int limit, boolean containsTotalCount) {
        super(page, limit, containsTotalCount);
    }

    public PageParam(List<Order> orders) {
        super(orders);
    }

    public PageParam(Order... order) {
        super(order);
    }

    public PageParam(int page, int limit, Order... order) {
        super(page, limit, order);
    }

    public PageParam(int page, int limit, List<Order> orders) {
        super(page, limit, orders);
    }

    public PageParam(int page, int limit, List<Order> orders, boolean containsTotalCount) {
        super(page, limit, orders, containsTotalCount);
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
