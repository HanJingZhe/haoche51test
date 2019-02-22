package com.example.day_test.bean;

/**
 * @author peng_wang
 * @date 2019/2/21
 */
public class AnimalKindBean {

    private String name;
    private String addr;

    @Override
    public String toString() {
        return "AnimalKindBean{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }

    public AnimalKindBean(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
