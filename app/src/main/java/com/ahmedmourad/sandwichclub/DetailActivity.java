package com.ahmedmourad.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedmourad.sandwichclub.model.Sandwich;
import com.ahmedmourad.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView imageView;

    @BindView(R.id.description_cv)
    CardView descriptionCardView;

    @BindView(R.id.description_tv)
    TextView descriptionTextView;

    @BindView(R.id.ingredients_cv)
    CardView ingredientsCardView;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;

    @BindView(R.id.origin_cv)
    CardView originCardView;

    @BindView(R.id.origin_tv)
    TextView originTextView;

    @BindView(R.id.also_known_cv)
    CardView alsoKnownCardView;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownTextView;

    @BindArray(R.array.sandwich_details)
    String[] sandwiches;

    @BindString(R.string.detail_error_message)
    String detailErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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

        final Sandwich sandwich = JsonUtils.parseSandwichJson(sandwiches[position]);

        if (sandwich == null) {

            // Sandwich data unavailable
            closeOnError();

            return;
        }

        populateUI(sandwich);

        Log.e("00000000000000000000000", sandwich.getImage());

        Picasso.with(this)
                .load(sandwich.getImage())
                // fetched from https://www.google.nl/search?client=opera&hs=4n1&dcr=0&biw=1280&bih=914&tbm=isch&sa=1&ei=O8OGWrH0F4KV5wLR_7noDA&q=food+image+placeholder&oq=food+image+placeholder&gs_l=psy-ab.3..0.7271.7955.0.8890.4.4.0.0.0.0.473.473.4-1.1.0....0...1c.1.64.psy-ab..3.1.471....0.p3Pc_SV7HFA#imgrc=7v9FGJKTHvkmjM:
                .placeholder(R.drawable.placeholder)
                // fetched from https://www.google.nl/search?client=opera&dcr=0&biw=1280&bih=914&tbm=isch&sa=1&ei=b8aGWsK9PKft5gKshqPICw&q=photo+not+found&oq=photo+not+found&gs_l=psy-ab.3..0l3j0i5i30k1l2j0i24k1l2.7815.10332.0.10451.15.9.0.0.0.0.509.917.4-1j1.2.0....0...1c.1.64.psy-ab..13.2.913...0i67k1.0.6qdO9_Aswmc#imgrc=T9ICms3ot5I2MM:
                // and slightly modified
                .error(R.drawable.error)
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {

        finish();

        Toast.makeText(this, detailErrorMessage, Toast.LENGTH_LONG).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Description
        if (sandwich.getDescription().isEmpty())
            descriptionCardView.setVisibility(View.GONE);
        else
            descriptionTextView.setText(sandwich.getDescription());

        // Ingredients
        if (sandwich.getIngredients().isEmpty())
            ingredientsCardView.setVisibility(View.GONE);
        else
            ingredientsTextView.setText(listToString(sandwich.getIngredients()));

        // Place Of Origin
        if (sandwich.getPlaceOfOrigin().isEmpty())
            originCardView.setVisibility(View.GONE);
        else
            originTextView.setText(sandwich.getPlaceOfOrigin());

        // Also Known As
        if (sandwich.getAlsoKnownAs().isEmpty())
            alsoKnownCardView.setVisibility(View.GONE);
        else
            alsoKnownTextView.setText(listToString(sandwich.getAlsoKnownAs()));
    }

    /**
     * converts our list to a displayable string
     *
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
