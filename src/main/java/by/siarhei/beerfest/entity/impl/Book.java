package by.siarhei.beerfest.entity.impl;


import by.siarhei.beerfest.entity.Entity;

import java.sql.Date;

public class Book extends Entity {
    long userId;
    String userName;
    long barId;
    String barName;
    int places;
    Date date;

    public Book() {
    }

    public long getBookId() {
        return id;
    }

    public void setBookId(long bookId) {
        this.id = bookId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBarId() {
        return barId;
    }

    public void setBarId(long barId) {
        this.barId = barId;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getbarName() {
        return barName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        if (userId != book.userId) {
            return false;
        }
        if (barId != book.barId) {
            return false;
        }
        if (places != book.places) {
            return false;
        }
        return date != null ? date.equals(book.date) : book.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (barId ^ (barId >>> 32));
        result = 31 * result + places;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "user with id: %s, bar with id: %s, reserved places: %s, on date: %s", userId, barId, places, date);
    }


}
