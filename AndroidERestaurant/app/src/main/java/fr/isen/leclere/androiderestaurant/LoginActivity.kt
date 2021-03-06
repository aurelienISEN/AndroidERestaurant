package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityCreateAccountBinding
import fr.isen.leclere.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import fr.isen.leclere.androiderestaurant.models.DataResultAccount
import org.json.JSONObject
import java.io.File

private lateinit var binding: ActivityLoginBinding
private val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.login.setOnClickListener{
            login()
        }

        binding.creatAccounte.setOnClickListener{
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
                val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", MODE_PRIVATE)
                if(sharedPreference.getString("id_user","0") != "1"){
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, R.string.connected, duration)
                    toast.show()
                } else {
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, R.string.createAnAccount, duration)
                    toast.show()
                    val intent = Intent(this, CreateAccountActivity::class.java)
                    startActivity(intent)
                }
    }

    override fun onStop() {
        super.onStop()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.logMessageLogin))
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(TAG, getString(R.string.logMessageLogin))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_layout,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        R.id.inscription -> {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            true
        }

        R.id.connexion -> {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            true
        }

        R.id.action_cart -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.cartNb)
        val file = File(cacheDir.absolutePath + "UserCart.json")
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        var nb = 0
        if(file.exists()){
            val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
            for(truc in json.itemList){
                nb+=truc.quantity
            }
        }

        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("number", nb.toString())
        editor.apply()
        item.title =sharedPreference.getString("number","0")
        return super.onPrepareOptionsMenu(menu)
    }
}