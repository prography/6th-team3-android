package com.prography.pethotel.ui.places

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.kakao.auth.authorization.AuthorizationResult

import com.prography.pethotel.R
import com.prography.pethotel.ui.places.adapters.TabAdapter
import com.prography.pethotel.ui.places.search.PlaceSearchResultActivity
import com.prography.pethotel.ui.places.util.TabFragmentsViewModel
import kotlinx.android.synthetic.main.place_info_fragment.*
import kotlinx.android.synthetic.main.places_view_pager_layout.*
import kotlinx.android.synthetic.main.search_custom_tab.*
import kotlinx.android.synthetic.main.search_custom_tab.view.*
import kotlinx.android.synthetic.main.search_custom_tab.view.tab_remove_button

@Suppress("DEPRECATION")
class PlaceInfoFragment : Fragment() {

    private lateinit var viewModel: PlaceInfoViewModel

    lateinit var tabAdapter : TabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.place_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        val mainToolbarTitle = requireActivity().findViewById<TextView>(R.id.main_toolbar_title)
//        mainToolbarTitle.visibility = View.GONE


        //TODO 메서드로 분리하기
        tabAdapter = TabAdapter(childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        place_view_pager.adapter = tabAdapter
        place_tabs.setupWithViewPager(place_view_pager)
        tabAdapter.apply {
            addFragment(PopularPlaceFragment.newInstance(), "#인기")
            addFragment(NearPlaceFragment.newInstance(), "#가까운")
            addFragment(DiscountPlaceFragment.newInstance(), "#저렴한")
        }
//        val icons = listOf<Int>(
//            R.drawable.ic_popularity,
//            R.drawable.ic_map_position,
//            R.drawable.ic_discount
//        )
//        for(x in 0 .. 2){
//            place_tabs.getTabAt(x)?.setIcon(icons[x])
//        }

        //TODO 메서드로 분리하기
//        tabFragmentsViewModel.tabFragments.observe(viewLifecycleOwner, Observer {
//            Log.d(TAG, "onActivityCreated: ${it.size}")
//                if(it.size > 3) {
//                    for (x in 1..it.size) {
//                        tabAdapter.addFragment(it[x].second, it[x].first)
//                    }
//                }
//        })
    }



//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
//        inflater.inflate(R.menu.actionbar_menu, menu)
//        val item = menu.findItem(R.id.menu_search)
//        val searchView = MenuItemCompat.getActionView(item) as SearchView
//        configureSearchView(searchView)
//    }


//    private lateinit var searchQuery : String
//
//    private fun configureSearchView(searchView: SearchView){
//        searchView.queryHint = "장소를 검색해 보세요"
//        val query = searchView.query
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//
//                //TODO 검색 진행하기
//                //데이터 갖고
//                //검색 화면으로 넘어가기
//
//                val intent = Intent(requireActivity(), PlaceSearchResultActivity::class.java)
//                intent.putExtra("QUERY", query)
//
//                if (!query.isNullOrEmpty()) {
//                    searchQuery = query
//                    startActivityForResult(intent, 1234)
//                }else{
//                    Toast.makeText(requireContext(), "검색어를 입력해 주세요.", Toast.LENGTH_LONG).show()
//                }
//
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.d(TAG, "onQueryTextChange: ${query}")
//                return true
//            }
//        })
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(resultCode == Activity.RESULT_OK && requestCode == 1234){
//            //TODO Query text 갖고 탭에다가 추가하기
//            tabAdapter.addFragment(SearchResultFragment(), searchQuery)
//        }
    }

    companion object {
        private const val TAG = "PlaceInfoFragment"
    }

}