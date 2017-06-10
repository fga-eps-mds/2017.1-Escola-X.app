package model;

public class Notification {

    private String notification_text;
    private String title;
    private String motive;
    private String notificaton_date;
    private Integer idNotification;

    public Notification () {

    }

    public Notification (String notification_text,String title,String motive,
                         String notificaton_date,Integer idNotification) {
        setIdNotification(idNotification);
        setMotive(motive);
        setNotification_text(notification_text);
        setNotificaton_date(notificaton_date);
        setTitle(title);
    }

    public String getNotification_text() {
        return notification_text;
    }

    public void setNotification_text(String notification_text) {
        this.notification_text = notification_text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getNotificaton_date() {
        return notificaton_date;
    }

    public void setNotificaton_date(String notificaton_date) {
        this.notificaton_date = notificaton_date;
    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }
}
