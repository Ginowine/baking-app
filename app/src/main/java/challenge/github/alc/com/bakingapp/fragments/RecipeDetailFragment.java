package challenge.github.alc.com.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import challenge.github.alc.com.bakingapp.R;
import challenge.github.alc.com.bakingapp.activities.RecipeDetailActivity;
import challenge.github.alc.com.bakingapp.adapters.RecipeDetailAdapter;
import challenge.github.alc.com.bakingapp.pojo.Ingredient;
import challenge.github.alc.com.bakingapp.pojo.Recipe;
import challenge.github.alc.com.bakingapp.widget.UpdateBakingService;

import static challenge.github.alc.com.bakingapp.activities.RecipeActivity.SELECTED_RECIPES;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {


    ArrayList<Recipe> recipe;
    String recipeName;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;

        recipe = new ArrayList<>();


        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        }
        else {
            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        List<Ingredient> ingredients = recipe.get(0).getIngredients();
        recipeName=recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();


        ingredients.forEach((a) ->
        {
            textView.append("\u2022 "+ a.getIngredient()+"\n");
            textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");
            textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");

            recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                    "Quantity: "+a.getQuantity().toString()+"\n"+
                    "Measure: "+a.getMeasure()+"\n");
        });

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recipe_detail_recycler);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailAdapter mRecipeDetailAdapter =new RecipeDetailAdapter((RecipeDetailActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe,getContext());

        //update widget
        UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);
        currentState.putString("Title",recipeName);
    }

}
