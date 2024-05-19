package com.example.JewelryShop.utils;

import com.example.JewelryShop.models.OptionValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartesianVariant {
    public List<List<OptionValue>> cartesian(List<List<OptionValue>> lists, int index) {
        if (lists.isEmpty()) return new ArrayList<>();
        if (lists.size() == 1)
            return lists.get(0).stream().map(e -> new ArrayList<>(List.of(e))).collect(Collectors.toList());
        if (index == lists.size()) {
            List<OptionValue> emptyList = new ArrayList<>();
            return List.of(emptyList);
        }
        List<OptionValue> currentList = lists.get(index);
        return currentList.stream().flatMap(element -> cartesian(lists, index + 1)
                .stream().map(list -> {
                    List<OptionValue> newList = new ArrayList<>(list);
                    newList.add(0, element);
                    return newList;
                })).collect(Collectors.toList());
    }
}
