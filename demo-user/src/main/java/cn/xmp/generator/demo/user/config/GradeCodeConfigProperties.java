package cn.xmp.generator.demo.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/1/9
 */
@Component
@ConfigurationProperties(prefix = "gradeCode")
public class GradeCodeConfigProperties {

    private List<String> lists;

    /**
     * 设置 lists
     *
     * @param lists
     */
    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    /**
     * @return 获取 lists
     */
    public List<String> getLists() {
        return lists;
    }

    /**
     * @return
     * @see [说明这个方法]
     */
    @Override
    public String toString() {
        return "ConfigProperties [ lists=" + lists + "]";
    }

}