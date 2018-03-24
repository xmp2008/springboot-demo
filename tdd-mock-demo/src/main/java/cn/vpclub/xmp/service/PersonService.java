package cn.vpclub.xmp.service;

import cn.vpclub.xmp.dao.PersonDao;
import cn.vpclub.xmp.entity.Person;
import lombok.AllArgsConstructor;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/24
 */
@AllArgsConstructor
public class PersonService {
    private final PersonDao personDao;

    public boolean update(Long id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null) {
            return false;
        }

        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }
}
