package com.uipath.seamlessboard.presentation.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.databinding.FragmentBoardBinding
import com.uipath.seamlessboard.misc.SpaceItemDecoration
import com.uipath.seamlessboard.presentation.board.adapter.BoardAdapter
import kotlinx.android.synthetic.main.fragment_board.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardFragment : Fragment() {

    private lateinit var fragmentBoardBinding: FragmentBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBoardBinding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return fragmentBoardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBoardBinding.boardViewModel = boardViewModel

        initAdapter()
    }

    override fun onResume() {
        super.onResume()

        (activity as? AppCompatActivity)?.supportActionBar?.setTitle(R.string.app_name)

        boardViewModel.navigationLiveData().observe(viewLifecycleOwner, Observer {
            val navDirections = when (it) {
                is BoardNavigationCommands.StartAddReviewActivity -> BoardFragmentDirections.actionBoardFragmentToAddReviewActivity()
                is BoardNavigationCommands.ShowRestaurantFragment ->
                    BoardFragmentDirections.actionBoardFragmentToRestaurantFragment(it.restaurantId)
            }
            findNavController().navigate(navDirections)
        })
    }

    override fun onPause() {
        super.onPause()
        boardViewModel.navigationLiveData().removeObservers(viewLifecycleOwner)
    }

    private fun initAdapter() {
        val adapter = BoardAdapter {
            boardViewModel.onRestaurantRatingClicked(it)
        }
        boardViewModel.restaurantPagedList.observe(this, Observer {
            adapter.submitList(it)
            emptyStateTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
        boardRecyclerView.layoutManager = GridLayoutManager(context, 2)
        boardRecyclerView.addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.grid_item_space)))
        boardRecyclerView.adapter = adapter
    }
}