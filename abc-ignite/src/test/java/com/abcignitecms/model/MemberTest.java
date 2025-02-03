package com.abcignitecms.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MemberTest {

    @Test
    void testMemberConstructor() {
        // Arrange
        String name = "Sai Tharun";
        String email = "sai.tharun@example.com";

        // Act
        Member member = new Member(name, email);

        // Assert
        assertNotNull(member);  // Ensure member object is not null
        assertEquals(name, member.getName());  // Ensure name is correctly set
        assertEquals(email, member.getEmail());  // Ensure email is correctly set
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Member member = new Member("Sai K", "sai.k@example.com");

        // Test initial values
        assertEquals("Sai K", member.getName());
        assertEquals("sai.k@example.com", member.getEmail());

        // Test setters
        member.setName("Updated Name");
        member.setEmail("updated.email@example.com");

        // Test updated values
        assertEquals("Updated Name", member.getName());
        assertEquals("updated.email@example.com", member.getEmail());
    }

    @Test
    void testSetNullName() {
        // Arrange
        Member member = new Member("Sai Tharun", "sai.tharun@example.com");

        // Act
        member.setName(null);

        // Assert
        assertNull(member.getName()); 
    }

    @Test
    void testSetNullEmail() {
        // Arrange
        Member member = new Member("Sai Tharun", "sai.tharun@example.com");

        // Act
        member.setEmail(null);

        // Assert
        assertNull(member.getEmail()); 
    }

    @Test
    void testEmptyStringName() {
        // Arrange
        Member member = new Member("Sai Tharun", "sai.tharun@example.com");

        // Act
        member.setName("");

        // Assert
        assertEquals("", member.getName());
    }

    @Test
    void testEmptyStringEmail() {
        // Arrange
        Member member = new Member("Sai Tharun", "sai.tharun@example.com");

        // Act
        member.setEmail("");

        // Assert
        assertEquals("", member.getEmail()); 
    }
}
