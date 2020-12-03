package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peter_majorosy.lifecure_allergycalendar.Firebase.ImageModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvGalleryAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.text.SimpleDateFormat


class GalleryFragment : Fragment() {

    private lateinit var adapter: RvGalleryAdapter
    private val user = FirebaseAuth.getInstance().currentUser!!.uid
    private val documentref = FirebaseFirestore.getInstance().collection("User").document(user)
    private val storageref = FirebaseStorage.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        createModelList()
        adapter = RvGalleryAdapter(context!!, mutableListOf())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_gallery.adapter = adapter
        rv_gallery.layoutManager = GridLayoutManager(context, 2)
    }


    @SuppressLint("SimpleDateFormat")
    private fun createModelList() {
        documentref.get().addOnSuccessListener { snapshot ->
            val smt = snapshot.get("imageReferences").toString()


            //ha nincs elmentett fénykép, ne próbáljon keresni
            if (smt != "null" && smt != "[]") {
                val list = smt.split(",").map { it.trim() }

                //lista elemeinek megfelelően adatmodel összeállítása
                (list.indices).forEach { i ->
                    val iterator = list[i].replace("[", "").replace("]", "")
                    storageref.getReferenceFromUrl(iterator).metadata.addOnSuccessListener { metadata ->
                        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
                        val date = simpleDateFormat.format(metadata.creationTimeMillis)

                        adapter.addItem(ImageModel(date, iterator))
                    }.addOnFailureListener {
                        errorToast()
                    }
                }
            } else {
                errorToast()
            }
        }.addOnFailureListener {
            errorToast()
        }

    }

    private fun errorToast() {
        Toast.makeText(context!!, "Couldn't find images to load.", Toast.LENGTH_SHORT).show()
    }
}