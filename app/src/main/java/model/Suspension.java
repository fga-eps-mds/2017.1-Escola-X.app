package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Suspension {

    private String title;
    private String description;
    private Integer quantity_days;
    private Integer idSuspension;

    public Suspension () {

    }

    public Suspension (String title,String description,Integer quantity_days,Integer idSuspension) {
        setTitle(title);
        setDescription(description);
        setQuantity_days(quantity_days);
        setIdSuspension(idSuspension);
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
