package by.siarhei.beerfest.entity;

public class Bar extends Entity {
    private long barId;
    private long accountId;
    private String name;
    private String description;
    private long foodId;
    private String foodName;
    private long beerId;
    private String beerName;
    private int places;

    public Bar() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public long getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public long getBarId() {
        return barId;
    }

    public void setBarId(long barId) {
        this.barId = barId;
    }

    @Override
    public String toString() {
        return String.format(
                "name: %s, description: %s, foodId: %s, beerId: %s, places: %s", name, description, foodId, beerId, places);
    }

}
