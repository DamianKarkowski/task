package com.example.task;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private String gender;
    private Name name;
    private Location location;
    private Login login;
    private Registered registered;

}
