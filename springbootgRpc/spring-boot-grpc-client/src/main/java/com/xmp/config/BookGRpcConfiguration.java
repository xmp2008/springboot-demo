package com.xmp.config;

import grpc.api.BookServiceGrpc;
import io.grpc.ManagedChannel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/6
 */
@Configuration
@EnableAutoConfiguration
public class BookGRpcConfiguration {
    @GrpcClient("spring-boot-grpc-server")
    private ManagedChannel channel;

    @Bean
    public BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub() {
        return BookServiceGrpc.newBlockingStub(channel);
    }
}
