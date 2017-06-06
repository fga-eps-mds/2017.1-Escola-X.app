package model;

public class Alumn extends Person {

    private Integer idAlumn;

    public Alumn(String name, String phone, String registry,Integer idAlumn) {
        super(name, phone, registry);
        setIdAlumn(idAlumn);
    }

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
        this.idAlumn = idAlumn;
    }
}
