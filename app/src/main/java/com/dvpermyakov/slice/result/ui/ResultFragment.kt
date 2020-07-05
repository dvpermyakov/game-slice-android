package com.dvpermyakov.slice.result.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.result.presentation.ResultViewModel
import kotlinx.android.synthetic.main.fragment_result.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ResultFragment : Fragment(), KodeinAware {

    private val adapter = ResultAdapter()

    private val viewModeFactory: ViewModelProvider.Factory by instance()
    private val viewModel: ResultViewModel by lazy {
        ViewModelProvider(this, viewModeFactory).get(ResultViewModel::class.java)
    }

    override val kodein: Kodein by closestKodein()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        viewModel.getResultState().observe(viewLifecycleOwner, Observer { state ->
            adapter.items = state.items
        })
    }

    companion object {
        fun newInstance() = ResultFragment()
    }
}