package com.example.myfridge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myfridge.api.getProduct
import com.example.myfridge.data.Fridge
import com.example.myfridge.data.NewProduct
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


class ActivityAddPersistentData : AppCompatActivity() {

    lateinit var fridge: Fridge
    lateinit var layout: View
    var stock: String = "Fridge"
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    val options = BarcodeScannerOptions.Builder()
        .enableAllPotentialBarcodes() // Optional
        .build()

    val scanner = BarcodeScanning.getClient(options)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        fridge = Fridge(filesDir)
        fridge.loadData()
        layout = findViewById(R.id.activity_add_new_layout)
        val intent: Intent = intent

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val nameText: EditText = findViewById(R.id.editTextNewProductName)
        val quantityText: EditText = findViewById(R.id.editTextNewProductQuantity)
        val dateText: EditText = findViewById(R.id.editTextNewProductDate)
        val productCodeText: EditText = findViewById(R.id.editTextBarcode)
        val cameraButton: ImageView = findViewById(R.id.cameraButton)
        val buttonAdd: View = findViewById(R.id.buttonAddElement)
        val buttonApi: View = findViewById(R.id.buttonGetProduct)

        val stock: String? = intent.getStringExtra("Stock")

        var takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val result = scanner.process(InputImage.fromBitmap(imageBitmap, 0))
                        .addOnSuccessListener { barcodes ->
                            for (barcode in barcodes) {
                                val bounds = barcode.boundingBox
                                val corners = barcode.cornerPoints

                                val rawValue = barcode.rawValue

                                val valueType = barcode.valueType
                                productCodeText.setText(rawValue)
                                Log.i("barcode", "Barcode : ${rawValue}")
                                // See API reference for complete list of supported types
                                when (valueType) {
                                    Barcode.TYPE_WIFI -> {
                                        val ssid = barcode.wifi!!.ssid
                                        val password = barcode.wifi!!.password
                                        val type = barcode.wifi!!.encryptionType
                                    }

                                    Barcode.TYPE_URL -> {
                                        val title = barcode.url!!.title
                                        val url = barcode.url!!.url
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { Log.e("barcode", "No code found in image") }
                }
            }

        dateText.setOnClickListener {
            layout.clearFocus()
            imm.hideSoftInputFromWindow(layout.windowToken, 0)
            val newFragment = DatePickerFragment.newInstance(dateText)

            newFragment.show(supportFragmentManager, "datePicker")
        }

        buttonAdd.setOnClickListener {
            var newProduct = NewProduct(
                name = nameText.text.toString(),
                quantity = quantityText.text.toString(),
                date = dateText.text.toString(),
                stock = this.stock
            )
            fridge.addItem(newProduct)
            logData()
            finish()
        }

        buttonApi.setOnClickListener {
            Log.i("contenu", "Call api")
            getProduct(productCodeText.text.toString(), nameText)
        }

        cameraButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(takePictureIntent)
        }

    }

    fun logData() {
        Log.i("debug", fridge.getDataString())
    }

//    fun clearJson() {
//        fridge.clearAll()
//    }

}
