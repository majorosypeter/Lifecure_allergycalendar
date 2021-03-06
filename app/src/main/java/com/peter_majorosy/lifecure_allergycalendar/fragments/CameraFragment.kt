package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val PERMISSION_CODE = 1
private const val REQ_CODE = 2

class CameraFragment : Fragment() {

    private lateinit var currentPhotoPath: String
    private val storageref = FirebaseStorage.getInstance().reference.child("Images")
    private var uri: Uri? = null
    private val auth = FirebaseAuth.getInstance().currentUser!!.uid
    private val collectionref = FirebaseFirestore.getInstance().collection("User").document(auth)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_photo.setOnClickListener {
            askPermission()
        }

        btn_upload.setOnClickListener {
            //készült-e már fénykép?
            if (uri != null) {
                uploadImage(uri)
            } else {
                Toast.makeText(context, "Take a picture first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Engedélykérés
    private fun askPermission() {
        //van-e már engedély?
        if (ContextCompat.checkSelfPermission(this.context!!,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity!!,
                arrayOf(android.Manifest.permission.CAMERA),
                PERMISSION_CODE)
        } else {
            dispatchTakePictureIntent()
        }
    }

    //Engedélykérés eredményének kiértékelése
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.size < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this.context!!,
                    "Permission is needed for using the camera",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Fényképezés eredményének kiértékelése
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE && resultCode == Activity.RESULT_OK) {
            val file = File(currentPhotoPath)
            uri = Uri.fromFile(file)

            image_preview.setImageURI(Uri.fromFile(file))
        }
    }

    //Fénykép feltöltése
    private fun uploadImage(uri: Uri?) {
        val progressBar = ProgressDialog(this.context!!)
        progressBar.setMessage("Uploading")
        progressBar.show()

        val fileRef = storageref.child(System.currentTimeMillis().toString() + ".jpg")
        val uploadtask = fileRef.putFile(uri!!)
        progressBar.setOnCancelListener {
            //A felhasználó megszakítja a folyamatot
            uploadtask.cancel()
        }
        uploadtask.addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d(tag, "Success: URL = $uri")
                Toast.makeText(this.context, "Uploaded", Toast.LENGTH_SHORT).show()

                collectionref.update("imageReferences", FieldValue.arrayUnion(uri.toString()))

                progressBar.dismiss()
            }
        }.addOnFailureListener {
            Toast.makeText(this.context, "Upload failed", Toast.LENGTH_SHORT).show()
            progressBar.dismiss()
        }

        Log.d("uri", uri.toString())
    }

    //developer.android.com dokumentáció szerinti képkészítés
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    //Képfájl létrehozása
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Fájl mentése meghatározott path alapján történik
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Van-e kamera alkalmazás?
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // A fénykép fájl elkészítése
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Hiba a file készítése közben
                    null
                }
                // Ha sikeresen elkészült a file
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this.context!!,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQ_CODE)
                }
            }
        }
    }
}