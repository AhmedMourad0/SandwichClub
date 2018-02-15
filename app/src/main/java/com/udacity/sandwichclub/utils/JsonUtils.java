package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // json keys
        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        // our sandwich
        Sandwich sandwich;

        try {

            sandwich = new Sandwich();

            // outer json object
            final JSONObject sandwichObject = new JSONObject(json);

            // json name object
            final JSONObject nameObject = sandwichObject.getJSONObject(KEY_NAME);

            sandwich.setMainName(nameObject.getString(KEY_MAIN_NAME));
            sandwich.setAlsoKnownAs(jsonStringArrayToStringList(nameObject.getJSONArray(KEY_ALSO_KNOWN_AS)));

            sandwich.setPlaceOfOrigin(sandwichObject.getString(KEY_PLACE_OF_ORIGIN));
            sandwich.setDescription(sandwichObject.getString(KEY_DESCRIPTION));
            sandwich.setImage(sandwichObject.getString(KEY_IMAGE));
            sandwich.setIngredients(jsonStringArrayToStringList(sandwichObject.getJSONArray(KEY_INGREDIENTS)));

        } catch (JSONException e) {

            // if anything goes wrong we wanna return null
            sandwich = null;

            e.printStackTrace();
        }

        return sandwich;
    }

    /**
     * converts a JSONArray of Strings to a String List
     * @param array the JSONArray to covert
     * @return the String List
     */
    private static List<String> jsonStringArrayToStringList(JSONArray array) {

        final List<String> list = new ArrayList<>(array.length());

        for (int i = 0; i < array.length(); ++i)
            list.add(array.optString(i));

        return list;
    }
}
