package com.google.relativeviewanalysis

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

var s = 0

class MyAdapter(private val list:List<RVData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {





        Log.d("Sathish_SSS", "onCreateViewHolder: ${s++}    ")

       return when(viewType){
            0->{
                MyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false).apply {
                        background = ContextCompat.getDrawable(parent.context,R.color.c1)
                    }
                )
            }
            1->{
                MyViewHolder1(
                    LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false).apply {
                        background = ContextCompat.getDrawable(parent.context,R.color.c2)
                    }
                )
            }
            2->{
                MyViewHolder2(
                    LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false).apply {
                        background = ContextCompat.getDrawable(parent.context,R.color.c3)
                    }
                )
            }
            3->{
                MyViewHolder3(
                    LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false).apply {
                        background = ContextCompat.getDrawable(parent.context,R.color.c4)
                    }
                )
            }

            else -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false).apply {
                        background = ContextCompat.getDrawable(parent.context,R.color.c1)
                    }
                )
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MyViewHolder->{
                holder.bind(list[position])
            }
            is MyViewHolder1->{
                holder.bind(list[position])
            }
            is MyViewHolder2->{
                holder.bind(list[position])
            }
            is MyViewHolder3->{
                holder.bind(list[position])
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}


class MyViewHolder(private val view: View):RecyclerView.ViewHolder(view){

    val name = view.findViewById<TextView>(R.id.tv_1)
    val img1 = view.findViewById<ImageView>(R.id.img_1)
    val img2 = view.findViewById<ImageView>(R.id.img_2)
    val goku = view.findViewById<ImageView>(R.id.gk_img)
    val dummy = view.findViewById<TextView>(R.id.dummy)
    val btn = view.findViewById<Button>(R.id.btn)
    val neutral = view.findViewById<TextView>(R.id.neutral)

    fun bind(data:RVData){
        name.text = data.name
        dummy.text = data.dummyContent
        btn.text = data.buttonContent
        neutral.text = data.neutralContent
        goku.setImageDrawable(ContextCompat.getDrawable(view.context,data.goku))
        img1.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable1))
        img2.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable2))
    }
}

class MyViewHolder1(private val view: View):RecyclerView.ViewHolder(view){

    val name = view.findViewById<TextView>(R.id.tv_1)
    val img1 = view.findViewById<ImageView>(R.id.img_1)
    val img2 = view.findViewById<ImageView>(R.id.img_2)
    val goku = view.findViewById<ImageView>(R.id.gk_img)
    val dummy = view.findViewById<TextView>(R.id.dummy)
    val btn = view.findViewById<Button>(R.id.btn)
    val neutral = view.findViewById<TextView>(R.id.neutral)

    fun bind(data:RVData){
        name.text = data.name
        dummy.text = data.dummyContent
        btn.text = data.buttonContent
        neutral.text = data.neutralContent
        goku.setImageDrawable(ContextCompat.getDrawable(view.context,data.goku))
        img1.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable1))
        img2.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable2))
    }
}

class MyViewHolder2(private val view: View):RecyclerView.ViewHolder(view){

    val name = view.findViewById<TextView>(R.id.tv_1)
    val img1 = view.findViewById<ImageView>(R.id.img_1)
    val img2 = view.findViewById<ImageView>(R.id.img_2)
    val goku = view.findViewById<ImageView>(R.id.gk_img)
    val dummy = view.findViewById<TextView>(R.id.dummy)
    val btn = view.findViewById<Button>(R.id.btn)
    val neutral = view.findViewById<TextView>(R.id.neutral)

    fun bind(data:RVData){
        name.text = data.name
        dummy.text = data.dummyContent
        btn.text = data.buttonContent
        neutral.text = data.neutralContent
        goku.setImageDrawable(ContextCompat.getDrawable(view.context,data.goku))
        img1.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable1))
        img2.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable2))
    }
}

class MyViewHolder3(private val view: View):RecyclerView.ViewHolder(view){

    val name = view.findViewById<TextView>(R.id.tv_1)
    val img1 = view.findViewById<ImageView>(R.id.img_1)
    val img2 = view.findViewById<ImageView>(R.id.img_2)
    val goku = view.findViewById<ImageView>(R.id.gk_img)
    val dummy = view.findViewById<TextView>(R.id.dummy)
    val btn = view.findViewById<Button>(R.id.btn)
    val neutral = view.findViewById<TextView>(R.id.neutral)

    fun bind(data:RVData){
        name.text = data.name
        dummy.text = data.dummyContent
        btn.text = data.buttonContent
        neutral.text = data.neutralContent
        goku.setImageDrawable(ContextCompat.getDrawable(view.context,data.goku))
        img1.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable1))
        img2.setImageDrawable(ContextCompat.getDrawable(view.context,data.drawable2))
    }
}



/**

//package com.google.relativeviewanalysis
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val list: List<RVData>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d("Sathish_SSS", "onCreateViewHolder: ${s++}    ")
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }
}


class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    val name = view.findViewById<TextView>(R.id.tv_1)
    val img1 = view.findViewById<ImageView>(R.id.img_1)
    val img2 = view.findViewById<ImageView>(R.id.img_2)
    val goku = view.findViewById<ImageView>(R.id.gk_img)
    val dummy = view.findViewById<TextView>(R.id.dummy)
    val btn = view.findViewById<Button>(R.id.btn)
    val neutral = view.findViewById<TextView>(R.id.neutral)

    fun bind(data: RVData) {
        name.text = data.name
        dummy.text = data.dummyContent
        btn.text = data.buttonContent
        neutral.text = data.neutralContent
        goku.setImageDrawable(ContextCompat.getDrawable(view.context, data.drawable1))
        img1.setImageDrawable(ContextCompat.getDrawable(view.context, data.drawable1))
        img2.setImageDrawable(ContextCompat.getDrawable(view.context, data.drawable2))
    }
}

*/



