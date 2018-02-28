package com.xmp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "xmp", type = "news")
public class Article implements Serializable {
    @Id
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    private Date postTime;
    /**
     * 点击率
     */
    private Long clickCount;
    /**
     * 作者
     */
    private Author author;
    /**
     * 所属教程
     */
    private Tutorial tutorial;
}
