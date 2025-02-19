package sanchez.carlos.mydigimind_sanchezcarlos.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sanchez.carlos.mydigimind_sanchezcarlos.Carrito
import sanchez.carlos.mydigimind_sanchezcarlos.R
import sanchez.carlos.mydigimind_sanchezcarlos.Recordatorio
import sanchez.carlos.mydigimind_sanchezcarlos.databinding.FragmentDashboardBinding
import sanchez.carlos.mydigimind_sanchezcarlos.ui.home.HomeFragment

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var etName: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var btnSave: Button
    private lateinit var carrito: Carrito

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        etName = root.findViewById(R.id.etName)
        timePicker = root.findViewById(R.id.btnSetTime)
        btnSave = root.findViewById(R.id.btnSave)

        carrito = Carrito()

        btnSave.setOnClickListener {
            guardarRecordatorio()
        }

        dashboardViewModel.text.observe(viewLifecycleOwner, {

        })

        return root
    }

    fun guardarRecordatorio() {

        val nombre = etName.text.toString()

        val hora = if (Build.VERSION.SDK_INT >= 23) {
            "${timePicker.hour}:${timePicker.minute}"
        } else {
            "${timePicker.currentHour}:${timePicker.currentMinute}"
        }

        val diasSeleccionados = obtenerDiasSeleccionados()

        if (nombre.isNotEmpty() && diasSeleccionados.isNotEmpty()) {
            val recordatorio = Recordatorio(diasSeleccionados, hora, nombre)
            HomeFragment.carrito.agregar(recordatorio)

            Toast.makeText(context, "Recordatorio guardado", Toast.LENGTH_SHORT).show()

            etName.text.clear()

            HomeFragment.adapter?.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun obtenerDiasSeleccionados(): String {
        val dias = mutableListOf<String>()
        val root = view ?: return ""

        if (root.findViewById<CheckBox>(R.id.chkMonday).isChecked) dias.add("Lunes")
        if (root.findViewById<CheckBox>(R.id.chkTuesday).isChecked) dias.add("Martes")
        if (root.findViewById<CheckBox>(R.id.chkWednesday).isChecked) dias.add("Miércoles")
        if (root.findViewById<CheckBox>(R.id.chkThursday).isChecked) dias.add("Jueves")
        if (root.findViewById<CheckBox>(R.id.chkFriday).isChecked) dias.add("Viernes")
        if (root.findViewById<CheckBox>(R.id.chkSaturday).isChecked) dias.add("Sábado")
        if (root.findViewById<CheckBox>(R.id.chkSunday).isChecked) dias.add("Domingo")

        return dias.joinToString(", ")
    }

}
