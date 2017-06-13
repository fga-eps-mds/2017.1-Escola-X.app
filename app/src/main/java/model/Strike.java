package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Strike {

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
}
