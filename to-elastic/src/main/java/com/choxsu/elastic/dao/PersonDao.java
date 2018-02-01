package com.choxsu.elastic.dao;

import com.choxsu.elastic.entity.Person;

import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/21 11:33
 */
public interface PersonDao {

    String save(Person person);

    Map butchSave(List list);

    String update(Person person);

    String deltele(String id);

    Object find(String id);

    Object query(Person person, int page, int size);
}