package org.onedroid.seefood.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import org.onedroid.seefood.domain.Meal

class FavoriteRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val favoritesCollection = firestore.collection("favorites")

    fun addMealToFavorites(meal: Meal, userId: String, onResult: (Boolean) -> Unit) {
        meal.idMeal?.let {
            favoritesCollection
                .document(userId)
                .collection("meals")
                .document(it)
                .set(meal)
                .addOnSuccessListener { onResult(true) }
                .addOnFailureListener { onResult(false) }
        }
    }

    fun removeMealFromFavorites(mealId: String, userId: String, onResult: (Boolean) -> Unit) {
        favoritesCollection
            .document(userId)
            .collection("meals")
            .document(mealId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun isMealFavorite(mealId: String, userId: String, onResult: (Boolean) -> Unit) {
        favoritesCollection
            .document(userId)
            .collection("meals")
            .document(mealId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun getFavoriteMeals(userId: String, onResult: (List<Meal>) -> Unit) {
        favoritesCollection
            .document(userId)
            .collection("meals")
            .get()
            .addOnSuccessListener { snapshot ->
                val meals = snapshot.toObjects(Meal::class.java)
                onResult(meals)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}