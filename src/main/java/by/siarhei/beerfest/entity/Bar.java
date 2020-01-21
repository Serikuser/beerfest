package by.siarhei.beerfest.entity;

public class Bar extends Entity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bar bar = (Bar) o;
        if (accountId != bar.accountId) {
            return false;
        }
        if (foodId != bar.foodId) {
            return false;
        }
        if (beerId != bar.beerId) {
            return false;
        }
        if (places != bar.places) {
            return false;
        }
        if (name != null ? !name.equals(bar.name) : bar.name != null) {
            return false;
        }
        if (description != null ? !description.equals(bar.description) : bar.description != null) {
            return false;
        }
        if (foodName != null ? !foodName.equals(bar.foodName) : bar.foodName != null) {
            return false;
        }
        return beerName != null ? beerName.equals(bar.beerName) : bar.beerName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (foodId ^ (foodId >>> 32));
        result = 31 * result + (foodName != null ? foodName.hashCode() : 0);
        result = 31 * result + (int) (beerId ^ (beerId >>> 32));
        result = 31 * result + (beerName != null ? beerName.hashCode() : 0);
        result = 31 * result + places;
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "name: %s, description: %s, foodId: %s, beerId: %s, places: %s", name, description, foodId, beerId, places);
    }

}
