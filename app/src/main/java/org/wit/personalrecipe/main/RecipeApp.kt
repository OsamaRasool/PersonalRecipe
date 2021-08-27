package org.wit.personalrecipe.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.personalrecipe.models.RecipeJSONStore
import org.wit.personalrecipe.models.RecipeMemStore
import org.wit.personalrecipe.models.RecipeStore

class RecipeApp: Application(), AnkoLogger {

    lateinit var recipes: RecipeStore

    override fun onCreate() {
        super.onCreate()
        recipes = RecipeJSONStore(applicationContext)
        info("Recipe Started")
    }
}