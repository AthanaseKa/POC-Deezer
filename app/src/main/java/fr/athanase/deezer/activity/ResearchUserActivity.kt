package fr.athanase.deezer.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fr.athanase.deezer.R
import fr.athanase.deezer.Tools
import fr.athanase.deezer.coroutine.DeezerManager
import fr.athanase.deezer.databinding.ResearchUserActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResearchUserActivity: AppCompatActivity() {
    private lateinit var binding: ResearchUserActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.research_user_activity)

        binding.researchUserButton.setOnClickListener {
            binding.researchUserEditText.text.toString().toIntOrNull()?.let { id ->
                fetchUser(id)
            }
        }
    }

    private fun fetchUser(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { DeezerManager.fetchUser(id) }?.let {
                displayToast(it.name)
                switchActivity(it.id)
            } ?: displayToast("Oups")
            /*DeezerManager.fetchUser(id)?.let {
                displayToast(it.name)
                switchActivity(it.id)
            } ?: displayToast("Oups")*/
        }
    }

    private fun switchActivity(userId: Int) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(Tools.PREFERENCE_USER_ID, userId).apply()
        val intent = Intent(this, DisplayUserActivity::class.java)
        startActivity(intent)
    }

    private fun displayToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}