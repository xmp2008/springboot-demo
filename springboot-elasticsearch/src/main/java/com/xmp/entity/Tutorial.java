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
public class Tutorial implements Serializable {
    private Long id;
    private String name;//教程名称
}
