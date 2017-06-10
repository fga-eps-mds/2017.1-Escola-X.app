package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Suspension implements Parcelable {

    private String title;
    private String description;
    private Integer quantity_days;
    private Integer idSuspension;

    private Alumn alumn;

    public Suspension () {

    }

    public Suspension (String title,String description,Integer quantity_days,Integer idSuspension) {
        setTitle(title);
        setDescription(description);
        setQuantity_days(quantity_days);
        setIdSuspension(idSuspension);
    }

    protected Suspension(Parcel in) {
        super();
        setIdSuspension(in.readInt());

        this.alumn = in.readParcelable(Alumn.class.getClassLoader());
    }

    public Alumn getAlumn() {
        return alumn;
    }

    public void setAlumn(Alumn alumn) {
        this.alumn = alumn;
    }

    public Integer getIdSuspension() {
        return idSuspension;
    }

    public void setIdSuspension(Integer idSuspension) {
        this.idSuspension = idSuspension;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity_days() {
        return quantity_days;
    }

    public void setQuantity_days(Integer quantity_days) {
        this.quantity_days = quantity_days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getIdSuspension());
        parcel.writeString(getTitle());
        parcel.writeString(getDescription());
        parcel.writeInt(getQuantity_days());
        parcel.writeParcelable(getAlumn(), flags);
    }

    public static final Creator<Suspension> CREATOR = new Creator<Suspension>() {
        @Override
        public Suspension createFromParcel(Parcel in) {
            return new Suspension(in);
        }

        @Override
        public Suspension[] newArray(int size) {
            return new Suspension[size];
        }
    };
}
