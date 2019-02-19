//package cn.xmp.generator.demo.user.service;
//
//import cn.vpclub.moses.core.model.response.BaseResponse;
//import cn.vpclub.moses.core.model.response.PageResponse;
//import cn.xmp.generator.demo.user.entity.AppUser;
//import cn.xmp.generator.demo.user.mapper.AppUserMapper;
//import cn.xmp.generator.demo.user.service.impl.AppUserServiceImpl;
//import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
//import cn.vpclub.moses.tests.TestsBase;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.Assert;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.mock;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//
///**
// * <p>
// *  服务测试类
// * </p>
// *
// * @author xiemopeng
// * @since 2019-01-28
// */
//@SpringBootTest
//@Slf4j
//public class AppUserServiceTests extends TestsBase<IAppUserService> {
//
//    private AppUserMapper baseMapper;
//
//    private AppUser model;
//
//    @Override
//    public IAppUserService preSetUp() {
//        baseMapper = mock(AppUserMapper.class);
//        return new AppUserServiceImpl(baseMapper);
//    }
//
//    @DataProvider(name = "AppUserServiceTests.testAddAppUser")
//    public Object[][] testAppUserDataProvider() throws Exception {
//        Long time = System.currentTimeMillis();
//        AppUser bean = new AppUser();
//
//        AppUser bean1 = (AppUser) BeanUtils.cloneBean(bean);
//
//        return new Object[][]{
//                {1000, "Success", bean},
//                {1000, "Success", bean1},
//        };
//    }
//
//
//    @DataProvider(name = "AppUserServiceTests.testAppUserIdDataProvider")
//    public Object[][] testAppUserIdDataProvider() {
//        AppUser bean1 = new AppUser();
//        bean1.setId(System.currentTimeMillis());
//        AppUser bean3 = new AppUser();
//        return new Object[][]{
//                {1000, "Success", bean1, bean1},
//                {1006, "参数不全", bean3, bean3},
//        };
//    }
//
//    @DataProvider(name = "AppUserServiceTests.testUpdateAppUserDataProvider")
//    public Object[][] testUpdateAppUserDataProvider() {
//        AppUser bean1 = new AppUser();
//        bean1.setId(System.currentTimeMillis());
//        AppUser bean3 = new AppUser();
//        return new Object[][]{
//                {1000, "Success", bean1, 1},
//                {1006, "参数不全", bean3, 0},
//        };
//    }
//
//    @DataProvider(name = "AppUserServiceTests.testAppUserListDataProvider")
//    public Object[][] testAppUserListDataProvider() {
//        ArrayList<AppUser> list = new ArrayList<>();
//        AppUser data = null;
//        for (int i = 0; i < 10; i++) {
//            data = new AppUser();
//            data.setId(Long.valueOf(i + ""));
//            list.add(data);
//        }
//        return new Object[][]{
//                {true, "Success", new AppUserPageParam(), list},
//        };
//    }
//
//    // Step 6: the final step is the test case
//    @Test(dataProvider = "AppUserServiceTests.testAddAppUser")
//    public void testAddAppUser(Integer code, String message, AppUser request) {
//        // 6.1
//        when(baseMapper.insert(any())).thenReturn(1);
//        log.info("testAddAppUser pagerequest is " + request);
//        // 6.2
//        BaseResponse response = testObject.add(request);
//        // 6.3
//        log.info("calling add() return: " + response.getMessage());
//        Assert.assertTrue(code == (response.getReturnCode().intValue()));
//    }
//
//    @Test(dataProvider = "AppUserServiceTests.testAppUserIdDataProvider")
//    public void testQueryAppUser(Integer code, String message, AppUser req, AppUser back) {
//        // 6.1 c
//        when(baseMapper.selectById(any())).thenReturn(back);
//        log.info("testQueryAppUser request is " + req);
//        // 6.2
//        BaseResponse response = testObject.query(req);
//        // 6.3
//        log.info("calling query() return: " + response.getMessage());
//        Assert.assertTrue(code == (response.getReturnCode().intValue()));
//    }
//
//    @Test(dataProvider = "AppUserServiceTests.testUpdateAppUserDataProvider")
//    public void testUpdateAppUser(Integer code, String message, AppUser req, Integer back) {
//        // 6.1
//        when(baseMapper.updateById(any())).thenReturn(back);
//        log.debug("testUpdateAppUser request is " + req);
//        // 6.2
//        BaseResponse response = testObject.update(req);
//        // 6.3
//        log.debug("calling update() return: " + response.getMessage());
//        Assert.assertTrue(code == (response.getReturnCode().intValue()));
//    }
//
//    @Test(dataProvider = "AppUserServiceTests.testUpdateAppUserDataProvider")
//    public void testDeleteAppUser(Integer code, String message, AppUser request, Integer back) {
//        // 6.1
//        when(baseMapper.deleteById(any())).thenReturn(back);
//        log.info("testDeleteAppUser request is " + request);
//        // 6.2
//        BaseResponse response = testObject.delete(request);
//        // 6.3
//        log.info("calling delete() return: " + response.getMessage());
//        Assert.assertTrue(code == (response.getReturnCode().intValue()));
//    }
//
//    @Test(dataProvider = "AppUserServiceTests.testAppUserListDataProvider", dependsOnMethods = "testAddAppUser")
//    public void testListAppUser(Boolean code, String message, AppUserPageParam pageParam, ArrayList<AppUser> list) {
//
//        when(this.baseMapper.selectPage(any(), any())).thenReturn(list);
//        when(this.baseMapper.selectList(any())).thenReturn(list);
//        log.info("testListAppUser page request is " + pageParam);
//        // 6.2
//        PageResponse response = testObject.page(pageParam);
//        //add crud 参数
//        if (null != response && CollectionUtils.isNotEmpty(response.getRecords())) {
//            List<AppUser> models = response.getRecords();
//            model = models.get(0);
//        }
//        // 6.3
//        log.info("calling selectByPageParam() return: " + response);
//        Assert.assertTrue(code == (CollectionUtils.isNotEmpty(response.getRecords())));
//    }
//}
