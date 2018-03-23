package com.xmp.web;

import com.xmp.model.Book;
import com.xmp.service.BookGrpcService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/6
 */
@RestController
@Slf4j
@AllArgsConstructor
@Scope("prototype")
@RequestMapping("/book")
public class BookController {

    private BookGrpcService bookGrpcService;

    @RequestMapping(value = "/createBooks", method = RequestMethod.POST)
    @ResponseBody
    public void createBooks(@RequestBody Book request) {
        bookGrpcService.createBooks(request);
    }
}
