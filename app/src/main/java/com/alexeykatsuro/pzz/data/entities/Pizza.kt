package com.alexeykatsuro.pzz.data.entities


data class Pizza(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: String,
    val photo: String,
    val variants: PizzaVariants
)
