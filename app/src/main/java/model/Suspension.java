package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Suspension {

    private String title;
    private String description;
    private Integer quantity_days;
    private Integer idSuspension;
    private String dateSuspension;
    private Integer idAlumn;

    public Suspension () {

    }

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
        this.idAlumn = idAlumn;
    }

    public String getDateSuspension() {
        return dateSuspension;
    }

    public void setDateSuspension(String dateSuspension) {
        this.dateSuspension = dateSuspension;
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
}
