package cn.xmp.generator.demo.user.service.impl;

import cn.xmp.generator.demo.user.entity.AppUser;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.mapper.AppUserMapper;
import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.model.response.PageResponse;
import cn.xmp.generator.demo.user.service.IAppUserService;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import cn.xmp.generator.demo.user.utils.ResponseConvert;
import cn.xmp.generator.demo.user.utils.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {
    public AppUserServiceImpl() {
        super();
    }

    public AppUserServiceImpl(AppUserMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 添加方法
     *
     * @return
     */
    @Override
    public BaseResponse add(AppUser model) {
        boolean back = this.insert(model);
        BaseResponse baseResponse = ResponseConvert.convert(back);
        return baseResponse;
    }

    /**
     * 删除
     *
     * @return
     */
    @Override
    public BaseResponse delete(AppUser model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            boolean back = this.deleteById(model.getId());
            baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
    }

    /**
     * 修改方法
     *
     * @return
     */
    @Override
    public BaseResponse update(AppUser model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            boolean back = this.updateById(model);
            baseResponse = ResponseConvert.convert(back);
        }
        return baseResponse;
    }

    /**
     * 单个查询
     *
     * @return
     */
    @Override
    public BaseResponse query(AppUser model) {
        BaseResponse baseResponse;
        if (null == model || null == model.getId()) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            AppUser data = this.selectById(model.getId());
            if (null != data) {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
                baseResponse.setDataInfo(data);
            } else {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
            }
        }
        return baseResponse;
    }

    /**
     * 用户登录
     *
     * @return
     */
    @Override
    public BaseResponse login(AppUser model) {
        BaseResponse baseResponse;

        if (StringUtil.isEmpty(model.getName()) || StringUtil.isEmpty(model.getPassword())) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            return baseResponse;
        }
        //封装条件
        EntityWrapper<AppUser> ew = new EntityWrapper<AppUser>();
        ew.eq("name", model.getName());
        ew.eq("password", model.getPassword());
        AppUser data = this.selectOne(ew);
        if (null != data) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
            baseResponse.setDataInfo(data);
        } else {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
        }
        return baseResponse;
    }

    /**
     * 条件查询分页列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public PageResponse page(AppUserPageParam pageParam) {
        PageResponse response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1000.getCode());
        Page<AppUser> page = new Page<AppUser>();

        Page<AppUser> pageResponse = null;
        //封装条件
        EntityWrapper<AppUser> ew = new EntityWrapper<AppUser>();

        //针对分页判断
        if (null != pageParam && null != pageParam.getPageNumber() && null != pageParam.getPageSize()) {
            page.setCurrent(pageParam.getPageNumber());
            page.setSize(pageParam.getPageSize());
            pageResponse = this.selectPage(page, ew);
        } else {
            List<AppUser> selectList = this.selectList(ew);
            pageResponse = new Page<>();
            pageResponse.setRecords(selectList);
        }
        //记录数
        pageResponse.setTotal(this.selectCount(ew));
        if (CollectionUtils.isEmpty(pageResponse.getRecords())) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1002.getCode());
        }
        BeanUtils.copyProperties(pageResponse, response);

        return response;
    }
}