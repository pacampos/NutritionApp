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
package com.example.nutrition.nutritionapp.utils;

/**
 * This utility class helps to get detailed information about recipe item(s) from fatsecret rest api
 *
 * @author Saurabh Rane
 * @version 1.0
 */
import com.example.nutrition.nutritionapp.Model.Category;
import com.example.nutrition.nutritionapp.Model.CompactRecipe;
import com.example.nutrition.nutritionapp.Model.Direction;
import com.example.nutrition.nutritionapp.Model.Ingredient;
import com.example.nutrition.nutritionapp.Model.Recipe;
import com.example.nutrition.nutritionapp.Model.Serving;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeUtility {

	/**
	 * Returns detailed information about the recipe
	 * 
	 * @param json			json object representing of the recipe
	 * @return				detailed information about the recipe
	 */
	public static Recipe parseRecipeFromJSONObject(JSONObject json) {
		String name = null;
		try {
			name = json.getString("recipe_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String url = null;
		try {
			url = json.getString("recipe_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String description = null;
		try {
			description = json.getString("recipe_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Long id = null;
		try {
			id = Long.parseLong(json.getString("recipe_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		List<String> images = new ArrayList<String>();
		JSONObject recipeImages = null;
		try {
			recipeImages = json.getJSONObject("recipe_images");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray recipeImage = null;
		try {
			recipeImage = recipeImages.getJSONArray("recipe_image");
		} catch(Exception e) {
			recipeImage = null;
		}
		
		if(recipeImage != null) {
			for(int i = 0; i < recipeImage.length(); i++) {
				String image = null;
				try {
					image = recipeImage.getString(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				images.add(image);
			}
		} else {
			String image = null;
			try {
				image = recipeImages.getString("recipe_image");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			images.add(image);
		}

		Integer rating = null;
		try {
			rating = Integer.parseInt(json.getString("rating"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		List<String> types = new ArrayList<String>();
		JSONObject recipeTypes = null;
		try {
			recipeTypes = json.getJSONObject("recipe_types");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray recipeType = null;
		try {
			recipeType = recipeTypes.getJSONArray("recipe_type");
		} catch(Exception e) {
			recipeType = null;
		}

		if(recipeType != null) {
			for(int i = 0; i < recipeType.length(); i++) {
				String type = null;
				try {
					type = recipeType.getString(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				types.add(type);
			}
		} else {
			String type = null;
			try {
				type = recipeTypes.getString("recipe_type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			types.add(type);
		}

		BigDecimal numberOfServings = null;
		try {
			numberOfServings = new BigDecimal(json.getString("number_of_servings"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Integer preparationTime = 0;
		try {
			preparationTime = Integer.parseInt(json.getString("preparation_time_min"));
		} catch(Exception ignore) {
			preparationTime = 0;
		}
		
		Integer cookingTime = 0;
		
		try {
			cookingTime = Integer.parseInt(json.getString("cooking_time_min"));
		} catch(Exception ignore) {
			cookingTime = 0;
		}
		
		List<Category> categories = new ArrayList<Category>();
		JSONObject recipeCategories = null;
		try {
			recipeCategories = json.getJSONObject("recipe_categories");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray recipeCategory = null;

		try {
			recipeCategory = recipeCategories.getJSONArray("recipe_category");
		} catch(Exception e) {
			recipeCategory = null;
		}

		if(recipeCategory != null) {
			for(int i = 0; i < recipeCategory.length(); i++) {
				JSONObject recipeCategoryObj = null;
				try {
					recipeCategoryObj = recipeCategory.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Category category = parseJsonToCategory(recipeCategoryObj);
				categories.add(category);
			}
		} else {
			JSONObject recipeCategoryObj = null;
			try {
				recipeCategoryObj = recipeCategories.getJSONObject("recipe_category");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Category category = parseJsonToCategory(recipeCategoryObj);
			categories.add(category);
		}

		JSONObject servingSizes = null;
		try {
			servingSizes = json.getJSONObject("serving_sizes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject servingObj = null;
		try {
			servingObj = servingSizes.getJSONObject("serving");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Serving serving = ServingUtility.parseServingFromJSONObject(servingObj);

		JSONObject directionsObj = null;
		try {
			directionsObj = json.getJSONObject("directions");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<Direction> directions = new ArrayList<Direction>();
		JSONArray directionArray = null;
		try {
			directionArray = directionsObj.getJSONArray("direction");
		} catch(Exception e) {
			directionArray = null;
		}
		
		if(directionArray != null) {
			for(int i = 0; i < directionArray.length(); i++) {
				JSONObject directionObj = null;
				try {
					directionObj = directionArray.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Direction direction = parseJsonToDirection(directionObj);
				directions.add(direction);
			}
		} else {
			JSONObject directionObj = null;
			try {
				directionObj = directionsObj.getJSONObject("direction");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Direction direction = parseJsonToDirection(directionObj);
			directions.add(direction);
		}
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		JSONObject ingredientsObj = null;
		try {
			ingredientsObj = json.getJSONObject("ingredients");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray ingredientArray = null;
		try {
			ingredientArray = ingredientsObj.getJSONArray("ingredient");
		} catch(Exception e) {
			ingredientArray = null;
		}

		if(ingredientArray != null) {
			for(int i = 0; i < ingredientArray.length(); i++) {
				JSONObject ingredientObj = null;
				try {
					ingredientObj = ingredientArray.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Ingredient ingredient = parseJsonToIngredient(ingredientObj);
				ingredients.add(ingredient);				
			}
		} else {
			JSONObject ingredientObj = null;
			try {
				ingredientObj = ingredientsObj.getJSONObject("ingredient");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Ingredient ingredient = parseJsonToIngredient(ingredientObj);
			ingredients.add(ingredient);
		}

		Recipe recipe = new Recipe();

		recipe.setName(name);
		recipe.setUrl(url);
		recipe.setDescription(description);
		recipe.setId(id);
		recipe.setImages(images);
		recipe.setRating(rating);
		recipe.setTypes(types);
		recipe.setNumberOfServings(numberOfServings);
		recipe.setPreparationTime(preparationTime);
		recipe.setCookingTime(cookingTime);
		recipe.setCategories(categories);
		recipe.setServing(serving);
		recipe.setDirections(directions);
		recipe.setIngredients(ingredients);

		return recipe;
	}
	
	/**
	 * Returns information about the compact item
	 * 
	 * @param json			json object representing of the recipe
	 * @return				compact recipe item
	 */
	public static CompactRecipe parseCompactRecipeFromJSONObject(JSONObject json) {

		String name = null;
		try {
			name = json.getString("recipe_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String url = null;
		try {
			url = json.getString("recipe_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String description = null;
		try {
			description = json.getString("recipe_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Long id = null;
		try {
			id = Long.parseLong(json.getString("recipe_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		List<String> images = new ArrayList<String>();
		
		try {
			String image = json.getString("recipe_image");
			images.add(image);
		} catch(Exception ignore) {}
		
		CompactRecipe recipe = new CompactRecipe();
		
		recipe.setName(name);
		recipe.setUrl(url);
		recipe.setDescription(description);
		recipe.setId(id);
		recipe.setImages(images);
		
		return recipe;
		
	}
	
	/**
	 * Returns a list of compact recipe items
	 * 
	 * @param array			json array representing a list of compact recipe
	 * @return				list of compact recipe items
	 */
	public static List<CompactRecipe> parseCompactRecipeListFromJSONArray(JSONArray array) {
		List<CompactRecipe> recipes = new ArrayList<CompactRecipe>();
		for(int i = 0; i < array.length(); i++) {
			JSONObject obj = null;
			try {
				obj = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			CompactRecipe recipe = parseCompactRecipeFromJSONObject(obj);
			recipes.add(recipe);
		}
		return recipes;
	}
	
	/**
	 * Returns the category that the recipe is classified under
	 * 
	 * @param json			json object representing of the category
	 * @return				the category that the recipe is classified under
	 */
	public static Category parseJsonToCategory(JSONObject json) {
		String url = null;
		try {
			url = json.getString("recipe_category_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String name = null;
		try {
			name = json.getString("recipe_category_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Category category = new Category();
		category.setUrl(url);
		category.setName(name);

		return category;
	}
	
	/**
	 * Returns direction involved in creating the recipe
	 * 
	 * @param json			json object representing of the direction
	 * @return				direction involved in creating the recipe
	 */
	public static Direction parseJsonToDirection(JSONObject json) {
		Integer number = null;
		try {
			number = Integer.parseInt(json.getString("direction_number"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String description = null;
		try {
			description = json.getString("direction_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Direction direction = new Direction();
		
		direction.setNumber(number);
		direction.setDescription(description);

		return direction;
	}
	
	/**
	 * Returns detailed information about the ingredient
	 * 
	 * @param json			json object representing of the ingredient
	 * @return				detailed information about the ingredient
	 */
	public static Ingredient parseJsonToIngredient(JSONObject json) {

		Long foodId = null;
		try {
			foodId = Long.parseLong(json.getString("food_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Long servingId = null;
		try {
			servingId = Long.parseLong(json.getString("serving_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String description = null;
		try {
			description = json.getString("ingredient_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String name = null;
		try {
			name = json.getString("food_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String url = null;
		try {
			url = json.getString("ingredient_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		BigDecimal numberOfUnits = null;
		try {
			numberOfUnits = new BigDecimal(json.getString("number_of_units"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String measurementDescription = null;
		try {
			measurementDescription = json.getString("measurement_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Ingredient ingredient = new Ingredient();
		
		ingredient.setFoodId(foodId);
		ingredient.setServingId(servingId);
		ingredient.setDescription(description);
		ingredient.setName(name);
		ingredient.setUrl(url);
		ingredient.setNumberOfUnits(numberOfUnits);
		ingredient.setMeasurementDescription(measurementDescription);
		
		return ingredient;
	}
}
