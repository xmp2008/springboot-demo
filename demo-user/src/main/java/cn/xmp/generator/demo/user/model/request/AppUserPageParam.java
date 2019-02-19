package cn.xmp.generator.demo.user.model.request;

import lombok.Data;

/**
 * <p>
 * 分页查询请求包装类
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@Data
public class AppUserPageParam {
    private Integer pageNumber;
    private Integer pageSize;
}
