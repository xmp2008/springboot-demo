//package cn.xmp.generator.demo.user.rpc;
//import io.grpc.stub.StreamObserver;
//import lombok.extern.slf4j.Slf4j;
//import cn.vpclub.spring.boot.grpc.annotations.GRpcService;
//import cn.vpclub.moses.core.model.response.BaseResponse;
//import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.fromGRpcMessage;
//import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.toGRpcMessage;
//import cn.vpclub.moses.core.model.response.PageResponse;
//import cn.xmp.generator.demo.user.api.AppUserProto;
//import cn.xmp.generator.demo.user.api.AppUserProto.AppUserResponse;
//import cn.xmp.generator.demo.user.api.AppUserProto.AppUserPageResponse;
//import cn.xmp.generator.demo.user.api.AppUserServiceGrpc;
//import cn.xmp.generator.demo.user.entity.AppUser;
//import cn.xmp.generator.demo.user.model.request.AppUserPageParam;
//import cn.xmp.generator.demo.user.service.IAppUserService;
//
///**
// * <p>
// *  rpc层数据传输
// * </p>
// *
// * @author xiemopeng
// * @since 2019-01-28
// */
//@Slf4j
//@GRpcService
//public class AppUserRpcService extends AppUserServiceGrpc.AppUserServiceImplBase {
//
//    private IAppUserService service;
//
//    public AppUserRpcService(IAppUserService service) {
//        this.service = service;
//    }
//
//    @Override
//    public void add(AppUserProto.AppUserDTO request, StreamObserver<AppUserProto.AppUserResponse> responseObserver) {
//        AppUser model = (AppUser) fromGRpcMessage(request, AppUser.class);
//        BaseResponse baseResponse = service.add(model);
//        AppUserResponse response = (AppUserResponse) toGRpcMessage(baseResponse, AppUserResponse.newBuilder());
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void query(AppUserProto.AppUserDTO request, StreamObserver<AppUserProto.AppUserResponse> responseObserver) {
//        AppUser model = (AppUser) fromGRpcMessage(request, AppUser.class);
//        BaseResponse baseResponse = service.query(model);
//        AppUserResponse response = (AppUserResponse) toGRpcMessage(baseResponse, AppUserResponse.newBuilder());
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void update(AppUserProto.AppUserDTO request, StreamObserver<AppUserProto.AppUserResponse> responseObserver) {
//        AppUser model = (AppUser) fromGRpcMessage(request, AppUser.class);
//        BaseResponse baseResponse = service.update(model);
//        AppUserResponse response = (AppUserResponse) toGRpcMessage(baseResponse, AppUserResponse.newBuilder());
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void delete(AppUserProto.AppUserDTO request, StreamObserver<AppUserProto.AppUserResponse> responseObserver) {
//        //业务操作
//        AppUser model = (AppUser) fromGRpcMessage(request, AppUser.class);
//        BaseResponse baseResponse = service.delete(model);
//        AppUserResponse response = (AppUserResponse) toGRpcMessage(baseResponse, AppUserResponse.newBuilder());
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void page(AppUserProto.AppUserRequest request, StreamObserver<AppUserProto.AppUserPageResponse> responseObserver) {
//        AppUserPageResponse response;
//        AppUserPageParam pageParam = (AppUserPageParam) fromGRpcMessage(request, AppUserPageParam.class);
//
//        PageResponse<AppUser> selectPage = service.page(pageParam);
//        response = (AppUserPageResponse) toGRpcMessage(selectPage, AppUserPageResponse.newBuilder());
//        //back
//        log.debug("page select back : {}",response);
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
