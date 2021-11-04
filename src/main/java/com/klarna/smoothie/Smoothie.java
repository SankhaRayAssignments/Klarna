package com.klarna.smoothie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.ImmutableMap;


class Smoothie {

    private static final Map<String, Flavours> flavoursMap = new ImmutableMap.Builder<String, Flavours>()
            .put("Classic", Flavours.newBuilder().withName("Classic")
                    .withIngredient("strawberry")
                    .withIngredient("banana")
                    .withIngredient("mango")
                    .withIngredient("peach")
                    .withIngredient("honey").build())
            .put("Freezie", Flavours.newBuilder().withName("Freezie")
                    .withIngredient("blackberry")
                    .withIngredient("blueberry")
                    .withIngredient("black currant")
                    .withIngredient("grape juice")
                    .withIngredient("frozen yogurt").build())
            .put("Greenie", Flavours.newBuilder().withName("Greenie")
                    .withIngredient("green apple")
                    .withIngredient("lime")
                    .withIngredient("avocado")
                    .withIngredient("spinach")
                    .withIngredient("ice")
                    .withIngredient("apple juice").build())
            .put("Just Desserts", Flavours.newBuilder().withName("Just Desserts")
                    .withIngredient("banana")
                    .withIngredient("ice cream")
                    .withIngredient("chocolate")
                    .withIngredient("peanut")
                    .withIngredient("ice")
                    .withIngredient("cherry").build())

            .build();

    public static String ingredients(String order) {
        if (StringUtils.isBlank(order)) {
            throw new IllegalArgumentException("Invalid order:" + order);
        }

        if (!flavoursMap.containsKey(order) && !order.contains(",")) {
            throw new IllegalArgumentException("Invalid order:" + order);
        }

        Flavours flavour = getFlavour(order);
        List<String> ingredients = null;
        if (order.contains(",")) {
            ingredients = flavour.getIngredients().stream().collect(Collectors.toList());
            List<String> requestIngredient = Stream.of(order.split(","))
                    .filter(s -> !flavoursMap.keySet().contains(s))
                    .collect(Collectors.toList());
            if (requestIngredient.stream().anyMatch(s -> !s.startsWith("-"))) {
                throw new IllegalArgumentException("Invalid parameters");
            }
            List<String> allergicSubtances = requestIngredient.stream()
                    .map(s -> s.replace("-", ""))
                    .collect(Collectors.toList());
            if (allergicSubtances.stream().anyMatch(StringUtils::isBlank)) {
                throw new IllegalArgumentException("Invalid parameters");
            }
            ingredients.removeAll(allergicSubtances);
        } else {
            ingredients = flavoursMap.get(order).getIngredients();
        }
        return ingredients.stream().collect(Collectors.joining(","));
    }

    private static Flavours getFlavour(String order) {
        String flavorKey = order;
        if (order.contains(",")) {
            String[] tokens = order.split(",");
            if (tokens.length > 0) {
                flavorKey = tokens[0];
            }
        }
        if (flavoursMap.containsKey(flavorKey)) {
            return flavoursMap.get(flavorKey);
        }
        throw new IllegalArgumentException("Invalid order:" + order);
    }

}

class Flavours {
    private String name;
    private List<String> ingredients;

    private Flavours() {
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public static FlavourBuilder newBuilder() {
        return new FlavourBuilder();
    }

    public static class FlavourBuilder {
        private Flavours instance;

        private FlavourBuilder() {
            instance = new Flavours();
        }

        public FlavourBuilder withName(String name) {
            instance.name = name;
            return this;
        }

        public FlavourBuilder withIngredient(String ingredient) {
            if (null == instance.ingredients) {
                instance.ingredients = new ArrayList<>();
            }
            instance.ingredients.add(ingredient);
            return this;
        }

        public Flavours build() {
            return instance;
        }
    }
}


  
