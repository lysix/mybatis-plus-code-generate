package com.common;

/**
 * @ClassName: BaseBusinessInnerServiceImpl
 * @ProjectName dcp
 * @author huangbing
 * @date 2019/10/1411:59
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.smy.framework.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务服务类的基础公共方法
 * @author huangbing
 * @create 2019-10-14 11:59
 * @since 1.0.0
 **/
@Slf4j
public abstract class BaseBussServiceImpl<T extends BaseModel,K extends BaseEntity> implements BaseBussService {
    /**
     * 数据表model对象转dto对象
     * @param records  List<T>
     * @return  List<K>
     */
    @Override
    public List<K> getDtosByModel(List records) {
        List<K> desList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(records)) {
            int recordLength = records.size();
            for (int i = 0; i < recordLength; i++) {
                T sourceModel = (T) records.get(i);
                K dto = (K)GenericsUtils.newObject(this.getClass(),1);
                // bean 属性对象
                BeanUtils.copyProperties(sourceModel, dto);
                desList.add(dto);
            }
        }
        return desList;
    }


    /**
     * 数据表dto对象转model对象
     * @param records  List<K>
     * @return  List<K>
     */
    @Override
    public List<T> getModelByDto(List records) {
        List<T> desList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(records)) {
            int recordLength = records.size();
            for (int i = 0; i < recordLength; i++) {
                K sourceModel = (K) records.get(i);
                T dto = (T)GenericsUtils.newObject(this.getClass(),0);
                // bean 属性对象
                BeanUtils.copyProperties(sourceModel, dto);
                desList.add(dto);
            }
        }

        return desList;
    }

    /**
     * 转换dto对象为Model
     * @param dto
     * @return T
     */
    @Override
    public Object  getModelByDto(Object dto){
        if (null == dto){
            log.info("转换dto对象为空！");
            return null;
        }

        try {
            T model = (T)GenericsUtils.newObject(this.getClass(),0);
            BeanUtils.copyProperties(dto,model);
            return model;
        }catch (Exception e){
            log.error("转换dto="+dto+"变量池对象出现异常！异常信息=" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 转换dto对象为Model
     * @param model
     * @return K
     */
    @Override
    public  Object getDtoByModel(Object model){
        if (null == model){
            log.info("转换model对象为空！");
            return null;
        }

        try {
            K dto = (K)GenericsUtils.newObject(this.getClass(),1);
            BeanUtils.copyProperties(model,dto);
            return dto;
        }catch (Exception e){
            log.error("转换model="+model+"变量池对象出现异常！异常信息=" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 组装页面查询响应对象
     * @param param QueryParam对象
     * @param iPage IPage对象
     * @return PageBean
     */
    protected PageBean<K> getPageBean(QueryParam param, IPage<T> iPage) {
        List<K> desList = null;
        try {
            desList = getDtosByModel(iPage.getRecords());
        } catch (Exception e) {
            log.error("查询参数转换异常，param="+param,e);
        }

        return makePageBean((int) param.getPageNo(),(int) param.getPageSize(),iPage.getTotal(), desList);
    }

    /**
     * 组装页面查询响应对象
     * @param current  当前页码
     * @param pageSize  当前页最大查询数量
     * @param total  数据总量
     * @param desList 当前查询数据集合
     * @return  PageBean<K>
     */
    private PageBean<K> makePageBean(int current,int pageSize, long total,List<K> desList) {
        PageBean<K> pageBean = new PageBean<>();
        pageBean.setList(desList);
        pageBean.setPageNo(current);
        pageBean.setPageSize(pageSize);
        pageBean.setCount(total);
        return pageBean;
    }


}
