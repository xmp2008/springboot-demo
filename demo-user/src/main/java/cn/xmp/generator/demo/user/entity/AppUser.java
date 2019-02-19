package cn.xmp.generator.demo.user.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@TableName("app_user")
@ToString(callSuper = true)
public class AppUser extends Model<AppUser> {

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
	@TableField("nick_name")
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
	@TableField("created_by")
	private Long createdBy;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Long createdTime;
    /**
     * 更新人
     */
	@TableField("updated_by")
	private Long updatedBy;
    /**
     * 更新时间
     */
	@TableField("updated_time")
	private Long updatedTime;
    /**
     * 删除标识(1在线2删除)
     */
	private Integer deleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
