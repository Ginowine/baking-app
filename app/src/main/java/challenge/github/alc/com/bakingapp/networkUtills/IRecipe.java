package challenge.github.alc.com.bakingapp.networkUtills;

import java.util.ArrayList;

import challenge.github.alc.com.bakingapp.pojo.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Gino Osahon on 05/08/2017.
 */
public interface IRecipe {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
