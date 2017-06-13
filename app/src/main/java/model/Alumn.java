package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alumn extends Person {

    private Integer idAlumn;

    public Alumn(String name,Integer registry,Integer idAlumn) {
        super(name,registry);
        setIdAlumn(idAlumn);
    }

    public Alumn () {

    }

    protected Alumn(Parcel in) {
        super();
        setIdAlumn(in.readInt());
    }

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
        this.idAlumn = idAlumn;
    }
}
