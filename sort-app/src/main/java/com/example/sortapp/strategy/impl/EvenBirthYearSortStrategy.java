package com.example.sortapp.strategy.impl;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenBirthYearSortStrategy implements SortStrategy<User> {

        private final SortStrategy<User> delegate;

        public EvenBirthYearSortStrategy(SortStrategy<User> delegate) {
                this.delegate = delegate;
        }

        @Override
        public void sort(List<User> list, Comparator<User> comparator) {

                List<User> evenUsers = extractEvenUsers(list);

                delegate.sort(evenUsers, comparator);

                replaceEvenUsers(list, evenUsers);
        }

        private List<User> extractEvenUsers(List<User> list) {

                List<User> evenUsers = new ArrayList<>();

                for (User user : list)
                        if (user.getBirthYear() % 2 == 0)
                                evenUsers.add(user);

                return evenUsers;
        }

        private void replaceEvenUsers(List<User> original, List<User> sortedEvenUsers) {

                int evenIndex = 0;

                for (int i = 0; i < original.size(); i++) {
                        User user = original.get(i);
                        if (user.getBirthYear() % 2 == 0)
                                original.set(i, sortedEvenUsers.get(evenIndex++));
                }
        }
}