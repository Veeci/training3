package com.example.mvvm_ordermanagement.model

import kotlin.random.Random

class OrderDataGenerator
{
    private val currentUser = arrayListOf<User>()
    private val currentProduct = arrayListOf<Product>()
    private val currentOrder = arrayListOf<Order>()

    init {
        for(i in 1..10)
        {
            generateUsers()
            generateProducts()
        }

        for(i in 1..100)
        {
            generateOrders()
        }
    }

    private fun generateUsers()
    {
        val index = Random.nextInt(usersList.size)
        val userName = usersList[index]
        val email = "${userName}@gmail.com"
        val userID = 1000 + currentUser.size

        currentUser.add(User(userID, userName, email))
    }

    private fun generateProducts()
    {
        val brandIndex = Random.nextInt(brandsList.size)
        val brandName = brandsList[brandIndex]
        val productIndex = Random.nextInt(productsList.size)
        val productName = productsList[productIndex]
        val price = Random.nextInt(1000)
        val productID = 2000 + currentProduct.size

        currentProduct.add(Product(productID, productName, brandName, price))
    }

    private fun generateOrders()
    {
        val user = currentUser[Random.nextInt(currentUser.size)]
        val productIndex = currentProduct[Random.nextInt(currentProduct.size)]
        val orderID = 3000 + currentOrder.size

        currentOrder.add(Order(orderID, user, productIndex))
    }
}