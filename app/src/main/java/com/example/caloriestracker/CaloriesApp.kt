package com.example.caloriestracker

import android.app.Application

class CaloriesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}