package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peter_majorosy.lifecure_allergycalendar.Firebase.ImageModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvGalleryAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class GalleryFragment : Fragment() {

    private lateinit var adapter: RvGalleryAdapter
    private val user = FirebaseAuth.getInstance().currentUser!!.uid
    private val documentref = FirebaseFirestore.getInstance().collection("User").document(user)
    private var modellist: MutableList<ImageModel> = mutableListOf()

    private val storageref = FirebaseStorage.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        createModelList()
        adapter = RvGalleryAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("string", "modellist: " + modellist.size.toString())

        rv_gallery.adapter = adapter
        rv_gallery.layoutManager = GridLayoutManager(context, 2)
        adapter.submitList(modellist)


/*
        var model1 = ImageModel("2020.10.11",
            "https://firebasestorage.googleapis.com/v0/b/lifecure-27450.appspot.com/o/Images%2F1606737820270.jpg?alt=media&token=ae941499-763d-41e5-b3c1-53daa5f34bef")
        var model2 = ImageModel("2020.10.11",
            "https://firebasestorage.googleapis.com/v0/b/lifecure-27450.appspot.com/o/Images%2F1606737820270.jpg?alt=media&token=ae941499-763d-41e5-b3c1-53daa5f34bef")
        var model3 = ImageModel("2020.10.11",
            "https://firebasestorage.googleapis.com/v0/b/lifecure-27450.appspot.com/o/Images%2F1606737820270.jpg?alt=media&token=ae941499-763d-41e5-b3c1-53daa5f34bef")
        var model4 = ImageModel("2020.10.11",
            "https://firebasestorage.googleapis.com/v0/b/lifecure-27450.appspot.com/o/Images%2F1606737820270.jpg?alt=media&token=ae941499-763d-41e5-b3c1-53daa5f34bef")
        var model5 = ImageModel("2020.10.11",
            "https://firebasestorage.googleapis.com/v0/b/lifecure-27450.appspot.com/o/Images%2F1606737820270.jpg?alt=media&token=ae941499-763d-41e5-b3c1-53daa5f34bef")

        val model = listOf(model1, model2, model3, model4, model5)

         Log.d("string", storageref.getReferenceFromUrl("gs://lifecure-27450.appspot.com/Images/1606769717890.jpg").toString())
        storageref.getReferenceFromUrl("gs://lifecure-27450.appspot.com/Images/1606769717890.jpg").metadata.addOnSuccessListener { metadata ->
            val date = metadata.creationTimeMillis.toString()
            Log.d("string", date)
*/


    }


    private fun createModelList() {
        documentref.get().addOnSuccessListener { snapshot ->
            val smt = snapshot.get("imageReferences").toString()

            var list = smt.split(",").map { it.trim() }
            modellist = mutableListOf()

            (list.indices).forEach { i ->
                var iterator = list[i].replace("[", "").replace("]", "")

                storageref.getReferenceFromUrl(iterator).metadata.addOnSuccessListener { metadata ->
                    val timestamp = metadata.creationTimeMillis.toString()  //TODO: dátummá alakítani

                    modellist.add(ImageModel(timestamp, iterator))
                }.addOnFailureListener {
                    Toast.makeText(context!!, "metadata hiba", Toast.LENGTH_SHORT)
                }
            }
        }.addOnFailureListener{
            Toast.makeText(context!!, "dokumentum hiba", Toast.LENGTH_SHORT)
        }

    }

}