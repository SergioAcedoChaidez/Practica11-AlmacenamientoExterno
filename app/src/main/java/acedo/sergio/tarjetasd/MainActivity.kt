package acedo.sergio.tarjetasd

import acedo.sergio.tarjetasd.databinding.ActivityMainBinding
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.*
import java.lang.Exception
import java.security.Guard

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if ((ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
                    PackageManager.PERMISSION_GRANTED
                    )
            || (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !=
                    PackageManager.PERMISSION_GRANTED
                    )
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                123
            )
        }

        binding.btGuardar.setOnClickListener{
            Guardar(binding.etNuevoDato.text.toString())
            binding.tvContenido.text = Cargar()
        }
    }

    private fun Cargar(): String{
    var texto = ""
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD,"datos")
            val ficheroFisico = File(miCarpeta,"datos.txt")
            val fichero = BufferedReader(
                InputStreamReader(FileInputStream(ficheroFisico))
            )
            texto = fichero.use (BufferedReader::readText)
        }catch (e: Exception){
    //Sergio Acedo Chaidez

        }
        return texto
    }

    private fun Guardar(texto: String) {
    try {
    val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
    val miCarpeta = File(rutaSD,"datos")
        if(!miCarpeta.exists()){
            miCarpeta.mkdir()
        }
        val ficheroFisico = File(miCarpeta,"datos.txt")
        ficheroFisico.appendText("$texto\n")
    }catch (e: Exception){
        Toast.makeText(this,
        "No se ha podido Guardar",
        Toast.LENGTH_LONG).show()

    }
    }


}
