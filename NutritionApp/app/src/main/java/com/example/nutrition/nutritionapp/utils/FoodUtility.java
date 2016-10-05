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

import com.example.nutrition.nutritionapp.Model.Food;
import com.example.nutrition.nutritionapp.Model.Serving;
import com.example.nutrition.nutritionapp.Model.CompactFood;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This utility class helps to get detailed information about food item(s) from fatsecret rest api
 *
 * @author Saurabh Rane
 * @version 1.0
 */
public class FoodUtility {

	/**
	 * Returns detailed information about the food
	 * 
	 * @param json			json object representing of the food
	 * @return				detailed information about the food
	 */
	public static Food parseFoodFromJSONObject(JSONObject json) {
		String name = null;
		try {
			name = json.getString("food_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String url = null;
		try {
			url = json.getString("food_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String type = null;
		try {
			type = json.getString("food_type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Long id = null;
		try {
			id = Long.parseLong(json.getString("food_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String brandName = "";
		
		try {
			brandName = json.getString("brand_name");
		} catch(Exception ignore) {
		}

		JSONObject servingsObj = null;
		try {
			servingsObj = json.getJSONObject("servings");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray array = null;
		List<Serving> servings = new ArrayList<Serving>();
		
		try {
			array = servingsObj.getJSONArray("serving");
			servings = ServingUtility.parseServingsFromJSONArray(array);
		} catch(Exception ignore) {
			System.out.println("Servings not found");
			array = null;
		}
		
		if(array == null) {
			try {
				JSONObject servingObj = servingsObj.getJSONObject("serving");
				Serving serving = ServingUtility.parseServingFromJSONObject(servingObj);
				servings.add(serving);
			} catch(Exception ignore) {
				System.out.println("com.example.nutrition.nutritionapp.Model.Serving not found");
			}
		}

		Food food = new Food();
		
		food.setName(name);
		food.setUrl(url);
		food.setType(type);
		food.setId(id);
		food.setBrandName(brandName);
		food.setServings(servings);
		
		return food;
	}
	
	/**
	 * Returns information about the compact food
	 * 
	 * @param json			json object representing of the food
	 * @return				compact food object from the json
	 */
	public static CompactFood parseCompactFoodFromJSONObject(JSONObject json) {

		String name = null;
		try {
			name = json.getString("food_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String url = null;
		try {
			url = json.getString("food_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String type = null;
		try {
			type = json.getString("food_type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String description = null;
		try {
			description = json.getString("food_description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Long id = null;
		try {
			id = Long.parseLong(json.getString("food_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		CompactFood food = new CompactFood();
		
		food.setName(name);
		food.setUrl(url);
		food.setType(type);
		food.setDescription(description);
		food.setId(id);
		
		return food;
	}

	/**
	 * Returns a list of compact food items
	 * 
	 * @param array			json array representing a list of compact food
	 * @return				list of compact food items
	 */
	public static List<CompactFood> parseCompactFoodListFromJSONArray(JSONArray array) {
		List<CompactFood> foods = new ArrayList<CompactFood>();
		
		for(int i = 0; i < array.length(); i++) {
			JSONObject obj = null;
			try {
				obj = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			CompactFood food = parseCompactFoodFromJSONObject(obj);
			
			foods.add(food);
		}
		
		return foods;
	}
}
