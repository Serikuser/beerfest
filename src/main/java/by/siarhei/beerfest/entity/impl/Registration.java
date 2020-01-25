package by.siarhei.beerfest.entity.impl;

import by.siarhei.beerfest.entity.Entity;

public class Registration extends Entity {
    long accountId;
    String token;
    boolean expired;
    long date;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Registration that = (Registration) o;

        if (accountId != that.accountId) {
            return false;
        }
        if (expired != that.expired) {
            return false;
        }
        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expired ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("token: %s is expired: %s", token, expired);
    }
}
