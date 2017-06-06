package model;

public class Strike {

    private String description_strike;
    private String date_strike;

    public Strike () {

    }

    public Strike (String description_strike,String date_strike) {
        setDate_strike(date_strike);
        setDescription_strike(description_strike);
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
