package com.example.task;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class UsersTests {

    List<User> userList;

    @Before
    public void setUp() {
        UserClient userClient = new UserClient();
        userList = userClient.users();
    }

    @Test
    public void checkUsersNotEmpty() {
        Assert.assertNotNull("List is empty", userList);
        Logger.getAnonymousLogger().log(Level.INFO, "List is not empty");
    }

    @Test
    public void assureUsersDataIsComplete() {
        for (User user : userList) {
            Assert.assertNotNull("Gender not provided", user.getGender());
            Assert.assertNotNull("First name not provided", user.getName().getFirst());
            Assert.assertNotNull("Last name not provided", user.getName().getLast());
            Assert.assertNotNull("City not provided", user.getLocation().getCity());
            Assert.assertNotNull("Login uuid not provided", user.getLogin().getUuid());
            Assert.assertNotNull("Registration date not provided", user.getRegistered().getDate());
        }
        Logger.getAnonymousLogger().log(Level.INFO, "All users data is complete");
    }

    @Test
    public void checkIfRegistrationDateIsOutdated() {
        Instant timeNow = Instant.now();
        for (User user : userList) {
            Instant dateTime = Instant.parse(user.getRegistered().getDate());
            Assert.assertTrue("Registration Date is after current date", dateTime.isBefore(timeNow));
        }
        Logger.getAnonymousLogger().log(Level.INFO, "Users are created correctly");
    }

}
