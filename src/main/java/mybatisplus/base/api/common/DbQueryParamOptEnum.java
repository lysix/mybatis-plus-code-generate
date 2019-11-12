package mybatisplus.base.api.common;
/**
 * @ClassName: OrderItemEnum
 * @ProjectName mybatis-plus-code-generator-master
 * @author huangbing
 * @date 2019/9/1820:25
 */

/**
 * 数据库字段排序、like查询等操作类型枚举类
 * @author huangbing
 * @create 2019-09-18 20:25
 * @since 1.0.0
 **/
public enum DbQueryParamOptEnum {

    DESC("desc", "倒序"),  //desc
    ASC("asc", "升序"),  //asc
    LIKE("like", "模糊匹配查找"),  //asc
    ;

    private DbQueryParamOptEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
