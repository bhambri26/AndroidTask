package com.example.infyassignment

public class Model{
    lateinit var title:String
    lateinit var desc:String
    lateinit var img:String

    constructor(title: String,desc:String,img:String) {
        this.title = title  //id
        this.desc = desc   //name
        this.img = img  //email
    }

    constructor()
}