package com.maluku.sma_rt.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentViewPagerBinding
import com.maluku.sma_rt.view.onboarding.screens.FirstScreen
import com.maluku.sma_rt.view.onboarding.screens.SecondScreen
import com.maluku.sma_rt.view.onboarding.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        binding = FragmentViewPagerBinding.inflate(layoutInflater)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.viewPager.adapter = adapter

        return binding.root
    }

}