package com.veganny.Populator;

import java.util.ArrayList;
import java.util.List;

public class CuisineRepository {
    private List<String> cuisines;

    public CuisineRepository() {
        cuisines = new ArrayList<>();
        cuisines.add("Italian");
        cuisines.add("Mexican");
        cuisines.add("Indian");
        cuisines.add("Chinese");
        cuisines.add("Japanese");
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void addCuisine(String cuisine) {
        cuisines.add(cuisine);
    }

    public void removeCuisine(String cuisine) {
        cuisines.remove(cuisine);
    }
}