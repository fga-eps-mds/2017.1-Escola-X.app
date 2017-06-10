package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alumn extends Person implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getIdAlumn());
        parcel.writeInt(getRegistry());
        parcel.writeString(getName());
    }

    public static final Creator<Alumn> CREATOR = new Creator<Alumn>() {
        @Override
        public Alumn createFromParcel(Parcel in) {
            return new Alumn(in);
        }

        @Override
        public Alumn[] newArray(int size) {
            return new Alumn[size];
        }
    };
}
