package model;

public abstract class Person {

    private String name;
    private String phone;
    private String registry;

    public Person (String name,String phone,String registry) {
        setName(name);
        setPhone(phone);
        setRegistry(registry);
    }

    public Person (String name,String phone) {
        setName(name);
        setPhone(phone);
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

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
}
