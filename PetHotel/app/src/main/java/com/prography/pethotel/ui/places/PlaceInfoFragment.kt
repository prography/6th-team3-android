package com.prography.pethotel.ui.places

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.ui.places.adapters.MainTabAdapter
import kotlinx.android.synthetic.main.place_info_fragment.place_tabs
import kotlinx.android.synthetic.main.place_info_fragment_v2.*
import kotlinx.android.synthetic.main.places_view_pager_layout.place_view_pager

@Suppress("DEPRECATION")
class PlaceInfoFragment : Fragment() {

    private lateinit var placeInfoViewModel: PlaceInfoViewModel

    private lateinit var tabAdapter : MainTabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.place_info_fragment_v2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        placeInfoViewModel = ViewModelProviders.of(requireActivity())[PlaceInfoViewModel::class.java]
//        val mainToolbarTitle = requireActivity().findViewById<TextView>(R.id.main_toolbar_title)
//        mainToolbarTitle.visibility = View.GONE


        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(place_toolbar)
        }
        (activity as AppCompatActivity).supportActionBar?.title = ""

        //TODO 메서드로 분리하기
        tabAdapter = MainTabAdapter(childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        place_view_pager.adapter = tabAdapter
        place_tabs.setupWithViewPager(place_view_pager)
        tabAdapter.apply {
            addFragment(PopularPlaceFragment(), "#인기")
            addFragment(NearPlaceFragment(), "#가까운")
            addFragment(DiscountPlaceFragment(), "#저렴한")
        }

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