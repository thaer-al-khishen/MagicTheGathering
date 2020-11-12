package com.example.magicthegathering

import com.example.magicthegathering.Fragments.HomeFragment

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.magicthegathering.Fragments.CardDetailsFragment
import com.example.magicthegathering.Fragments.CardsListFragment
import com.example.magicthegathering.Models.Card
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val homeFragment = HomeFragment()
    private var currentFragment: FragmentType =
        FragmentType.HOME

    companion object {
        enum class FragmentType {
            HOME,
            CARDSLIST,
            CARDDETAILS
        }

        private const val BACK_STACK_ROOT_TAG = "root_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showHomeFragment()
        }
    }

    fun switch(
        type: FragmentType,
        card: Card?
    ) {
        when (type) {

            FragmentType.HOME -> {
                popBackStack()
            }

            FragmentType.CARDSLIST -> {
                replaceFragment(R.id.mainContainer, CardsListFragment.newInstance())
            }

            FragmentType.CARDDETAILS -> {
                card?.let {
                    replaceFragment(R.id.mainContainer, CardDetailsFragment.newInstance(it))
                }
            }

        }
        currentFragment = type
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, homeFragment)
            .commit()
        supportFragmentManager.executePendingTransactions()
    }

    private fun popBackStack() {
        supportActionBar?.hide()
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
