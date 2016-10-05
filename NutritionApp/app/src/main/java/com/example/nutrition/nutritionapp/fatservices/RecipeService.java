/*
 * Copyright (C) 2016 Saurabh Rane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.nutrition.nutritionapp.fatservices;

import com.example.nutrition.nutritionapp.Model.CompactRecipe;
import com.example.nutrition.nutritionapp.Model.Recipe;
import com.example.nutrition.nutritionapp.utils.RecipeUtility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This service class helps to get or search recipe items from fatsecret rest api
 *
 * @author Saurabh Rane
 * @version 1.0
 */
public class RecipeService {
	
	/** Request Object */
	private Request request;
	
	/**
	 * Constructor to set values for APP_KEY and APP_SECRET
	 *
	 * @param APP_KEY		a value FatSecret API issues to you which helps this API identify you
	 * @param APP_SECRET	a secret FatSecret API issues to you which helps this API establish that it really is you
	 */
	public RecipeService(String APP_KEY, String APP_SECRET) {
		request = new Request(APP_KEY, APP_SECRET);
	}
	
	/**
	 * Returns detailed information for the specified recipe
	 *
	 * @param recipeId		the unique recipe identifier
	 * @return				detailed information for the specified recipe
	 */
	public Recipe getRecipe(Long recipeId) {
		JSONObject response = request.getRecipe(recipeId);
		if(response != null) {
			JSONObject recipe = null;
			try {
				recipe = response.getJSONObject("recipe");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return RecipeUtility.parseRecipeFromJSONObject(recipe);
		}
		
		return null;
	}
	
	/**
	 * Returns response associated with the recipes at zeroth page depending on the search query
	 * 
	 * @param query			search terms for querying recipes
	 * @return				recipe items at zeroth page based on the query
	 */
	public Response<CompactRecipe> searchRecipes(String query) {
		return searchRecipes(query, 0);
	}

	/**
	 * Returns response associated with the recipes depending on the search query and page number
	 * 
	 * @param query			search terms for querying recipes
	 * @param pageNumber	page Number to search the recipes
	 * @return				recipe items at a particular page number based on the query
	 */
	public Response<CompactRecipe> searchRecipes(String query, Integer pageNumber) {
		JSONObject json = request.getRecipes(query, pageNumber);
		
		if(json != null) {

            JSONObject recipes = null;
            try {
                recipes = json.getJSONObject("recipes");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int maxResults = 0;
            try {
                maxResults = recipes.getInt("max_results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int totalResults = 0;
            try {
                totalResults = recipes.getInt("total_results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<CompactRecipe> results = new ArrayList<CompactRecipe>();//RecipeUtility.parseCompactRecipeListFromJSONArray(recipe);
			
			if(totalResults > maxResults * pageNumber) {
                JSONArray recipe = null;
                try {
                    recipe = recipes.getJSONArray("recipe");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                results = RecipeUtility.parseCompactRecipeListFromJSONArray(recipe);
			}			
			
			Response<CompactRecipe> response = new Response<CompactRecipe>();
			
			response.setPageNumber(pageNumber);
			response.setMaxResults(maxResults);
			response.setTotalResults(totalResults);
			response.setResults(results);
			
			return response;
		}		
		return null;
	}
}