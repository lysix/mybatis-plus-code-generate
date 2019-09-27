package com.common;

import com.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询参数
 * @author
 * @since 2018-11-08
 */
@Data
//@ApiModel("查询参数对象")
public abstract class QueryParam implements Serializable{
    private static final long serialVersionUID = -3263921252635611410L;

//    @ApiModelProperty(value = "页码,默认为1")
	private Integer pageNo = CommonConstant.DEFAULT_PAGE_INDEX;
//	@ApiModelProperty(value = "页大小,默认为10")
	private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;

    /**  完全相等 */
    private Map<String,Object> queryEqParamMap;

    /** 查询时间字段的名称，默认为 “create_date ” */
    private String dateFieldName;
    /**  开始时间  */
    private Date startDate;
    /**  结束时间  */
    private Date endDate;

    /**  like匹配 */
    private Map<String,String> queryLikeParamMap;

    // 升序排序
    private List<String> ascColumn;

    // 倒序排序
    private List<String> descColumn;

    public void setDateFieldName(String dateFieldName) {
        if (dateFieldName == null || "".equals(dateFieldName)){
            this.dateFieldName = CommonConstant.DEFAULT_DATE_FIELD_NAME;
        }else{
            this.dateFieldName = dateFieldName;
        }
    }

    public void setPageNo(Integer pageNo) {
	    if (pageNo == null || pageNo <= 0){
	        this.pageNo = CommonConstant.DEFAULT_PAGE_INDEX;
        }else{
            this.pageNo = pageNo;
        }
    }

    public void setPageSize(Integer pageSize) {
	    if (pageSize == null || pageSize <= 0){
	        this.pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        }else{
            this.pageSize = pageSize;
        }
    }

}
