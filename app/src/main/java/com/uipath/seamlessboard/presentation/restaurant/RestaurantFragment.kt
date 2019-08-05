package com.uipath.seamlessboard.presentation.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.misc.SpaceItemDecoration
import com.uipath.seamlessboard.presentation.restaurant.adapter.ReviewAdapter
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : Fragment() {

    private val restaurantViewModel: RestaurantViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_restaurant, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            restaurantViewModel.onCreate(RestaurantFragmentArgs.fromBundle(it).restaurantId)
        }

        restaurantViewModel.restaurant().observe(this, Observer {
            it?.let { restaurantRating ->
                (activity as? AppCompatActivity)?.supportActionBar?.title = restaurantRating.restaurant.name
                deliveryRatingView.rating = restaurantRating.deliveryRating
                foodRatingView.rating = restaurantRating.foodRating
            }
        })

        initAdapter()
    }

    private fun initAdapter() {
        val adapter = ReviewAdapter()
        restaurantViewModel.reviewsPagedList().observe(this, Observer {
            adapter.submitList(it)
        })
        reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewRecyclerView.addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.grid_item_space)))
        reviewRecyclerView.adapter = adapter
    }
}