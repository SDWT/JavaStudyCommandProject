package com.example.sortapp.sorting.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.example.sortapp.model.User;
import com.example.sortapp.sorting.comparator.UserComparator;

public class BubbleSorter extends UserSorter {

    public BubbleSorter(UserComparator comparator) {
        super(comparator);
    }

    @Override
    public List<User> sort(List<User> users) {
        int size = users.size();
        var result = new ArrayList<User>(users);
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size - 1; j++) {
                var userA = users.get(j);
                var userB = users.get(j + 1);
                if (comparator.compare(userA, userB) > 0) {
                    swap(users, j, j + 1);
                }
            }
        }
        return result;
    }

    private void swap(List<User> users, int a, int b) {
        var userA = users.get(a);
        var userB = users.get(b);
        users.set(a, userB);
        users.set(b, userA);
    }
}
