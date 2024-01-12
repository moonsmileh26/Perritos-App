package mx.com.moonsmileh.perritos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.com.moonsmileh.perritos.databinding.ItemDogBinding

class DogsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)
    fun bind(imageUrl: String) {
        Picasso.get().load(imageUrl).into(binding.imageViewDog)
    }
}