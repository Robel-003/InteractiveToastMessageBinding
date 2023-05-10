package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LetterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LetterListFragment : Fragment() {

    // FragmentLetterListBinding has a value of null b/c you can't inflate the layout until onCreateView() is called
    // there's a period of time in-between when the instance of LetterListFragment is created & when this property is usable

    // properties with underscore "_binding" are not intended to be accessed directly
    // here we're accessing the view binding in LetterListFragment with the binding property.
    // _binding property does not need to be accessed outside of LetterListFragment
        // since it's nullable, everytime you access property of _binding, need to include ? for null safety
        // if a value won't be null when accessing it, append !! to its type name.
        // Then you can access any other property without the ? operator
    private var _binding: FragmentLetterListBinding? = null;

    // property for RecyclerView
    private lateinit var recyclerView: RecyclerView

    // Keeps track of which LayoutManager is in use for the [RecyclerView]
    private var isLinearLayoutManager = true

    // get() means this property is "get-only", meaning you can get the value.
    // But once it's assigned, you can't assign it to something else
    private val binding get() = _binding!!


    // displaying options menu by overriding onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    // with fragments, layout is inflated in onCreateView(),
    // with activities, layout is inflated in onCreate()
    override fun onCreateView(
        // inflating the value by implementing onCreateView()
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setting value of _binding and returning root view
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    // setting the value of RecyclerView property and calling chooseLayout() like in MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        chooseLayout()
    }


    // resetting binding property to null once view no longer exists
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Activity class has global property menuInflater, while Fragment does not
    // menu inflater is passed into onCreateOptionsMenu().
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // onCreateOptionsMenu() used with fragments doesn't require a return
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }


    // below (functionality)code has been moved from MainActivity into here
    // all MainActivity class needs to do is inflate the layout so fragment is displayed in view

    // unlike an activity, a fragment is not a context. Can't pass in *this* as layout manager's context
    // however, fragments provide a context property you can use instead

    /**
     * Sets the LayoutManager for the [RecyclerView] based on the desired orientation of the list.
     */
    private fun chooseLayout() {
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter()
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter()
            }
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }


    /**
     * Determines how to handle interactions with the selected [MenuItem]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }





}