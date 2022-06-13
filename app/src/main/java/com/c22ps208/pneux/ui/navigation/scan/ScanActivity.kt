package com.c22ps208.pneux.ui.navigation.scan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityScanBinding
import com.c22ps208.pneux.preferences.AppPermissions
import com.c22ps208.pneux.ui.navigation.ResultActivity
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ScanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanBinding

    private val executor = Executors.newSingleThreadExecutor()

    private val GALLERY_REQUEST_CODE = 123
    private lateinit var interpreter : Interpreter
    private lateinit var appPermissions: AppPermissions

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        appPermissions = AppPermissions()

        initMLModel()
        btnBack()

        binding.btnFile.setOnClickListener {
            if (appPermissions.isStorageOk(this)) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                Log.i(TAG, "onCreate: onresult launched")
                onresult.launch(intent)
            } else {
                Toast.makeText(this, "Please Enable Storage Permission", Toast.LENGTH_SHORT).show()
                appPermissions.requestStoragePermission(this)
            }
        }
    }

    private fun initMLModel() {
        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("ModelPneuxFinal", DownloadType.LOCAL_MODEL,
                conditions)
            .addOnSuccessListener { model: CustomModel? ->
                Log.i(TAG, "initMLModel: Model loaded successfully")

                val modelFile = model?.file
                if (modelFile != null) {
                    interpreter = Interpreter(modelFile)
                }
            }
    }

    //get image from gallery
    private val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        Log.i(TAG, "This is the result: ${result.data} ${result.resultCode}")
        onResultReceived(GALLERY_REQUEST_CODE, result)
    }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when(requestCode) {
            GALLERY_REQUEST_CODE ->{
                if(result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i(TAG, "onResultReceived: $uri")
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imgScan.setImageBitmap(bitmap)
                        binding.pbScanLoading.visibility = View.VISIBLE
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.pbScanLoading.visibility = View.GONE
                            outputGenerator(bitmap)
                        },2000)
                    }
                } else {
                    Log.e(TAG, "onResultReceived: error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
        val input = ByteBuffer.allocateDirect(150*150*3*4).order(ByteOrder.nativeOrder())
        for (y in 0 until 150) {
            for (x in 0 until 150) {
                val px = newBitmap.getPixel(x, y)

                // Get channel values from the pixel value.
                val r = Color.red(px)
                val g = Color.green(px)
                val b = Color.blue(px)

                // Normalize channel values to [-1.0, 1.0]. This requirement depends on the model.
                // For example, some models might require values to be normalized to the range
                // [0.0, 1.0] instead.
                val rf = (r - 127) / 255f
                val gf = (g - 127) / 255f
                val bf = (b - 127) / 255f

                input.putFloat(rf)
                input.putFloat(gf)
                input.putFloat(bf)
            }
        }

        val bufferSize = 1 * java.lang.Float.SIZE / java.lang.Byte.SIZE
        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
        interpreter.run(input, modelOutput)

        modelOutput.rewind()
        val probabilities = modelOutput.asFloatBuffer()
        try {
            probabilities.get().let { acc ->
                if(acc < 0.05) {
                    // Tidak Normal
                    Log.i(TAG, "outputGenerator: Tidak Normal dan $acc")
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("EXTRA_RESULT", "Tidak Normal")
                    intent.putExtra("EXTRA_PROB", acc)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    // Normal
                    Log.i(TAG, "outputGenerator: Normal dan $acc")
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("EXTRA_RESULT", "Normal")
                    intent.putExtra("EXTRA_PROB", acc)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "outputGenerator: Error generating output")
        }
    }

    private fun btnBack() {
        binding.btnBackHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy() {

        // Terminate all outstanding analyzing jobs (if there is any).
        executor.apply {
            shutdown()
            awaitTermination(1000, TimeUnit.MILLISECONDS)
        }

        super.onDestroy()
    }

    companion object {
        private val TAG = ScanActivity::class.java.simpleName
    }
}