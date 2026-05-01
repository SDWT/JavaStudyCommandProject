package com.example.sortapp.sorting.algorithm;

import java.util.List;

import com.example.sortapp.model.User;
import com.example.sortapp.sorting.comparator.UserComparator;

public class BubbleSorter extends UserSorter {

    public BubbleSorter(UserComparator comparator) {
        super(comparator);
    }

    @Override
    public List<User> sort(List<User> users) {
        return null;
    }
}
