package cn.vpclub.xmp.dao;

import cn.vpclub.xmp.entity.Person;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/24
 */
public interface PersonDao {

    Person getPerson(Long id);

    boolean update(Person person);
}
