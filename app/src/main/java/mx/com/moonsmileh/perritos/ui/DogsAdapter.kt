package mx.com.moonsmileh.perritos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.moonsmileh.perritos.DogsViewHolder
import mx.com.moonsmileh.perritos.R

class DogsAdapter(var dogs: List<String>) : RecyclerView.Adapter<DogsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogsViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        val imageUrl = dogs[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount() = dogs.size
}