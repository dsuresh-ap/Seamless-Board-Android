package com.uipath.seamlessboard.presentation.board

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.databinding.FragmentBoardBinding
import com.uipath.seamlessboard.misc.SpaceItemDecoration
import com.uipath.seamlessboard.presentation.board.adapter.BoardAdapter
import com.uipath.seamlessboard.presentation.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_board.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardFragment : Fragment() {

    private lateinit var fragmentBoardBinding: FragmentBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBoardBinding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return fragmentBoardBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBoardBinding.boardViewModel = boardViewModel

        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_board, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                boardViewModel.onLogoutClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as? AppCompatActivity)?.supportActionBar?.setTitle(R.string.app_name)

        boardViewModel.navigationLiveData().observe(viewLifecycleOwner, Observer {
            val navDirections = when (it) {
                is BoardNavigationCommands.StartAddReviewActivity -> BoardFragmentDirections.actionBoardFragmentToAddReviewActivity()
                is BoardNavigationCommands.ShowRestaurantFragment ->
                    BoardFragmentDirections.actionBoardFragmentToRestaurantFragment(it.restaurantId)
                is BoardNavigationCommands.SignOut -> {
                    activity?.run {
                        AuthUI.getInstance().signOut(this).addOnCompleteListener { boardViewModel.onLoggedOut() }
                    }
                    null
                }
                is BoardNavigationCommands.Login -> {
                    activity?.run {
                        val intent = Intent(this, LoginActivity::class.java).apply {
                            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                        startActivity(intent)
                    }
                    null
                }
            }
            navDirections?.let {
                findNavController().navigate(navDirections)
            }
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