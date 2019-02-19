//package cn.xmp.generator.demo.user.app;
//
//import cn.xmp.generator.demo.user.SaApplication;
//import cn.xmp.generator.demo.user.request.AppUserRequest;
//import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
//import cn.vpclub.moses.tests.PostUtilTests;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.springframework.web.context.WebApplicationContext;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
///**
// * Created by chenwei
// */
//@SpringBootTest(classes = {SaApplication.class})
//@Slf4j
//public class AppUserIntegrationTests extends AbstractTestNGSpringContextTests {
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeMethod
//    public void setUp() {
//        RestAssuredMockMvc.webAppContextSetup(context);
//    }
//
//    private Long id = System.currentTimeMillis();
//
//    @DataProvider(name = "AppUserIntegrationTests.testAddAppUserData")
//    public Object[][] testAppUserAddDataProvider() throws Exception {
//        AppUserRequest bean = new AppUserRequest();
//        bean.setId(id);
//        return new Object[][]{
//                {1000, "成功", bean},
//        };
//    }
//
//    @DataProvider(name = "AppUserIntegrationTests.testAppUserData")
//    public Object[][] testAppUserDataProvider() throws Exception {
//        AppUserRequest bean = new AppUserRequest();
//        bean.setId(id);
//        return new Object[][]{
//                {1000, "成功", bean},
//        };
//    }
//
//
//    @Test(dataProvider = "AppUserIntegrationTests.testAppUserData")
//    public void testQueryAppUser(Integer code, String message, AppUserRequest request) {
//        String uri = "/appUser/query";
//        PostUtilTests.post(code, request, uri);
//    }
//
//
//    @DataProvider(name = "AppUserIntegrationTests.testPageAppUserData")
//    public Object[][] testAppUserListDataProvider() {
//        return new Object[][]{
//                {1000, "Success", new AppUserPageParam()},
//        };
//    }
//
//    @Test(dataProvider = "AppUserIntegrationTests.testPageAppUserData")
//    public void testPageAppUser(Integer code, String message, AppUserPageParam request) {
//        String uri = "/appUser/page";
//        PostUtilTests.postList(code, request, uri);
//    }
//
//}
