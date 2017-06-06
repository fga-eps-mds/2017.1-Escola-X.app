package model;

public class Parent extends Person{

    private String cpfParent;

    public Parent (String name,String address,String phone,String gender,String birthDate,
                   String cpfParent) {
        super(name,address,phone,gender,birthDate);
        setCpfParent(cpfParent);
    }

    public String getCpfParent() {
        return cpfParent;
    }

    public void setCpfParent(String cpfParent) {
        this.cpfParent = cpfParent;
    }
}
