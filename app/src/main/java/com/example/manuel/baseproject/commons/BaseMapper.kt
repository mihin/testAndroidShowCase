package com.example.manuel.baseproject.commons

interface BaseMapper<in A, out B> {

    fun mapFrom(type: A?): B
}