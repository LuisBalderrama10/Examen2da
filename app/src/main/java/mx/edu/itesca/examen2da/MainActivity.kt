package mx.edu.itesca.examen2da

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private var desc: EditText? = null;
    private var precio: EditText? = null;
    private var cant: EditText? = null;
    private var iva: TextView? = null;
    private var subtotal: TextView? = null;
    private var total: TextView? = null;
    private var regis: Button? =null;
    private var fact: Button? =null;
    private var lvDatos: ListView? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        desc = findViewById<View>(R.id.descripcion) as EditText
        precio = findViewById<View>(R.id.precio) as EditText
        cant = findViewById<View>(R.id.cantidad) as EditText
        total = findViewById<View>(R.id.total) as TextView
        iva = findViewById<View>(R.id.iva) as TextView
        subtotal = findViewById<View>(R.id.subtotal) as TextView
        regis = findViewById<View>(R.id.registrar) as Button
        fact = findViewById<View>(R.id.facturar) as Button
        lvDatos = findViewById<View>(R.id.lvDatos) as ListView
        botonRegistrar()
        listardatos()
    }
    private fun botonRegistrar() {
        regis?.setOnClickListener(View.OnClickListener {
            if (desc?.getText().toString().trim { it <= ' ' }.isEmpty()
                || precio?.getText().toString().trim { it <= ' ' }.isEmpty()
                || cant?.getText().toString().trim { it <= ' ' }.isEmpty()
            ) {
                ocultarTeclado()
                Toast.makeText(this@MainActivity, "Complete Los Campos Faltantes!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val des: String = desc?.getText().toString()
                val pre: Float = precio?.getText().toString().toFloat()
                val can: Int = cant?.getText().toString().toInt()
                val db: FirebaseDatabase = FirebaseDatabase.getInstance()
                val dbref: DatabaseReference = db.getReference(datos::class.java.getSimpleName())
                dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                            val dat = datos(pre,can,des)
                            dbref.push().setValue(dat)
                            ocultarTeclado()
                            Toast.makeText(this@MainActivity,
                                "Prducto Registrado Correctamente!!", Toast.LENGTH_SHORT).show()
                        desc?.setText("")
                        precio?.setText("")
                        cant?.setText("")
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            } // Cierra el if/else inicial.
        })
    } // Cierra el método botonRegistrar
    private fun listardatos() {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val dbref: DatabaseReference = db.getReference(datos::class.java.getSimpleName())
        val lisdato = ArrayList<datos>()
        val ada = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, lisdato)
        lvDatos?.setAdapter(ada)
        dbref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val luc: datos = snapshot.getValue(datos::class.java)!!
                lisdato.add(luc)
                ada.notifyDataSetChanged()
                facturar(lisdato)
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { ada.notifyDataSetChanged() }
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun ocultarTeclado() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    private fun facturar(lisdato: ArrayList<datos>) {
        fact?.setOnClickListener(View.OnClickListener {
                val db: FirebaseDatabase = FirebaseDatabase.getInstance()
                var iva2:Double=0.16
                var sub2:Float = 0.0F
                var tot2:Float
                val dbref: DatabaseReference = db.getReference(factura::class.java.getSimpleName())
                dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(a in lisdato){
                            sub2+=a.precio
                        }
                        tot2=(sub2.toFloat()*iva2.toFloat())+sub2
                        iva2*=sub2
                        val dat = factura(0.16.toFloat(),sub2,tot2)
                        dbref.push().setValue(dat)
                        ocultarTeclado()
                        Toast.makeText(this@MainActivity,
                            "Factura Registrado!!", Toast.LENGTH_SHORT).show()
                        iva?.setText("${iva2.toString()}")
                        subtotal?.setText(sub2.toString())
                        total?.setText(tot2.toString())
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
        })

    }
}