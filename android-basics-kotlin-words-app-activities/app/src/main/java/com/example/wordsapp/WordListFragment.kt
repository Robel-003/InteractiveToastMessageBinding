package com.example.wordsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentWordListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WordListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordListFragment : Fragment() {

    /**
     * Provides global access to these variables from anywhere in the app
     * via DetailActivity.<variable> without needing to create
     * a DetailActivity instance.
     */
    companion object {
        const val LETTER = "letter"
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    private var _binding: FragmentWordListBinding? = null

    private val binding get() = _binding!!

    private lateinit var  letterId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // b/c it's possible for arguments to be optional, we called let() and passed in a lambda
        // below code executes assuming arguments is not null, passing in the non null arguments for it parameter
        // if arguments is null, lambda will not execute
        arguments?.let {
            letterId = it.getString(LETTER).toString()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    // implementation of onViewCreated() is similar to configuring RecyclerView in onCreate() in DetailActivity
    // since fragments don't have direct access to the intent, need to reference it with activity.intent
    // have to do this in onViewCreated() since there's no guarantee activity exists earlier in the lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // intent.extras.getString returns String? (String or null)
        // so toString() guarantees that the value will be a String
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
            // update: can access the letterId when you set the recycler view's adapter..
            // ..by replacing (activity?.intent?.extras?.getString(LETTER).toString(), ..) with letterId
        recyclerView.adapter = WordAdapter(letterId, requireContext())

        // Adds a [DividerItemDecoration] between items
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }


    // resetting _binding variable
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}