package com.xmp.service;

import com.xmp.utils.JsonUtil;
import grpc.api.Book;
import grpc.api.BookList;
import grpc.api.BookServiceGrpc;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/6
 */
@Service
@Slf4j
@AllArgsConstructor
public class BookGrpcService {

    private BookServiceGrpc.BookServiceBlockingStub blockingStub;

    public void createBooks(com.xmp.model.Book request) {
        log.info("createBooks request is:{}", JsonUtil.objectToJson(request));

        Book.Builder bookBuilder = grpc.api.Book.newBuilder();
        bookBuilder.setISBN(request.getISBN() == null ? "" : request.getISBN());
        bookBuilder.setAuthor(request.getAuthor() == null ? "" : request.getAuthor());
        bookBuilder.setTitle(request.getTitle() == null ? "" : request.getTitle());

        BookList.Builder bookListBuilder = BookList.newBuilder();
        bookListBuilder.addBook(bookBuilder);
        BookList gRpcResponse = blockingStub.createBooks(bookListBuilder.build());

        log.info("createBooks response is:{}", gRpcResponse);
    }
}
