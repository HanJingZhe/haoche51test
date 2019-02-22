package com.example.day_test.bean;

import java.util.List;

/**
 * @author peng_wang
 * @date 2019/2/21
 */
public class AnimalBean {

    private String name;
    private String action;
    private List<AnimalKindBean> kinds;

    @Override
    public String toString() {
        return "AnimalBean{" +
                "name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", kinds=" + kinds +
                '}';
    }

    public AnimalBean(String name, String action, List<AnimalKindBean> kinds) {
        this.name = name;
        this.action = action;
        this.kinds = kinds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<AnimalKindBean> getKinds() {
        return kinds;
    }

    public void setKinds(List<AnimalKindBean> kinds) {
        this.kinds = kinds;
    }
}
