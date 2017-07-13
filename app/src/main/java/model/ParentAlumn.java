package model;

public class ParentAlumn {

    private String nameAlumn;
    private String nameParent;
    private String phoneParent;
    private Integer idStrike;
    private String descriptionStrike;
    private String dateStrike;
    private String title;
    private String description;
    private Integer quantityDays;
    private Integer idSuspension;
    private Integer idNotification;
    private String notificationDate;
    private String notificationText;
    private String dateSuspension;

    public ParentAlumn () {

    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getDescriptionStrike() {
        return descriptionStrike;
    }

    public void setDescriptionStrike(String descriptionStrike) {
        this.descriptionStrike = descriptionStrike;
    }

    public Integer getQuantityDays() {
        return quantityDays;
    }

    public void setQuantityDays(Integer quantityDays) {
        this.quantityDays = quantityDays;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNameAlumn() {
        return nameAlumn;
    }

    public void setNameAlumn(String nameAlumn) {
        this.nameAlumn = nameAlumn;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }

    public String getPhoneParent() {
        return phoneParent;
    }

    public void setPhoneParent(String phoneParent) {
        this.phoneParent = phoneParent;
    }

    public Integer getIdStrike() {
        return idStrike;
    }

    public void setIdStrike(Integer idStrike) {
        this.idStrike = idStrike;
    }

    public String getDateStrike() {
        return dateStrike;
    }

    public void setDateStrike(String dateStrike) {
        this.dateStrike = dateStrike;
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

    public Integer getIdSuspension() {
        return idSuspension;
    }

    public void setIdSuspension(Integer idSuspension) {
        this.idSuspension = idSuspension;
    }

    public String getDateSuspension() {
        return dateSuspension;
    }

    public void setDateSuspension(String dateSuspension) {
        this.dateSuspension = dateSuspension;
    }
}
