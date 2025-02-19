package sanchez.carlos.mydigimind_sanchezcarlos.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sanchez.carlos.mydigimind_sanchezcarlos.Carrito
import sanchez.carlos.mydigimind_sanchezcarlos.R
import sanchez.carlos.mydigimind_sanchezcarlos.Recordatorio
import sanchez.carlos.mydigimind_sanchezcarlos.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var gridView: GridView
    private lateinit var adapter: RecordatorioAdapter

    companion object {
        var carrito = Carrito()
        var adapter: RecordatorioAdapter? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        gridView = root.findViewById(R.id.gridview)

        adapter = RecordatorioAdapter(requireContext(), carrito.recordatorios)
        gridView.adapter = adapter

        homeViewModel.text.observe(viewLifecycleOwner, {

        })

        return root
    }

    class RecordatorioAdapter(private val context: Context, private val recordatorios: List<Recordatorio>) : BaseAdapter() {

        override fun getCount(): Int = recordatorios.size

        override fun getItem(position: Int): Any = recordatorios[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout, parent, false)

            val tvNombre = view.findViewById<TextView>(R.id.txtNombreRecordatorio)
            val tvDias = view.findViewById<TextView>(R.id.txtDiasRecordatorio)
            val tvTiempo = view.findViewById<TextView>(R.id.txtTiempoRecordatorio)

            val recordatorio = recordatorios[position]

            tvNombre.text = recordatorio.nombre
            tvDias.text = "Días: ${recordatorio.dias}"
            tvTiempo.text = "Hora: ${recordatorio.tiempo}"

            return view
        }
    }

}
