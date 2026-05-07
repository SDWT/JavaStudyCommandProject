package com.example.sortapp.strategy.comparator;

import java.util.Comparator;

import com.example.sortapp.domain.model.User;

public class NameEmailYearComparator implements Comparator<User> {

    @Override
    public int compare(User a, User b) {
        int result;
        result = a.getName().compareTo(b.getName());
        if (result != 0) {
            return result;
        }
        result = a.getEmail().compareTo(b.getEmail());
        if (result != 0) {
            return result;
        }
        result = a.getBirthYear() - b.getBirthYear();
        if (result != 0) {
            return result;
        }
        return 0;
    }
}
