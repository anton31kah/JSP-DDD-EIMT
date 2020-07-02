package com.example.ridemanagement.application.form

data class CheckRideForm(
        val conductorId: String,
        val rideId: String,
        val ticketsIds: List<String>
)
