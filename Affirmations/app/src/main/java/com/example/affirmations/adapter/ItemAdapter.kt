package com.example.affirmations.adapter

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

// item adapter class needs information on how to resolve string resources and other info about the app
// this is all stored in object Context instance
    // RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() <- this overrides the adapter methods
class ItemAdapter(private val context: Context,
                  private val dataset: List<Affirmation>) :
                    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // a viewholder represents a single list item view in recyclerview which can be reused
    // viewholder instance holds reference to individual views within a list item layout
    // this makes it easier to update the list item view with new data
    // view holders also add info that recyclerview uses to efficiently move views around screen

    // making ItemViewHolder a subclass of RecyclerView.ViewHolder and passing view into superclass constructor
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        // textview variable referencing textview in list_item.xml that'll hold the affirmations
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    // creating a new view

        // obtaining an instance of layoutinflater from provided context (context of the parent)
        // the layout inflater know how to inflate an xml layout into a hierarchy of view objects
            // .inflate(...) inflates the actual list item view
                // the first two params are the xml layout resource ID and the parent view group
                // the third param(attachToRoot) is a check for the recyclerview to add items to the view hierarchy when its time
            // adapterLayout now holds reference to the list item view(from which we can later find child views like the textview)
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        // returning a new ItemViewHolder where the root view is adapterLayout
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() = dataset.size // <-- concise way of below code for item count
//    override fun getItemCount(): Int {
//        return dataset.size
//    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // item variable gets the item at the given position in the dataset
        val item = dataset[position]
        // need to update all views referenced by view holder to reflect correct data for this item
            // updating the view holder to show the affirmation string
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
    }

}