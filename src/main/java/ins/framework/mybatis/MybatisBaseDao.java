package ins.framework.mybatis;

import java.util.List;

public interface MybatisBaseDao<T, I> {
    int insert(T var1);

    int insertSelective(T var1);

    int deleteByPrimaryKey(I var1);

    int deleteBatchByPrimaryKeys(List<I> var1);

    int updateByPrimaryKey(T var1);

    int updateSelectiveByPrimaryKey(T var1);

    T selectByPrimaryKey(I var1);

    List<T> selectBatchByPrimaryKeys(List<I> var1);

    Page<T> selectPage(PageParam var1, T var2);
}
