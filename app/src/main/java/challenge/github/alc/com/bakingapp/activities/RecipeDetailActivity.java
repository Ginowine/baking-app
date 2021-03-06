package challenge.github.alc.com.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import challenge.github.alc.com.bakingapp.R;
import challenge.github.alc.com.bakingapp.adapters.RecipeDetailAdapter;
import challenge.github.alc.com.bakingapp.fragments.RecipeDetailFragment;
import challenge.github.alc.com.bakingapp.fragments.RecipeStepDetailFragment;
import challenge.github.alc.com.bakingapp.pojo.Recipe;
import challenge.github.alc.com.bakingapp.pojo.Step;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListener,RecipeStepDetailFragment.ListItemClickListener{

    public static String ALL_RECIPES="All_Recipes";
    public static String SELECTED_RECIPES="Selected_Recipes";
    public static String SELECTED_STEPS="Selected_Steps";
    public static String SELECTED_INDEX="Selected_Index";
    public static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    public static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";


    public ArrayList<Recipe> recipe;
    public String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {

            Bundle selectedRecipeBundle = getIntent().getExtras();

            recipe = new ArrayList<>();
            recipe = selectedRecipeBundle.getParcelableArrayList(SELECTED_RECIPES);
            recipeName = recipe.get(0).getName();

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

                final RecipeStepDetailFragment fragment2 = new RecipeStepDetailFragment();
                fragment2.setArguments(selectedRecipeBundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();

            }



        } else {
            recipeName= savedInstanceState.getString("Title");
        }


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                if (findViewById(R.id.fragment_container2)==null) {
                    if (fm.getBackStackEntryCount() > 1) {
                        //go back to "Recipe Detail" screen
                        fm.popBackStack(STACK_RECIPE_DETAIL, 0);
                    } else if (fm.getBackStackEntryCount() > 0) {
                        //go back to "Recipe" screen
                        finish();

                    }


                }
                else {

                    //go back to "Recipe" screen
                    finish();

                }

            }
        });
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int selectedItemIndex, String recipeName) {


        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setTitle(recipeName);

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Step>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX,selectedItemIndex);
        stepBundle.putString("Title",recipeName);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();

        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",recipeName);
    }
}
