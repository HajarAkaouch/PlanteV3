package com.example.plantev3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText


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
                Toast.makeText(this@Settings, getString(R.string.selected_item) + " " + "" + humidStrings[position], Toast.LENGTH_SHORT
                ).show()
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
                humid = uviStrings[position]
                Toast.makeText(this@Settings, getString(R.string.selected_item) + " " + "" + humidStrings[position], Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        confirmBtn.setOnClickListener {
            if (nameSetText.text.toString().isEmpty() || tempSetText.text.toString().isEmpty()){
                Toast.makeText(this,"Champ obligatoire manquant", Toast.LENGTH_SHORT).show()
            }else{
                myPlante = Plante(nameSetText.text.toString(),humid,tempSetText.text.toString(),uvi)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }

    }

}



