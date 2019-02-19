//package cn.xmp.generator.demo.user.app;
//
//import cn.xmp.generator.demo.user.SaApplication;
//import cn.xmp.generator.demo.user.api.AppUserProto.*;
//import cn.xmp.generator.demo.user.api.AppUserServiceGrpc;
//import cn.xmp.generator.demo.user.entity.AppUser;
//import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
//import cn.xmp.generator.demo.user.rpc.AppUserRpcService;
//import cn.vpclub.moses.core.enums.ReturnCodeEnum;
//import cn.vpclub.moses.tests.GRpcServerIntegrationTestsBase;
//import cn.vpclub.moses.core.model.response.PageResponse;
//import io.grpc.BindableService;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.Assert;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import java.util.List;
//import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.fromGRpcMessage;
//import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.toGRpcMessage;
//
///**
// * <p>
// *  集成测试类
// * </p>
// *
// * @author xiemopeng
// * @since 2019-01-28
// */
//// Step 1: add @SpringBootTest and annotation
//@SpringBootTest(classes = {SaApplication.class})
//@Slf4j
//public class AppUserRpcIntegrationTests extends GRpcServerIntegrationTestsBase {
//
//    // Step 2: define mock service and grpc stub
//    @Autowired
//    private AppUserRpcService rpc;
//
//    private AppUserServiceGrpc.AppUserServiceBlockingStub blockingStub;
//
//    private AppUser model;
//
//    // Step 3: pre-setup
//    @Override
//    public BindableService preSetUp() {
//        log.info("preSetup is called ...");
//        return rpc;
//    }
//
//    // Step 4: post-setup
//    @Override
//    public void postSetUp() {
//        blockingStub = AppUserServiceGrpc.newBlockingStub(inProcessChannel).withInterceptors(clientInterceptor);
//    }
//
//    // Step 5: add dataprovider provider, this approach is to add multiple test cases with two simple methods only
//    @DataProvider(name = "AppUserIntegrationTests.testAppUserDataProvider")
//    public Object[][] testAppUserDataProvider() throws Exception {
//        AppUser bean = new AppUser();
//        AppUser bean1 = new AppUser();
//
//        return new Object[][]{
//                {1000, "Success", bean},
//                {1000, "Success", bean1},
//        };
//    }
//
//    @Test(dataProvider = "AppUserIntegrationTests.testAppUserDataProvider")
//    public void testAddAppUser(Integer code, String message, AppUser param) {
//        // 6.1 convert AppUserParam to gRpc page AppUser
//        AppUserDTO request = (AppUserDTO) toGRpcMessage(param, AppUserDTO.newBuilder());
//
//        // 6.2 call gRpc server method in client.
//        AppUserResponse response = blockingStub.add(request);
//
//
//
//        // 6.3 verify output
//        Assert.assertTrue(code == response.getReturnCode().getValue());
//    }
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @ToString
//    private class AppUserIdParam {
//        private Long id;
//        private Long updatedTime;
//    }
//
//    @DataProvider(name = "AppUserIntegrationTests.testAppUserIdDataProvider")
//    public Object[][] testAppUserIdDataProvider() {
//
//        return new Object[][]{
//                {1000, "Success", new AppUserIdParam(model.getId(), Long.parseLong(RandomStringUtils.randomNumeric(17)))},
//                {1002, "query: no record was found", new AppUserIdParam(1000L, Long.parseLong(RandomStringUtils.randomNumeric(17)))},
//                {1006, "id: cannot be null", new AppUserIdParam(null, null)},
//        };
//    }
//
//
//    @Test(dataProvider = "AppUserIntegrationTests.testAppUserIdDataProvider", dependsOnMethods = "testListAppUser")
//    public void testQueryAppUser(Integer code, String message, AppUserIdParam idParam) {
//        // 6.1 convert AppUserParam to gRpc page AppUser
//        AppUserDTO request = (AppUserDTO) toGRpcMessage(idParam, AppUserDTO.newBuilder());
//
//        // 6.2 call gRpc server method in client.
//        AppUserResponse response = blockingStub.query(request);
//        log.info("expected: {}, actual: {} ", message, response.getMessage());
//        // 6.3 verify output
//        Assert.assertTrue(code == response.getReturnCode().getValue());
//    }
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @ToString
//    private class UpdateAppUserParam {
//        private Long id;
//        private String name;
//        private Long updatedTime;
//    }
//
//    @DataProvider(name = "AppUserIntegrationTests.testUpdateAppUserDataProvider")
//    public Object[][] testUpdateAppUserDataProvider() {
//
//        String randomAppUser1 = RandomStringUtils.randomAlphabetic(16);
//
//        return new Object[][]{
//                {1000, "Success", new UpdateAppUserParam(model.getId(), randomAppUser1, Long.parseLong(RandomStringUtils.randomNumeric(17)))},
//        };
//    }
//
//    @Test(dataProvider = "AppUserIntegrationTests.testUpdateAppUserDataProvider", dependsOnMethods = "testListAppUser")
//    public void testUpdateAppUser(Integer code, String message, UpdateAppUserParam updateParam) {
//        // 6.1 convert AppUserParam to gRpc page AppUser
//        AppUserDTO request = (AppUserDTO) toGRpcMessage(model, AppUserDTO.newBuilder());
//
//        // 6.2 call gRpc server method in client.
//        AppUserResponse response = blockingStub.update(request);
//        log.info("expected: {}, actual: {} ", message, response.getMessage());
//        // 6.3 verify output
//        Assert.assertTrue(code == response.getReturnCode().getValue());
//    }
//
//
//    @DataProvider(name = "AppUserIntegrationTests.testDeleteAppUserIdDataProvider")
//    public Object[][] testDeleteAppUserIdDataProvider() {
//        return new Object[][]{
//                {1000, "Success", new AppUserIdParam(model.getId(), Long.parseLong(RandomStringUtils.randomNumeric(17)))},
//                {1005, "delete: no record was deleted", new AppUserIdParam(1000L, Long.parseLong(RandomStringUtils.randomNumeric(17)))},
//        };
//    }
//
//    // Step 6: the final step is the test case
//    @Test(dataProvider = "AppUserIntegrationTests.testDeleteAppUserIdDataProvider", dependsOnMethods = "testUpdateAppUser")
//    public void testDeleteAppUser(Integer code, String message, AppUserIdParam idParam) {
//        // 6.1 convert AppUserParam to gRpc page AppUser
//        AppUserDTO request = (AppUserDTO) toGRpcMessage(idParam, AppUserDTO.newBuilder());
//
//        // 6.2 call gRpc server method in client.
//        AppUserResponse response = blockingStub.delete(request);
//        log.info("expected: {}, actual: {} ", message, response.getMessage());
//        // 6.3 verify output
//        Assert.assertTrue(code == response.getReturnCode().getValue());
//    }
//
//    @DataProvider(name = "AppUserIntegrationTests.testAppUserListDataProvider")
//    public Object[][] testAppUserListDataProvider() {
//        return new Object[][]{
//                {1000, "Success", new AppUserPageParam()},
//        };
//    }
//
//    @Test(dataProvider = "AppUserIntegrationTests.testAppUserListDataProvider")
//    public void testListAppUser(Integer code, String message, AppUserPageParam pageParam) {
//        // 6.1 convert AppUserParam to gRpc page AppUser
//        AppUserRequest request = (AppUserRequest) toGRpcMessage(pageParam, AppUserRequest.newBuilder());
//
//        // 6.2 call gRpc server method in client.
//        AppUserPageResponse response = blockingStub.page(request);
//        log.info("expected: {}, actual: {} ", message, response.getMessage());
//
//        // added for delete,update,query AppUser integration test
//        if (ReturnCodeEnum.CODE_1000.getCode().intValue() == response.getReturnCode().getValue()) {
//            PageResponse page = (PageResponse) fromGRpcMessage(response, PageResponse.class, AppUser.class);
//            if (null != page && CollectionUtils.isNotEmpty(page.getRecords())){
//                List<AppUser> models = page.getRecords();
//                model = models.get(0);
//            }
//        }
//
//        // 6.3 verify output
//        Assert.assertTrue(code == response.getReturnCode().getValue());
//    }
//}
