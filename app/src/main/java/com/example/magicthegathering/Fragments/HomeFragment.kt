package com.example.magicthegathering.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magicthegathering.Adapters.HorizontalCardsAdapter
import com.example.magicthegathering.MainActivity
import com.example.magicthegathering.Models.Card
import com.example.magicthegathering.R
import com.example.magicthegathering.ViewModels.CardsViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.net.UnknownHostException

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapterHorizontal: HorizontalCardsAdapter

    private val viewModel by viewModels<CardsViewModel>()


    private val cardsList: ArrayList<Card> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        fun getInstance(): HomeFragment {
            val fragment =
                HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setUpRecyclerView()
        setUpViewModel()
        setPullToRefresh()
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapterHorizontal =
            HorizontalCardsAdapter(cardsList, object : HorizontalCardsAdapter.ICardsItemSelected {

                override fun onCardsItemsClicked(position: Int) {
                    (activity as MainActivity).switch(
                        MainActivity.Companion.FragmentType.CARDDETAILS, cardsList[position]
                    )
                }

            })
        recycler_view.adapter = adapterHorizontal
    }

    private fun setUpViewModel() {

        viewModel.card.observe(viewLifecycleOwner) {
            it?.let {
                if (it.throwable is UnknownHostException) {
                    no_internet_layout.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                    empty_layout.visibility = View.GONE
                } else {
                    it.data?.let {
                        if (it.isEmpty()) {
                            no_internet_layout.visibility = View.GONE
                            empty_layout.visibility = View.VISIBLE
                            recycler_view.visibility = View.GONE

                        } else {
                            no_internet_layout.visibility = View.GONE
                            recycler_view.visibility = View.VISIBLE
                            empty_layout.visibility = View.GONE
                            cardsList.clear()
                            cardsList.addAll(it)
                            adapterHorizontal.notifyDataSetChanged()

                        }
                    }
                }
                refresh.isRefreshing = false
            }
        }
    }

    private fun setPullToRefresh() {
        refresh.setOnRefreshListener {
            viewModel.getAllCards()
        }
    }

    fun addFragment(card: Card?) {
        card?.let {
            fragmentManager?.beginTransaction()!!
                .replace(
                    R.id.fragmentFirstTabLayout,
                    CardDetailsFragment.newInstance(it)
                )
                .addToBackStack("ss")
                .commit()
        }
    }

    fun setOnClickListener() {
        viewMoreTextView.setOnClickListener {
            (activity as MainActivity).switch(
                MainActivity.Companion.FragmentType.CARDSLIST, null
            )
        }
    }

}
