
package com.securecode.DomainObject;

public final class UsernameDomainPrimitive {

    private final String value;

    public UsernameDomainPrimitive(String username) {
        if (username == null ||
            !username.matches("[A-Za-z0-9]{5,32}")) {
            throw new IllegalArgumentException(
                "Username must be 5 to 32 characters and contain only letters and numbers"
            );
        }

        this.value = username;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
