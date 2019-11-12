package mybatisplus.base.dto;

/**
 * @ClassName: BaseEntity
 * @ProjectName mybatis-plus-code-generate
 * @author huangbing
 * @date 2019/11/1220:53
 */

import lombok.Data;

import java.io.Serializable;

/**
 * dto基础类
 * @author huangbing
 * @create 2019-11-12
 * @since 1.0.0
 **/
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2249325746951841661L;

    private String simpleName = this.getClass().getSimpleName();

}
