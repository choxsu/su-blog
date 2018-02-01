package com.choxsu.elastic.entity;

import java.util.Date;

/**
 * @author chox su
 * @date 2017/12/21 11:34
 */
public class Person {

    private String id;

    private String name;

    private Integer age;

    private String sex;

    private Date birthday;

    private String introduce;

    public Person() {
    }

    public Person(String name, Integer age, String sex, Date birthday, String introduce) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.introduce = introduce;
    }

    public Person(String id, String name, Integer age, String sex, Date birthday, String introduce) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.introduce = introduce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
