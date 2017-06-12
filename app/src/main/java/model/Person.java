package model;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private Integer registry;
    private String phone;
    private Integer idPerson;

    public Person () {

    }

    public Person (String name,Integer registry) {
        setName(name);
        setRegistry(registry);
    }

    public Person (String name,String phone) {
        setName(name);
        setPhone(phone);
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRegistry() {
        return registry;
    }

    public void setRegistry(Integer registry) {
        this.registry = registry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
