package com.terraludyca.ludoteca.model;

/**
 * Represents a ludoteca member retrieved from Google Sheets.
 */
public class Member {

    private final String name;
    private final String email;
    private final int subscriptionYear;

    /**
     * Builds a new member with the provided data.
     */
    public Member(String name, String email, int subscriptionYear) {
        this.name = name;
        this.email = email;
        this.subscriptionYear = subscriptionYear;
    }

    /**
     * Returns the member name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the member email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the subscription year for the member.
     */
    public int getSubscriptionYear() {
        return subscriptionYear;
    }
}
