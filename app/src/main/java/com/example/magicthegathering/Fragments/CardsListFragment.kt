package com.example.magicthegathering.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magicthegathering.Adapters.VerticalCardsAdapter
import com.example.magicthegathering.MainActivity
import com.example.magicthegathering.Models.Card
import com.example.magicthegathering.R
import com.example.magicthegathering.ViewModels.CardsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cards_list.*
import java.net.UnknownHostException

@AndroidEntryPoint
class CardsListFragment : Fragment() {

    private lateinit var adapterVertical: VerticalCardsAdapter
    private val viewModel by viewModels<CardsViewModel>()
    private val cardsList: ArrayList<Card> = ArrayList()

    companion object {
        fun getInstance(): CardsListFragment {
            val fragment =
                CardsListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = CardsListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpViewModel()
        setPullToRefresh()
    }

    private fun setUpRecyclerView() {
        recycler_view_vertical_list.layoutManager = LinearLayoutManager(activity)
        adapterVertical = VerticalCardsAdapter(cardsList, object : VerticalCardsAdapter.ICardsItemSelected {

            override fun onCardsItemsClicked(position: Int) {
//                addFragment(cardsList[position])
                (activity as MainActivity).switch(
                    MainActivity.Companion.FragmentType.CARDDETAILS, cardsList[position])
            }

        })
        recycler_view_vertical_list.adapter = adapterVertical
    }

    private fun setUpViewModel() {

        viewModel.card.observe(viewLifecycleOwner) {
            it?.let {
                if (it.throwable is UnknownHostException) {
                    no_internet_layout2.visibility = View.VISIBLE
                    recycler_view_vertical_list.visibility = View.GONE
                    empty_layout2.visibility = View.GONE
                } else {
                    it.data?.let {
                        if (it.isEmpty()) {
                            no_internet_layout2.visibility = View.GONE
                            empty_layout2.visibility = View.VISIBLE
                            recycler_view_vertical_list.visibility = View.GONE

                        } else {
                            no_internet_layout2.visibility = View.GONE
                            recycler_view_vertical_list.visibility = View.VISIBLE
                            empty_layout2.visibility = View.GONE
                            cardsList.clear()
                            cardsList.addAll(it)
                            adapterVertical.notifyDataSetChanged()

                        }
                    }
                }
                refresh2.isRefreshing = false
            }
        }
    }

    private fun setPullToRefresh() {
        refresh2.setOnRefreshListener {
            viewModel.getAllCards()
        }
    }

}