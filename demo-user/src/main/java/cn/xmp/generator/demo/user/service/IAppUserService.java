package cn.xmp.generator.demo.user.service;

import cn.xmp.generator.demo.user.entity.AppUser;
import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.model.response.PageResponse;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
public interface IAppUserService extends IService<AppUser> {
    public BaseResponse add(AppUser model);

    public BaseResponse delete(AppUser model);

    public BaseResponse update(AppUser model);

    public BaseResponse query(AppUser model);

    public PageResponse page(AppUserPageParam pageParam);

    public BaseResponse login(AppUser model);
}
