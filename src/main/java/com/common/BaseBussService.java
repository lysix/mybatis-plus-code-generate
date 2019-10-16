package com.common;
/**
 * @ClassName: BaseBusinessInnerService
 * @ProjectName dcp
 * @author huangbing
 * @date 2019/10/1411:49
 */

import java.util.List;

/**
 * 业务服务类的基础公共方法
 * <code>
 *     <T extends BaseModel,K extends BaseEntity>
 *     样例：<DcpVariablePoolModel, DcpVariablePoolDto>
 * </code>
 * @author huangbing
 * @create 2019-10-14 11:49
 * @since 1.0.0
 **/
public interface BaseBussService<T,K> {

    /**
     * 数据表model对象转dto对象
     * @param records  List<T>
     * @return  List<K>
     */
    public List<K> getDtosByModel(List<T> records) throws InstantiationException, IllegalAccessException;

    /**
     * 数据表dto对象转model对象
     * @param records  List<K>
     * @return  List<K>
     */
    public List<T> getModelByDto(List<K> records) throws InstantiationException, IllegalAccessException;

    /**
     * 转换dto对象为Model
     * @param dto
     * @return T
     */
    public T getModelByDto(K dto);

    /**
     * 转换dto对象为Model
     * @param model
     * @return K
     */
    public K getDtoByModel(T model);

    /**
     * 根据{@link QueryParam}查询dto对象数据
     * @param param QueryParam
     * @return {@link PageBean<K>}
     */
    public PageBean<K> selectPageByQueryParam(QueryParam param);

    /**
     * @Description 条件查询
     * @Param {@link QueryParam}
     * @return List<K>
     **/
    public List<K> selectByQueryParam(QueryParam param);

}
