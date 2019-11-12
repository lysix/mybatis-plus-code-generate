package mybatisplus.base.core.dbinner.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mybatisplus.base.api.common.DbQueryParamOptEnum;
import mybatisplus.base.api.common.QueryParam;
import mybatisplus.base.core.dbinner.base.BaseService;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author geekidea
 * @date 2018-11-08
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 设置分页查询条件
     * @param queryParam
     * @return  Page 分页对象
     */
    protected Page setPageParam(QueryParam queryParam) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(queryParam.getPageNo());
        // 设置页大小
        page.setSize(queryParam.getPageSize());

        // 升序
        List<OrderItem> ascList = makeOrderParam(queryParam.getAscColumn(), DbQueryParamOptEnum.ASC);
        if (CollectionUtils.isNotEmpty(ascList)) {
            page.setOrders(ascList);
        }

        // 倒序
        List<OrderItem> descOrderList = makeOrderParam(queryParam.getDescColumn(), DbQueryParamOptEnum.DESC);
        if (CollectionUtils.isNotEmpty(descOrderList)) {
            page.setOrders(descOrderList);
        }

        return page;
    }

    /**
     * 组装查询条件
     * @param queryParam QueryParam查询条件对象
     * @return QueryWrapper<T> 查询条件
     */
    protected QueryWrapper<T> setQueryWrapper(QueryParam queryParam) {
        QueryWrapper<T> queryWrapper = null;

        // 完全相等
        Map<String, Object> queryParamMap = queryParam.getQueryEqParamMap();
        if (CollectionUtils.isNotEmpty(queryParamMap)) {
            if(queryWrapper  == null){
                queryWrapper = new QueryWrapper<>();
            }

            queryWrapper.allEq(queryParamMap);
        }

        // like
        Map<String, String> queryLikeParamMap = queryParam.getQueryLikeParamMap();
        if (CollectionUtils.isNotEmpty(queryLikeParamMap)) {
            if(queryWrapper  == null){
                queryWrapper = new QueryWrapper<>();
            }

            for (Map.Entry<String, String> entry : queryLikeParamMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                queryWrapper.like(key, value);
            }
        }

        Date startDate = queryParam.getStartDate();
        Date endDate = queryParam.getEndDate();
        if(startDate != null || endDate!= null){
            queryWrapper.between(queryParam.getDateFieldName(),startDate,endDate);
        }
        //TODO startDate EndDate  其中一个为空

        return queryWrapper;
    }

    private List<OrderItem> makeOrderParam(Object obj, DbQueryParamOptEnum orderEnum) {
        List<OrderItem> orderList = null;

        if (obj instanceof String) {
            String field = String.valueOf(obj);
            if (!StringUtils.isEmpty(field)) {
                if (field.contains("|")) {
                    String[] split = field.split("|");
                    orderList = makeOrderList(orderEnum, Arrays.asList(split));
                } else {
                    orderList = makeOrderList(orderEnum, Arrays.asList(field));
                }
            }
        } else if (obj instanceof List) {
            orderList = makeOrderList(orderEnum, (List<String>) obj);
        } else if (obj instanceof String[]) {
            orderList = makeOrderList(orderEnum, Arrays.asList((String[]) obj));
        }

        return orderList;
    }

    private List<OrderItem> makeOrderList(DbQueryParamOptEnum orderEnum, List<String> split) {
        if (CollectionUtils.isEmpty(split)) {
            return null;
        }

        List<OrderItem> orderList = new ArrayList<>();
        for (String ts : split) {
            OrderItem order = getOrderItem(orderEnum, ts);
            if (order != null) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    private OrderItem getOrderItem(DbQueryParamOptEnum orderEnum, String ts) {
        OrderItem order = null;
        if (!StringUtils.isEmpty(ts)) {
            if (DbQueryParamOptEnum.ASC.equals(orderEnum)) {
                order = OrderItem.asc(ts);
            } else if (DbQueryParamOptEnum.DESC.equals(orderEnum)) {
                order = OrderItem.desc(ts);
            }
        }
        return order;
    }

}
