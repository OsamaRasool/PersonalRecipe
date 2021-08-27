package org.wit.personalrecipe.models

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import java.nio.file.Files.exists
import java.util.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.wit.personalrecipe.helpers.exists
import org.wit.personalrecipe.helpers.read
import org.wit.personalrecipe.helpers.write

val JSON_FILE = "recipes.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<RecipeModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RecipeJSONStore : RecipeStore, AnkoLogger {

    val context: Context
    var recipes = mutableListOf<RecipeModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RecipeModel> {
        return recipes
    }

    override fun create(recipe: RecipeModel) {
       recipe.id = generateRandomId()
       recipes.add(recipe)
        serialize()
    }


    override fun update(recipe: RecipeModel) {
        val recipesList = findAll() as ArrayList<RecipeModel>
        var foundRecipe: RecipeModel? = recipesList.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            foundRecipe.name= recipe.name
            foundRecipe.ingredients = recipe.ingredients
            foundRecipe.recipeExplain = recipe.recipeExplain
            foundRecipe.image = recipe.image


        }
        serialize()
    }

    override fun delete(recipe: RecipeModel) {
        recipes.remove(recipe)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(recipes, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        recipes = Gson().fromJson(jsonString, listType)
    }
}
