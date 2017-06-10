package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parent extends Person implements Parcelable{

    private Integer idParent;

    private Alumn alumn;

    public Parent(String name, String phone,Integer idParent) {
        super(name, phone);
        setIdParent(idParent);
    }

    public Parent () {

    }

    protected Parent(Parcel in) {
        super();
        setIdParent(in.readInt());

        this.alumn = in.readParcelable(Alumn.class.getClassLoader());
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    public Alumn getAlumn() {
        return alumn;
    }

    public void setAlumn(Alumn alumn) {
        this.alumn = alumn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getIdParent());
        parcel.writeString(getName());
        parcel.writeString(getPhone());
        parcel.writeParcelable(getAlumn(), flags);
    }

    public static final Creator<Parent> CREATOR = new Creator<Parent>() {
        @Override
        public Parent createFromParcel(Parcel in) {
            return new Parent(in);
        }

        @Override
        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };
}
