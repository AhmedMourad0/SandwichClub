package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();

        if (intent == null) {

            // intent is null
            closeOnError();

            return;
        }

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {

            // EXTRA_POSITION not found in intent
            closeOnError();

            return;
        }

        final String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        final Sandwich sandwich = JsonUtils.parseSandwichJson(sandwiches[position]);

        if (sandwich == null) {

            // Sandwich data unavailable
            closeOnError();

            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into((ImageView) findViewById(R.id.image_iv));

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {

        finish();

        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Description
        if (sandwich.getDescription().isEmpty())
            findViewById(R.id.description_cv).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.description_tv)).setText(sandwich.getDescription());

        // Ingredients
        if (sandwich.getIngredients().isEmpty())
            findViewById(R.id.ingredients_cv).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.ingredients_tv)).setText(listToString(sandwich.getIngredients()));

        // Place Of Origin
        if (sandwich.getPlaceOfOrigin().isEmpty())
            findViewById(R.id.origin_cv).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.origin_tv)).setText(sandwich.getPlaceOfOrigin());

        // Also Known As
        if (sandwich.getAlsoKnownAs().isEmpty())
            findViewById(R.id.also_known_cv).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.also_known_tv)).setText(listToString(sandwich.getAlsoKnownAs()));
    }

    /**
     * converts our list to a displayable string
     * @param list the list to convert
     * @return its String representation
     */
    private String listToString(List<String> list) {

        StringBuilder result = new StringBuilder();

        for (String str : list)
            result.append(str).append("  -  ");

        return result.delete(result.length() - 4, result.length()).toString();
    }
}
