package cn.xmp.generator.demo.user.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */

@Getter
@Setter
@ToString(callSuper = true)
public class AppUserRequest  {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 姓名
     */
	private String name;
    /**
     * 昵称
     */
	private String nickName;
    /**
     * 密码
     */
	private String password;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 创建人
     */
	private Long createdBy;
    /**
     * 创建时间
     */
	private Long createdTime;
    /**
     * 更新人
     */
	private Long updatedBy;
    /**
     * 更新时间
     */
	private Long updatedTime;
    /**
     * 删除标识(1在线2删除)
     */
	private Integer deleted;


}
