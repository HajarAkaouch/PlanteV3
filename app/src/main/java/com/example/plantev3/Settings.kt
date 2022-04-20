package com.example.plantev3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Settings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val humidSetSpin:Spinner = findViewById(R.id.HumidSet)
        val uviSetSpin:Spinner = findViewById(R.id.UviSet)

        val tempSetText: AppCompatEditText = findViewById(R.id.TempSet)
        val nameSetText: AppCompatEditText = findViewById(R.id.plantNameSet)

        val confirmBtn: Button = findViewById(R.id.confirmBtn)

        val humidStrings = resources.getStringArray(R.array.HumidSettings)
        val uviStrings = resources.getStringArray(R.array.UviSettings)

        var humid:String = ""
        var uvi: String = ""

        val planteDao = (application as prefPlanteApp).db.employeeDao()

        lifecycleScope.launch {
            planteDao.fetchAllPref().collect {
                Log.d("exactemployee", "$it")
                val list = ArrayList(it)
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.HumidSettings,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            humidSetSpin.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.UviSettings,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            uviSetSpin.adapter = adapter
        }

        humidSetSpin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                humid = humidStrings[position]

                Toast.makeText(this@Settings, getString(R.string.selected_item) + " " + "" + humidStrings[position], Toast.LENGTH_SHORT).show()

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        uviSetSpin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                uvi= uviStrings[position]
                Toast.makeText(this@Settings, getString(R.string.selected_item) + " " + "" + humidStrings[position], Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        confirmBtn.setOnClickListener {

            addRecord(planteDao,humid =  humid, temp = tempSetText.text.toString(), uvi = uvi, name = nameSetText.text.toString())
            Constants.maPlante = Plante(nameSetText.text.toString(), humid, tempSetText.text.toString(), uvi)
            finish()
        }

    }

    fun addRecord(planteDao: PlanteDao, humid:String, temp:String, uvi:String, name:String) {

        if (name.isNotEmpty() && temp.isNotEmpty() && humid.isNotEmpty() && uvi.isNotEmpty()) {
            lifecycleScope.launch {
                planteDao.insert(PlantePreference(name = name, humidPref = humid, uviPref = uvi, tempPref = temp))
                Toast.makeText(applicationContext, "Préférence sauvegradé", Toast.LENGTH_LONG).show()

            }
        } else {
            Toast.makeText(
                applicationContext,
                "Champ obligatoire manquant",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}



