package com.example.nour.nourapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nour.nourapp.R
import kotlinx.android.synthetic.main.service_row.view.*

/**
 * Created by nour on 7/21/18.
 */


class ServiceAdapter :RecyclerView.Adapter<ServiceViewHolder>(){


    override fun getItemCount(): Int {
        return 3
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ServiceViewHolder {
        val layoutInFlatter = LayoutInflater.from(parent?.context)
        val cellforRow = layoutInFlatter.inflate(R.layout.service_row,parent,false)
        val height = parent?.height?.div(4)
        height?.let { cellforRow.setMinimumHeight(it) }
        return ServiceViewHolder(cellforRow)
    }


    override fun onBindViewHolder(holder: ServiceViewHolder?, position: Int) {

    }


}



class ServiceViewHolder(val view: View):RecyclerView.ViewHolder(view){



}