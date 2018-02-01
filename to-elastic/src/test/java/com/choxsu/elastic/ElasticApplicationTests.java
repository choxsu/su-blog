package com.choxsu.elastic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choxsu.elastic.entity.Person;
import com.choxsu.elastic.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    public void testSavePerson() {

        String name = "苏叔";
        String introduce = "宇宙超级无敌帅";
        Person person = new Person(name, 23, "男", new Date(), introduce);

        System.out.println("save:=============================>" + personService.savePerson(person));
    }

    @Test
    public void testUpdatePerson() {
        String name = "靓女";
        int age = 24;
        String sex = "女";
        String introduce = "这是一个非常非常非常非常漂亮的女孩。";
        Date birthday = new Date();
        Person person = new Person(name, age, sex, birthday, introduce);
        person.setId("mjupFmABhhkOZSWoch9i");
        personService.updatePerson(person);
    }

    @Test
    public void testFindPerson() {
        String id = "Yv4yd2AB_3fiaqlI0AgD";
        Object person = personService.findPerson(id);
        System.out.println("find:=================>" + person);
        Person resultPerson = JSONObject.parseObject(JSON.toJSONString(person), Person.class);
        System.out.println(resultPerson.toString());
    }

    @Test
    public void testDelPerson() {
        String id = "mjupFmABhhkOZSWoch9i";
        System.out.println(personService.delPerson(id));
    }

    @Test
    public void testQueryPerson() {
        Person person = new Person();
        person.setName("帅");
        person.setIntroduce("人");
//        person.setAge(27);
        Object obj = personService.queryPerson(person, 0 , 20);
        System.out.println(obj);
    }

}
