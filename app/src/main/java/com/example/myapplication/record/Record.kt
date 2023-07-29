package com.example.myapplication.record

data class Record(
    var hasImage: HasImage,
    var userName: String,
    var title: String,
    var content: String,
    var img: Int?
)
