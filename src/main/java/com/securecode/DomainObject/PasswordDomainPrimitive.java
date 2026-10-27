
package com.securecode.DomainObject;

public final class PasswordDomainPrimitive {

    private final String value;

    public PasswordDomainPrimitive(String password) {
        if (password == null ||
            !password.matches("[A-Za-z0-9!@#$%^&*]{10,32}")) {
            throw new IllegalArgumentException(
                "Password must be 10 to 32 characters and contain only letters, numbers, and !@#$%^&*"
            );
        }

        this.value = password;
    }

    public String getValue() {
        return value;
    }
}
