
package com.securecode;

import com.securecode.DomainObject.UsernameDomainPrimitive;
import com.securecode.DomainObject.PasswordDomainPrimitive;

public class DomainPrimitiveTest {

    public static void main(String[] args) {

        testUsername("Shilpa123");
        testUsername("abcdefghijklmnopqrstuvwxyz");
        testUsername("ShilpaZZ");

        testPassword("Secure@12345");
        testPassword("test@123009");
        testPassword("Password^123");
    }

    private static void testUsername(String value) {
        try {
            new UsernameDomainPrimitive(value);
            System.out.println("Username accepted: " + value);
        } catch (IllegalArgumentException ex) {
            System.out.println("Username rejected: " + value);
        }
    }

    private static void testPassword(String value) {
        try {
            new PasswordDomainPrimitive(value);
            System.out.println("Password accepted");
        } catch (IllegalArgumentException ex) {
            System.out.println("Password rejected");
        }
    }
}
