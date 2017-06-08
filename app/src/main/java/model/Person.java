package model;

public abstract class Person {

    private String name;
    private String phone;
    private Integer registry;

    public Person (String name,String phone,Integer registry) {
        setName(name);
        setPhone(phone);
        setRegistry(registry);
    }

    public Person (String name,String phone) {
        setName(name);
        setPhone(phone);
    }

    public Person () {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRegistry() {
        return registry;
    }

    public void setRegistry(Integer registry) {
        this.registry = registry;
    }
}
