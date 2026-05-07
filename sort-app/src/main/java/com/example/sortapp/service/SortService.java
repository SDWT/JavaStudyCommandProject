package com.example.sortapp.service;

import java.util.Comparator;
import java.util.List;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.strategy.SortStrategy;

public class SortService {

    public void handleRandom() {
        System.out.println("Random input not implemented yet");
    }

    public void handleFile() {
        System.out.println("File input not implemented yet");
    }

    public void handleManual() {
        System.out.println("Manual input not implemented yet");
    }

    public List<User> sort(List<User> data, SortStrategy<User> strategy, Comparator<User> comparator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sort'");
    }
}
