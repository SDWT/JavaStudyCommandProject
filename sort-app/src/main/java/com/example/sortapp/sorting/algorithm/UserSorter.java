package com.example.sortapp.sorting.algorithm;

import java.util.List;

import com.example.sortapp.model.User;
import com.example.sortapp.sorting.comparator.UserComparator;

abstract class UserSorter {

    protected UserComparator comparator;

    protected UserSorter(UserComparator comparator) {
        this.comparator = comparator;
    }

    public abstract List<User> sort(List<User> users);
}
