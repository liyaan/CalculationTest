package com.liyaan.calculationtest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.liyaan.calculationtest.paging.PagingActivity

class MainActivity: AppCompatActivity() {
    var controler:NavController? = null
    var button19:Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controler = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,controler!!)
        button19 = findViewById(R.id.button19)
        button19!!.setOnClickListener {
            val intent = Intent(this@MainActivity,PagingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (controler!!.currentDestination!!.id == R.id.questionFragment){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.quit_dialog_title))
            alertDialog.setPositiveButton(R.string.dialog_positive_message) { dialog, which ->
                controler!!.navigateUp()
            }
            alertDialog.setNegativeButton(R.string.dialog_negative_message){dialog, which ->

            }
            val dialog = alertDialog.create()
            dialog.show()
        }else if (controler!!.currentDestination!!.id == R.id.tiltleFragment){
            finish()
        }else{
            controler!!.navigate(R.id.tiltleFragment)
        }

        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}