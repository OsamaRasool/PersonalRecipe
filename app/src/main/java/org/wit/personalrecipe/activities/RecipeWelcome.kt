package org.wit.personalrecipe.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivityForResult
import org.wit.personalrecipe.R

class RecipeWelcome: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        toolbarW.title = title

        welcome.setOnClickListener() {
            startActivityForResult<RecipeListActivity>(0)
        }
    }
}