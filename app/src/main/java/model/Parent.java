package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parent extends Person {

    private Integer idParent;

    public Parent(String name, String phone,Integer idParent) {
        super(name, phone);
        setIdParent(idParent);
    }

    public Parent () {

    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }
}
