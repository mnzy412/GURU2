package com.example.myapplication

data class User(
    var nickname: String,
    var email: String,
    var uId: String
){
    constructor(): this("","","")
}