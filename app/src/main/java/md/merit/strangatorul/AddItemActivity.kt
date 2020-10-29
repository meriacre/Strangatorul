package md.merit.strangatorul

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_item.*
import java.util.*

class AddItemActivity : AppCompatActivity() {
private lateinit var settingsSaveData:SettingsSaveData

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsSaveData = SettingsSaveData(this)
        if (settingsSaveData.loadDarkMode() == true){
            setTheme(R.style.DarkTheme)
        }else{
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val correctMonthDefault = month+1
        tv_add_date.setText(""+ day +"/" + correctMonthDefault + "/" + year )
        btnCalendar.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                var correctMonth = mMonth + 1
                tv_add_date.setText(""+ mDay +"/" + correctMonth + "/" + mYear)
            }, year, month, day)
            dpd.show()
        }

        btn_save.setOnClickListener{
            if(edt_add_name.text.isEmpty()){
                Toast.makeText(this, "Please enter transaction name!", Toast.LENGTH_SHORT).show()
                edt_add_name.requestFocus()
            } else{
                val transaction = Transaction()
                transaction.itemTitle = edt_add_name.text.toString()
                transaction.itemDate =tv_add_date.text.toString()
                transaction.itemDescription = edt_add_description.text.toString()
               if(edt_add_price.text.isEmpty()){
                   transaction.itemPrice= 0.0
               }else {
                   transaction.itemPrice = edt_add_price.text.toString().toDouble()
               }
                MainActivity.dbHandler.addTransaction(this,transaction)
                clearEdits()
                edt_add_name.requestFocus()
            }
        }

        btn_cancel.setOnClickListener {
            clearEdits()
            finish()
        }

        tv_curency_display.text = MainActivity.currencySign
    }


    private fun clearEdits(){
        edt_add_name.text.clear()
        edt_add_description.text.clear()
        edt_add_price.text.clear()
    }


}