package com.peter_majorosy.lifecure_allergycalendar.Firebase

data class FirebaseModel(
    var foodName: String = "",
    var ingredients: String = "",
    var author: String = "",
    var id: String = "",
    var likes: Int = 0,
    var dislikes: Int = 0
)