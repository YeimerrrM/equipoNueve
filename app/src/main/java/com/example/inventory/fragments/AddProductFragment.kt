package com.example.inventory.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.inventory.R
import com.google.android.material.textfield.TextInputEditText

class AddProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back arrow
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Campo código producto
        val tietCode = view.findViewById<TextInputEditText>(R.id.tiet_product_code)

        // Filtro: sólo dígitos (protege contra pegado de texto con letras)
        val onlyDigitsFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val sb = StringBuilder()
            for (i in start until end) {
                val c = source[i]
                if (c.isDigit()) sb.append(c)
            }
            // Si no se filtró nada y el source era solo dígitos, retornamos null (permitir)
            if (sb.length == end - start) null else sb.toString()
        }

        // Filtro: longitud máxima 4
        val maxLen = InputFilter.LengthFilter(4)

        // Aplicar filtros (orden: longitud, luego dígitos)
        tietCode.filters = arrayOf(maxLen, onlyDigitsFilter)

        // Opcional: listener para prevenir que el usuario sobrepase 4 caracteres (ya hace InputFilter)
        // También puedes mostrar error en el TextInputLayout si quieres:
        // val til = view.findViewById<TextInputLayout>(R.id.til_product_code)
        // tietCode.doAfterTextChanged { text -> if (text?.length == 4) til.error = null }

        // Botón guardar: ejemplo básico
        val btnSave = view.findViewById<Button>(R.id.btn_save_product)
        btnSave.setOnClickListener {
            val codeText = tietCode.text?.toString()?.trim() ?: ""
            if (codeText.isEmpty()) {
                // indicar error
                // require view binding: usar TextInputLayout.setError(...) en caso de necesitar
                // aquí básicamente solo evitamos guardar
                // Si quieres que ponga error visual:
                // val til = view.findViewById<TextInputLayout>(R.id.til_product_code)
                // til.error = "Ingresa el código"
                return@setOnClickListener
            }

            // TODO: guardar la data usando ViewModel o Repository

            // por ahora cerramos el fragment
            parentFragmentManager.popBackStack()
        }
    }
}


