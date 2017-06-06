package model;

public class Alumn extends Person {

    private String registry;
    private String shift;

    public Alumn (String name,String address,String phone,String gender,String birthDate,
                  String registry,String shift) {
        super(name,address,phone,gender,birthDate);
        setRegistry(registry);
        setShift(shift);
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
