package org.wit.personalrecipe.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.card_recipe.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.personalrecipe.R
import org.wit.personalrecipe.helpers.readImage
import org.wit.personalrecipe.helpers.readImageFromPath
import org.wit.personalrecipe.helpers.showImagePicker
import org.wit.personalrecipe.main.RecipeApp
import org.wit.personalrecipe.models.RecipeModel

class RecipeActivity : AppCompatActivity(), AnkoLogger {

    var recipe = RecipeModel()
    lateinit var app: RecipeApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        app = application as RecipeApp
        var edit = false



        if (intent.hasExtra("recipe_edit")) {
            edit=true
            recipe = intent.extras.getParcelable<RecipeModel>("recipe_edit")
            recipeName.setText(recipe.name)
            recipeIngredients.setText(recipe.ingredients)
            recipeExplain.setText(recipe.recipeExplain)
            recipeImage.setImageBitmap(readImageFromPath(this, recipe.image))
            btnAdd.setText(R.string.button_saveRecipe)
            chooseImage.setText(R.string.button_changeImage)
        }

        btnAdd.setOnClickListener() {
            recipe.name = recipeName.text.toString()
            recipe.ingredients = recipeIngredients.text.toString()
            recipe.recipeExplain = recipeExplain.text.toString()
            if ( recipe.name.isNotEmpty()) {
                if (edit){
                    app.recipes.update(recipe.copy())
                }
                else {
                    app.recipes.create(recipe.copy())
                }
                info("Add Button Pressed. name: $recipeTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast (R.string.message_enter_title)
            }
        }

        chooseImage.setOnClickListener{
            showImagePicker(this, IMAGE_REQUEST)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return super.onCreateOptionsMenu(menu)    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.recipes.delete(recipe)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    recipe.image = data.getData().toString()
                    recipeImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.button_changeImage)
                }
            }
        }
    }

}