package com.trilema.doctrinalang

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trilema.doctrinalang.support.SharedPref
import com.trilema.doctrinalang.support.Tagarela
import com.trilema.doctrinalang.ui.AboutFragmet
import java.util.*

class MainActivity : AppCompatActivity() {
    private val ACT_CHECK_TTS_DATA = 1000
    private var btnInfo: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val spinner = findViewById<Spinner>(R.id.spinFlag)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_time, R.id.navigation_position)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController)

        //add info
        btnInfo = findViewById(R.id.btnInfo)
        btnInfo?.setOnClickListener(View.OnClickListener {
            val aboutFragment: DialogFragment = AboutFragmet()
            aboutFragment.show(supportFragmentManager, "about")
        })

        // Check to see if we have TTS voice data
        val ttsIntent = Intent()
        ttsIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(ttsIntent, ACT_CHECK_TTS_DATA)

        //prepare spinner with flags
        var sp = SharedPref()
        val imageName = arrayOf("le français", "Deutsch")
        val image = intArrayOf(R.drawable.flag_france, R.drawable.flag_germany)
        val spinnerCustomAdapter = SpinnerCustomAdapter(applicationContext, image, imageName);
        spinner.adapter = spinnerCustomAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sp.setLanguage(applicationContext,position)
                Tagarela.ACTUAL_LANGUAGE = position
                Tagarela.checkListener()
            }

        }
        spinner.setSelection(sp.getLanguage(applicationContext))
        Tagarela.context = applicationContext

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACT_CHECK_TTS_DATA) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Data exists, so we instantiate the TTS engine
                Tagarela.tts = TextToSpeech(this) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        val result = Tagarela.tts?.setLanguage(Locale.FRENCH)
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(applicationContext, "This language is not supported!",
                                    Toast.LENGTH_SHORT)
                        }
                    }
                }
            } else {
                // Data is missing, so we start the TTS
                // installation process
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }

    override fun onDestroy() {
        if (Tagarela.tts != null) {
            Tagarela.tts!!.stop()
            Tagarela.tts!!.shutdown()
        }
        super.onDestroy()
    }

    class SpinnerCustomAdapter(private var context: Context, private var flags: IntArray, private var Network: Array<String>) : BaseAdapter() {
        private var inflater: LayoutInflater = LayoutInflater.from(context)
        override fun getCount(): Int {
            return flags.size
        }

        override fun getItem(i: Int): Any? {
            return null
        }

        override fun getItemId(i: Int): Long {
            return 0
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            view = inflater.inflate(R.layout.flag_spinner_item, null)
            val icon = view.findViewById(R.id.flagSpinImg) as ImageView
            val names = view.findViewById(R.id.flagPinText) as TextView
            icon.setImageResource(flags[i])
            names.text = Network[i]
            return view
        }
    }

}