package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alumn extends Person {

    private Integer idAlumn;

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
