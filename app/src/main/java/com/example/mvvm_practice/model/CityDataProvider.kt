package com.example.mvvm_practice.model

import com.example.mvvm_practice.R

class CityDataProvider
{
    private val cities = arrayListOf<City>()

    init{
        cities.add(City("Bangkok", R.drawable.bangkok, 10_000_000))
        cities.add(City("London", R.drawable.london, 8_900_000))
        cities.add(City("Paris", R.drawable.paris, 2_100_000))
        cities.add(City("Tokyo", R.drawable.tokyo, 13_900_000))
        cities.add(City("New York", R.drawable.newyork, 8_400_000))
        cities.add(City("Sydney", R.drawable.sydney, 5_300_000))
        cities.add(City("Dubai", R.drawable.rio, 10_000_000))
        cities.add(City("Beijing", R.drawable.beijing, 21_500_000))
        cities.add(City("Hong Kong", R.drawable.rome, 21_500_000))
        cities.add(City("Singapore", R.drawable.singapore, 21_500_000))
    }

    fun getCities() = cities
}