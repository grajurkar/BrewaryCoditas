package com.coditas.brewary;

import com.coditas.brewary.constants.ConstantData;
import com.coditas.brewary.exception.DuplicateIngredientException;
import com.coditas.brewary.exception.IllegalIngredientsException;
import com.coditas.brewary.exception.InvalidOrderException;

import java.util.*;
import java.util.stream.Collectors;

public class BeverageFactory {

     
    Map<String, Double> itemRates = ConstantData.getItemRates();
    Map<String, List<String>> listBeveragesMap = ConstantData.getBeveragesMap();

 
    Map<String, Double> ingredientRates = ConstantData.getIngredientRates();

    public double getInvoiceFromOrder(String order) {
        double cost = 0.0d;
        List<String> orderItems = getItemsFromOrder(order.trim());
        for (String item : orderItems) {
            List<String> itemWithIngredients = getIngredientFromItem(item);
            checkIfValidOrder(item);
            cost = cost + calculateInvoice(itemWithIngredients);
        }
        return cost;
    }
//defined custom user defined exceptions for validations
  
    private void checkIfValidOrder(String item) {
        List<String> itemIngredients = getIngredientFromItem(item);

        
        if (!listBeveragesMap.containsKey(itemIngredients.get(0)))
            throw new InvalidOrderException("You have given Invalid Items Order -> " + item + " .Please order again with valid menu.!!");

        List<String> allIngredients = listBeveragesMap.get(itemIngredients.get(0)); 
        
        
        Optional<String> duplicateIngredient = itemIngredients.stream().filter(i -> Collections.frequency(itemIngredients, i) > 1).findFirst();
        if (duplicateIngredient.isPresent())
            throw new DuplicateIngredientException("You have orderred duplicate items in the order -> " + duplicateIngredient.get());

        List<String> ingredients = itemIngredients.subList(1, itemIngredients.size());
        boolean validIngredients = ingredients.stream().allMatch(t -> allIngredients.stream().anyMatch(t::contains));

 
        if (!validIngredients)
            throw new IllegalIngredientsException("Please check and order again..!!");

     
        if (itemIngredients.size() == allIngredients.size() + 1)
            throw new InvalidOrderException("!! Order not valid!! Your are not allowed to exclude all items from " + item);

    }
    //calculate result here
 
    private Double calculateInvoice(List<String> itemWithIngredients) {
        Double itemValue = itemRates.get(itemWithIngredients.get(0));
        Double ingredientsValue = 0.0d;
        if (itemWithIngredients.size() > 1)
            for (int i = 1; i < itemWithIngredients.size(); i++) {
                ingredientsValue = ingredientsValue + ingredientRates.get(itemWithIngredients.get(i));
            }
        return itemValue - ingredientsValue;
    }


    private List<String> getItemsFromOrder(String order1) {
        return Arrays.stream(order1.split("(?!,\\s*-),")).map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
    }

   
    private List<String> getIngredientFromItem(String item) {
        return Arrays.stream(item.split(",")).map(s -> s.replace("-", "")).map(String::trim).collect(Collectors.toList());
    }

}
