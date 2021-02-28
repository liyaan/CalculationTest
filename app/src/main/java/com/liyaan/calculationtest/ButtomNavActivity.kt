package com.liyaan.calculationtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_buttom_nav.*

class ButtomNavActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttom_nav)
        val navControl = Navigation.findNavController(findViewById(R.id.fragment3))
        val configuration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this,navControl,configuration)
        NavigationUI.setupWithNavController(bottomNavigationView,navControl)
    }
}