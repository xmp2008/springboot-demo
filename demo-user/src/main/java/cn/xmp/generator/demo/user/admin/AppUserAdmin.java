package cn.xmp.generator.demo.user.admin;

import cn.xmp.generator.demo.user.entity.AppUser;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.model.response.PageResponse;
import cn.xmp.generator.demo.user.request.AppUserRequest;
import cn.xmp.generator.demo.user.service.IAppUserService;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/admin/appUser")
public class AppUserAdmin  {


    private IAppUserService appUserService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@RequestBody AppUserRequest request) {
        BaseResponse response;
        //业务操作
        log.info("add AppUser: {}", request);
        try {
        AppUser bean = new AppUser();
        BeanUtils.copyProperties(request, bean);
        response = appUserService.add(bean);
        } catch (Exception e) {
        log.error("error: {}", e);
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        response.setMessage(e.getMessage());
        }
        return response;
        }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse update(@RequestBody AppUserRequest request) {
        BaseResponse response;
        //业务操作
        log.info("update AppUser: {}", request);
        try {
        AppUser bean = new AppUser();
        BeanUtils.copyProperties(request, bean);
        response = appUserService.update(bean);
        } catch (Exception e) {
        log.error("error: {}", e);
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        response.setMessage(e.getMessage());
        }
        return response;
        }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse query(@RequestBody AppUserRequest request) {
        BaseResponse response;
        //业务操作
        log.info("query AppUser : {}", request);
        try {
        AppUser bean = new AppUser();
        BeanUtils.copyProperties(request, bean);
        response = appUserService.query(bean);
        log.info("query AppUser back: {}", response);
        } catch (Exception e) {
        log.error("error: {}", e);
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        response.setMessage(e.getMessage());
        }
        return response;

        }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse delete(@RequestBody AppUserRequest request) {
        BaseResponse response;
        //业务操作
        log.info("delete AppUser : {}", request);
        try {
        AppUser bean = new AppUser();
        BeanUtils.copyProperties(request, bean);
        response = appUserService.delete(bean);
        log.info("delete AppUser back: {}", response);
        } catch (Exception e) {
        log.error("error: {}", e);
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        response.setMessage(e.getMessage());
        }
        return response;
        }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse page(@RequestBody AppUserPageParam request) {
        PageResponse response;
        try {
        //业务操作
        log.info("AppUser page method request : {}", request);
        response = appUserService.page(request);
        } catch (Exception e) {
        log.error("error: {}", e);
        response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        response.setMessage(e.getMessage());
        }
        return response;
    }
    }
