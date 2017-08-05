package challenge.github.alc.com.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import challenge.github.alc.com.bakingapp.IdlingResource.SimpleIdlingResource;
import challenge.github.alc.com.bakingapp.R;
import challenge.github.alc.com.bakingapp.activities.RecipeActivity;
import challenge.github.alc.com.bakingapp.adapters.RecipeAdapter;
import challenge.github.alc.com.bakingapp.networkUtills.InitRetrofit;
import challenge.github.alc.com.bakingapp.pojo.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static challenge.github.alc.com.bakingapp.activities.RecipeActivity.ALL_RECIPES;
import challenge.github.alc.com.bakingapp.networkUtills.IRecipe;
/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {


    public RecipeFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;

        View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

        recyclerView=(RecyclerView)  rootView.findViewById(R.id.recipe_recycler);
       final RecipeAdapter recipesAdapter =new RecipeAdapter((RecipeActivity)getActivity());
        recyclerView.setAdapter(recipesAdapter);



        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        IRecipe iRecipe = InitRetrofit.Retrieve();
        Call<ArrayList<Recipe>> recipe = iRecipe.getRecipe();

        final SimpleIdlingResource idlingResource = (SimpleIdlingResource)((RecipeActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<Recipe> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes,getContext());
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }


}
