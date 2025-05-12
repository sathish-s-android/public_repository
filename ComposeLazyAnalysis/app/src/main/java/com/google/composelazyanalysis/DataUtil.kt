package com.google.composelazyanalysis

import java.util.random.RandomGenerator
import kotlin.random.Random

object DataUtil {

    private const val size = 1000
    val data = mutableListOf<RVData>()
    private val randomNames = mutableListOf<String>()

    init {
        generateRandomStringList()
        generate()
    }


    private fun generate(){
        for (i in 0..size){
            data.add(
                RVData(
                    Random.nextInt(0,4),
                    i,
                    randomNames[getRandomNumber()],
                    R.drawable.ac_unit,
                    R.drawable.add_a_photo,
                    R.drawable.pngegg,
                    randomNames[getRandomNumber()],
                    randomNames[getRandomNumber()],
                    randomNames[getRandomNumber()],
                )
            )
        }
    }

    private fun getRandomNumber():Int{
        return Random.nextInt(0, 100)
    }


    private fun generateRandomStringList() {
        val chars = ('a'..'z') + ('A'..'Z') // letters a-z and A-Z
        val list = List(100) {
            (1..8) // random string length between 1 to 8
                .map { chars.random() }
                .joinToString("")
        }
        randomNames.addAll(list)
    }

}



data class RVData(
    val viewType:Int,
    val id:Int,
    val name:String,
    val drawable1:Int,
    val drawable2:Int,
    val goku:Int,
    val buttonContent:String,
    val neutralContent:String,
    val dummyContent:String
)