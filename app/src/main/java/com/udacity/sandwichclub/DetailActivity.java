package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getName();

    private TextView alsoKnownTextView;
    private TextView placeOfOriginTextView;
    private TextView ingredientsTextView;
    private TextView descriptionTextView;
    private ImageView ingredientsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsImageView = findViewById(R.id.image_iv);
        alsoKnownTextView = findViewById(R.id.also_known_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int defaultPosition = getResources().getInteger(R.integer.default_position);
        int position = intent.getIntExtra(getString(R.string.extra_position_intent_key), defaultPosition);
        if (position == defaultPosition) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json, this);
        } catch (JSONException e) {
            Log.d(TAG, getString(R.string.parsing_json_error) + " " + e.getMessage());
            e.printStackTrace();
        }

        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }



    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        clearUI();
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList!=null && !alsoKnownList.isEmpty()) {
            for (String s : sandwich.getAlsoKnownAs()) {
                alsoKnownTextView.append(" " + s);

            }
        }else{
            alsoKnownTextView.setText(sandwich.getMainName());
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTextView.setText(getString(R.string.default_origin_value));
        }

        for (String s : sandwich.getIngredients()) {
            ingredientsTextView.append(" " + s);
        }

        descriptionTextView.setText(sandwich.getDescription());


        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        setTitle(sandwich.getMainName());
    }

    private void clearUI() {
        alsoKnownTextView.setText("");
        placeOfOriginTextView.setText("");
        ingredientsTextView.setText("");
        descriptionTextView.setText("");
    }
}
