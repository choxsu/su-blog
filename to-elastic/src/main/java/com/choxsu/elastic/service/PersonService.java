package com.choxsu.elastic.service;

import com.choxsu.elastic.dao.PersonDao;
import com.choxsu.elastic.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chox su
 * @date 2017/12/21 11:46
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * 保存person
     * @param person
     * @return
     */
    public String savePerson(Person person) {

        return personDao.save(person);
    }


    public String updatePerson(Person person) {

        return personDao.update(person);
    }

    public Object findPerson(String id) {
        return personDao.find(id);
    }

    public String delPerson(String id) {
        return personDao.deltele(id);
    }

    public Object queryPerson(Person person, int page, int size) {
        return personDao.query(person, page, size);
    }
}
