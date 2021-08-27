package org.wit.personalrecipe.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RecipeMemStore : RecipeStore, AnkoLogger {

    val recipes = ArrayList<RecipeModel>()

    override fun findAll(): List<RecipeModel> {
        return recipes
    }

    override fun create(recipe: RecipeModel) {
        recipe.id = getId()
        recipes.add(recipe)
        logAll()
    }

    override fun delete(recipe: RecipeModel) {
        recipes.remove(recipe)
    }

    override fun update(recipe: RecipeModel) {
        var foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            foundRecipe.name = recipe.name
            foundRecipe.ingredients = recipe.ingredients
            foundRecipe.recipeExplain = recipe.recipeExplain
            foundRecipe.image = recipe.image
            logAll()
        }
    }

    fun logAll() {
        recipes.forEach { info("${it}") }
    }
}
