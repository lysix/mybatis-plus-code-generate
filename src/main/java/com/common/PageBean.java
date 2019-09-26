package com.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuyong
 * @ClassName: PageBean
 * @ProjectName mybatis-plus-code-generator-master
 * @date 2019/9/1723:19
 */
@Data
public class PageBean<T> implements Serializable {

    private int pageNo;
    private int pageSize;
    private long count;
    private List<T> list;
    private String message;

}
