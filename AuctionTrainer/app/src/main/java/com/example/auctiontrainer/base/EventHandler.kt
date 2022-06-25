package com.example.auctiontrainer.base

interface EventHandler<T> {
    fun obtainEvent(event: T)
}