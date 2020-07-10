package com.dvpermyakov.slice.screens.result.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.screens.result.presentation.ResultViewModel
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

    private val args: ResultFragmentArgs by navArgs()

    override val kodein: Kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setResultId(args.resultId)
    }

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
        recyclerView.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        ResultAdapter.VIEW_TYPE_HEADER -> 2
                        ResultAdapter.VIEW_TYPE_ITEM -> 1
                        else -> 1
                    }
                }
            }
        }
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimension(R.dimen.result_padding).toInt())
        )

        viewModel.getResultState().observe(viewLifecycleOwner, Observer { state ->
            adapter.header = state.header
            adapter.items = state.items
        })

        retryButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}