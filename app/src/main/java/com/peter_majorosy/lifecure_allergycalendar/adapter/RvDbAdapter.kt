package com.peter_majorosy.lifecure_allergycalendar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.peter_majorosy.lifecure_allergycalendar.Firebase.FirebaseModel
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.rv_db_rov.view.*

class RvDbAdapter(options: FirestoreRecyclerOptions<FirebaseModel>) :
    FirestoreRecyclerAdapter<FirebaseModel, RvDbAdapter.ViewHolder>(options) {

    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()
    private var ref = db.collection("User")
    private val currentuser = auth.currentUser?.uid.toString()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvname: TextView = itemView.tv_dbname
        val tvcontent: TextView = itemView.tv_dbcontent
        val tvuser: TextView = itemView.tv_dbuser
        val btnlike: ImageView = itemView.btn_like
        val btndislike: ImageView = itemView.btn_dislike
        var cntrdislike: TextView = itemView.tv_dislikecntr
        var cntrlike: TextView = itemView.tv_likecntr
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_db_rov, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: FirebaseModel) {
        //default értékek betöltése, az újrafelhasználási hiba elkerülésére
        holder.btnlike.setImageResource(R.drawable.ic_like_off)
        holder.btndislike.setImageResource(R.drawable.ic_dislike_off)

        holder.tvname.text = model.foodName
        holder.tvcontent.text = model.ingredients
        holder.tvuser.text = model.author
        holder.cntrlike.text = model.likes.toString()
        holder.cntrdislike.text = model.dislikes.toString()


        ref.document(currentuser).get().addOnSuccessListener { snapshot ->
            if (snapshot != null) {
                //létezik-e az adott User listájában az adott dokumentumId(Food), mi az értéke(Like/Dislike)
                val docid = snapshot.getString(model.id)
                if (docid != null) {
                    if (docid.toInt() == 1) {
                        holder.btnlike.setImageResource(R.drawable.ic_like_on)
                    } else {
                        holder.btndislike.setImageResource(R.drawable.ic_dislike_on)
                    }
                }
            } else {
                holder.btnlike.setImageResource(R.drawable.ic_like_off)
                holder.btnlike.setImageResource(R.drawable.ic_like_off)
            }


            holder.btnlike.setOnClickListener {
                //uid alapján user megkeresése
                ref.document(currentuser).get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot != null) {
                            //létezik-e az adott User listájában az adott dokumentumId(Food), mi az értéke(Like/Dislike)
                            val docid = snapshot.getString(model.id)
                            Log.d("docid", docid.toString())
                            if (docid != null) {
                                Log.d("status", "docid != null")
                                if (docid.toInt() == 1) {
                                    //Likeolta már, levenni a likeot, törölni az user dokumentumából az id-t, Csökkenteni a likeok számát 1-gyel
                                    holder.btnlike.setImageResource(R.drawable.ic_like_off)
                                    val updates = hashMapOf<String, Any>(
                                        model.id to FieldValue.delete())
                                    ref.document(currentuser).update(updates)
                                    decreaseLikes(model)

                                } else if (docid.toInt() == 0) {
                                    //dislikeolta már, likeolni, levenni a dislikeot, dokumentum id-hoz tartozó értéket 1-be állítani, csökkenteni a dislikeot, növelni a likeot
                                    holder.btnlike.setImageResource(R.drawable.ic_like_on)
                                    holder.btndislike.setImageResource(R.drawable.ic_dislike_off)
                                    ref.document(currentuser)
                                        .update(model.id, "1")
                                    increaseLikes(model)
                                    decreaseDislikes(model)
                                }
                            } else {
                                Log.d("status", "nemletezik")
                                //nem létezik a listájában ->  Likeolni, hozzáadni az adatbázishoz a dokumentum(Food) id-ját, 1-be állítani, növelni a likeot
                                holder.btnlike.setImageResource(R.drawable.ic_like_on)
                                ref.document(currentuser)
                                    .update(model.id, "1")
                                increaseLikes(model)
                            }
                        }
                    }
            }

            holder.btndislike.setOnClickListener {
                //uid alapján user megkeresése
                ref.document(currentuser).get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot != null) {
                            //létezik-e az adott User listájában az adott dokumentumId(Food), mi az értéke(Like/Dislike)
                            val docid = snapshot.getString(model.id)

                            Log.d("dokumentumid", docid.toString())
                            if (docid != null) {
                                if (docid.toInt() != 1) {
                                    //Disikeolta már, levenni a dislikeot, törölni az user dokumentumából az id-t, csökkenteni a dislikeot
                                    holder.btndislike.setImageResource(R.drawable.ic_dislike_off)
                                    val updates = hashMapOf<String, Any>(
                                        model.id to FieldValue.delete())
                                    ref.document(currentuser).update(updates)
                                    decreaseDislikes(model)
                                } else {
                                    //likeolta már, dislikeolni, levenni a likeot, dokumentum id-hoz tartozó értéket 0-ba állítani, növelni a dislikeot, csökkenteni a likeot
                                    holder.btnlike.setImageResource(R.drawable.ic_like_off)
                                    holder.btndislike.setImageResource(R.drawable.ic_dislike_on)
                                    ref.document(currentuser)
                                        .update(model.id, "0")
                                    increaseDislikes(model)
                                    decreaseLikes(model)
                                }
                            } else {
                                //nem létezik a listájában ->  dislikeolni, hozzáadni az adatbázishoz a dokumentum(Food) id-ját, 0-ba állítani, növelni a dislikeot
                                holder.btndislike.setImageResource(R.drawable.ic_dislike_on)
                                val update: MutableMap<String, Any> = HashMap()
                                update[model.id] = "0"
                                ref.document(currentuser).update(update)
                                increaseDislikes(model)
                            }
                        }
                    }
            }
        }
    }

    private fun decreaseLikes(model: FirebaseModel) {
        val update = hashMapOf<String, Any>(
            "likes" to FieldValue.increment(-1)
        )
        db.collection("Food").document(model.id).update(update)
    }

    private fun decreaseDislikes(model: FirebaseModel) {
        val update = hashMapOf<String, Any>(
            "dislikes" to FieldValue.increment(-1)
        )
        db.collection("Food").document(model.id).update(update)
    }

    private fun increaseLikes(model: FirebaseModel) {
        val update = hashMapOf<String, Any>(
            "likes" to FieldValue.increment(1)
        )
        db.collection("Food").document(model.id).update(update)
    }

    private fun increaseDislikes(model: FirebaseModel) {
        val update = hashMapOf<String, Any>(
            "dislikes" to FieldValue.increment(1)
        )
        db.collection("Food").document(model.id).update(update)
    }
}
