package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.ktx.storage
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

private const val PERMISSION_CODE = 1
private const val REQ_CODE = 2

class CameraFragment : Fragment() {

    private lateinit var currentPhotoPath: String
    private val storageref = FirebaseStorage.getInstance().reference.child("Images")
    private lateinit var uri : Uri
    private val auth = FirebaseAuth.getInstance().currentUser!!.uid
    private val collectionref = FirebaseFirestore.getInstance().collection("User").document(auth)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_photo.setOnClickListener {
            askPermission()
        }

        btn_upload.setOnClickListener {
            uploadImage(uri)
        }

    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE && resultCode == Activity.RESULT_OK) {
            var file = File(currentPhotoPath)
            uri = Uri.fromFile(file)

            image_preview.setImageURI(Uri.fromFile(file))
        }
    }

    private fun uploadImage( uri: Uri?) {
        val progressBar = ProgressDialog(this.context!!)
        progressBar.setMessage("Uploading")
        progressBar.show()

        Log.d("uri", uri.toString())

        if (uri != null) {
            val fileRef = storageref.child(System.currentTimeMillis().toString() + ".jpg")
            fileRef.putFile(uri).addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d(tag, "Success: URL = $uri")
                    Toast.makeText(this.context!!, "Uploaded", Toast.LENGTH_SHORT).show()

                    var update = hashMapOf(
                        "imageReferences" to "")

                    collectionref.update("imageReferences", FieldValue.arrayUnion(uri.toString()))

                    progressBar.dismiss()
                }

            }.addOnFailureListener {
                Toast.makeText(this.context!!, "Upload failed", Toast.LENGTH_SHORT).show()
                progressBar.dismiss()
            }

        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
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