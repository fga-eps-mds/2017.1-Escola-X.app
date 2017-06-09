package model;

public class Alumn extends Person {

    private Integer idAlumn;

    public Alumn(String name,Integer registry,Integer idAlumn) {
        super(name,registry);
        setIdAlumn(idAlumn);
    }

    public Alumn () {

    }

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
        this.idAlumn = idAlumn;
    }
}
