package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Strike implements Parcelable{

    private Integer idStrike;
    private String description_strike;
    private String date_strike;

    public Strike () {

    }

    public Strike (String description_strike,String date_strike,Integer idStrike) {
        setDate_strike(date_strike);
        setDescription_strike(description_strike);
        setIdStrike(idStrike);
    }

    private Alumn alumn;

    public Strike(Parcel in) {
        super();
        setIdStrike(in.readInt());

        this.alumn = in.readParcelable(Alumn.class.getClassLoader());
    }

    public Alumn getAlumn() {
        return alumn;
    }

    public void setAlumn(Alumn alumn) {
        this.alumn = alumn;
    }

    public Integer getIdStrike() {
        return idStrike;
    }

    public void setIdStrike(Integer idStrike) {
        this.idStrike = idStrike;
    }

    public String getDescription_strike() {
        return description_strike;
    }

    public void setDescription_strike(String description_strike) {
        this.description_strike = description_strike;
    }

    public String getDate_strike() {
        return date_strike;
    }

    public void setDate_strike(String date_strike) {
        this.date_strike = date_strike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getIdStrike());
        parcel.writeString(getDescription_strike());
        parcel.writeString(getDate_strike());
        parcel.writeParcelable(getAlumn(), flags);
    }

    public static final Creator<Strike> CREATOR = new Creator<Strike>() {
        @Override
        public Strike createFromParcel(Parcel in) {
            return new Strike(in);
        }

        @Override
        public Strike[] newArray(int size) {
            return new Strike[size];
        }
    };
}
