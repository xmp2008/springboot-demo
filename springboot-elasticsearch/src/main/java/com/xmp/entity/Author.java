package com.xmp.entity;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/2/27
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    /**
     * 作者id
     */
    private Long id;
    /**
     * 作者姓名
     */
    private String name;
    /**
     * 作者简介
     */
    private String remark;
}
