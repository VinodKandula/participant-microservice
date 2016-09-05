package com.amhzing.participant.query.data.jpa.mapping;

public class NameBuilder {
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;

    public NameBuilder setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public NameBuilder setMiddleName(final String middleName) {
        this.middleName = middleName;
        return this;
    }

    public NameBuilder setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public NameBuilder setSuffix(final String suffix) {
        this.suffix = suffix;
        return this;
    }

    public Name createName() {
        return Name.create(firstName, middleName, lastName, suffix);
    }
}