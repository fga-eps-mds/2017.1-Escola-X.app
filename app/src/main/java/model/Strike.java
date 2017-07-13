package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Strike {

    private Integer idStrike;
    private String description_strike;
    private String date_strike;
    private Integer idAlumn;

    public Strike () {

    }

    public Integer getIdAlumn() {
        return idAlumn;
    }

    public void setIdAlumn(Integer idAlumn) {
        this.idAlumn = idAlumn;
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
