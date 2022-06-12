package com.c22ps208.pneux.ui.navigation.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.ActivityScanBinding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ScanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanBinding
    private lateinit var bitmapBuffer: Bitmap

    private val executor = Executors.newSingleThreadExecutor()
    private val permissions = listOf(Manifest.permission.CAMERA)

    private val GALLERY_REQUEST_CODE = 123

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btnBack()

        binding.btnFile.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onresult.launch(intent)
            } else {
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    // to get permission
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {granted ->
        if(granted) {
            takePicturePreview.launch(null)
        } else {
            Toast.makeText(this@ScanActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
        }
    }

    // image previews
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {bitmap ->
        if (bitmap != null) {
            binding.imgScan.setImageBitmap(bitmap)
            outputGenerator(bitmap)
        }
    }

    //get image from gallery
    private val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        Log.i(TAG, "This is the result: ${result.data} ${result.resultCode}")
        onResultReceived(GALLERY_REQUEST_CODE)
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

        // Release TFLite resources.
        tflite.close()
        nnApiDelegate.close()

        super.onDestroy()
    }

    /** Convenience method used to check if all permissions required by this app are granted */
    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val TAG = ScanActivity::class.java.simpleName

        private const val ACCURACY_THRESHOLD = 0.5f
        private const val MODEL_PATH = "Pneu_Model_Fix.tflite"
    }
}