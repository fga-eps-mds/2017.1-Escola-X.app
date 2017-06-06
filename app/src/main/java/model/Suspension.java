package model;

public class Suspension {

    private String title;
    private String description;
    private Integer quantity_days;

    public Suspension (String title,String description,Integer quantity_days) {
        setTitle(title);
        setDescription(description);
        setQuantity_days(quantity_days);
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
