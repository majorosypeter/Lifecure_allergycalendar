package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.peter_majorosy.lifecure_allergycalendar.Firebase.FirebaseModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvDbAdapter
import kotlinx.android.synthetic.main.fragment_database.*

class DatabaseFragment : Fragment() {

    private var db = FirebaseFirestore.getInstance()
    private var collectionref = db.collection("Food")
    private lateinit var adapter: RvDbAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_database, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var options: FirestoreRecyclerOptions<FirebaseModel> =
            FirestoreRecyclerOptions.Builder<FirebaseModel>()
                .setQuery(collectionref, FirebaseModel::class.java)
                .build()

        adapter = RvDbAdapter(options)
        rv_db.layoutManager =
            LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        rv_db.adapter = adapter

        et_search.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    var options: FirestoreRecyclerOptions<FirebaseModel> =
                        FirestoreRecyclerOptions.Builder<FirebaseModel>()
                            .setQuery(collectionref, FirebaseModel::class.java)
                            .build()
                    adapter.updateOptions(options)
                } else {
                    val newref = collectionref.whereEqualTo("foodName", s.toString())
                    val searchoptions: FirestoreRecyclerOptions<FirebaseModel> =
                        FirestoreRecyclerOptions.Builder<FirebaseModel>()
                            .setQuery(newref, FirebaseModel::class.java)
                            .build()
                    adapter.updateOptions(searchoptions)
                }
            }
        })

        fab_db.setOnClickListener {
            var addFragment = DatabaseAddFragment()
            addFragment.show(fragmentManager!!, "databaseaddfragment")
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
