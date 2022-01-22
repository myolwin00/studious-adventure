package com.emrys.navinset.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrys.navinset.R
import com.emrys.navinset.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.Side
import dev.chrisbanes.insetter.windowInsetTypesOf

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTop.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_home_to_navigation_details)
            val windowInsetsController = WindowInsetsControllerCompat(requireActivity().window, view)
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        }


        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        val adapter = DummyListAdapter {

        }
        binding.rvList.adapter = adapter
        adapter.submitList(list)

        Insetter.builder()
            .margin(windowInsetTypesOf(statusBars = true), Side.TOP)
            .applyToView(binding.btnTop)

        Insetter.builder()
            .margin(windowInsetTypesOf(navigationBars = true), Side.BOTTOM)
            .applyToView(binding.btnBot)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showDP() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setTitleText("Select date")
                .build()
                .show(childFragmentManager, "datepicker")
    }

    val list = MutableList(100) {
        DummyData(
            id = it.toString(),
            name = "Name: $it"
        )
    }
}