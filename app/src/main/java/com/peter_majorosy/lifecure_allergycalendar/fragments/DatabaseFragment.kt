package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.peter_majorosy.lifecure_allergycalendar.Firebase.FirebaseModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvDbAdapter
import kotlinx.android.synthetic.main.fragment_database.*
import kotlinx.android.synthetic.main.fragment_database.view.*

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

        setUpRv()

        fab_db.setOnClickListener {
            var addFragment = DatabaseAddFragment()
            addFragment.show(fragmentManager!!, "databaseaddfragment")
        }
    }

    private fun setUpRv() {
        var query = collectionref
        var options: FirestoreRecyclerOptions<FirebaseModel> =
            FirestoreRecyclerOptions.Builder<FirebaseModel>()
                .setQuery(query, FirebaseModel::class.java)
                .build()

        adapter = RvDbAdapter(options)
        rv_db.layoutManager =
            LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        rv_db.adapter = adapter
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
