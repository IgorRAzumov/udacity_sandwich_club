package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) throws JSONException {
        JSONObject JsonObject = new JSONObject(json);

        JSONObject sandwichNameObject = JsonObject.getJSONObject(
                context.getString(R.string.name_object_json_key));

        String mainName = sandwichNameObject.getString(context.getString(
                R.string.sandwich_name_json_key));

        List<String> alsoKnownAsList = new ArrayList<>();
        JSONArray alsoKnowAsArray = sandwichNameObject.getJSONArray(context.getString(
                R.string.also_known_name_json_key));
        for (int i = 0; i < alsoKnowAsArray.length(); i++) {
            alsoKnownAsList.add(alsoKnowAsArray.getString(i));
        }


        String placeOfOrigin = JsonObject.getString(context.getString(
                R.string.place_of_origin_gson_key));

        String description = JsonObject.getString(context.getString(R.string.description_json_key));

        String image = JsonObject.getString(context.getString(R.string.image_json_key));

        List<String> ingredientsList = new ArrayList<>();
        JSONArray ingredientsJsonArray = JsonObject.getJSONArray(context.getString(
                R.string.ingredients_gson_key));
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredientsList.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAsList,
                placeOfOrigin, description, image, ingredientsList);
    }
}
