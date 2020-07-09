package com.prography.pethotel.utils

import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.HotelReview
import com.prography.pethotel.ui.monitor.MonitorMedia

class DummyData {


    companion object{
        val hotelDummyList : ArrayList<HotelData> = makeDummyHotelInfo()

        val mediaList : ArrayList<MonitorMedia> = makeDummyMediaList()

        val hotelReviews : ArrayList<HotelReview> = makeDummyReviews()

        private fun makeDummyHotelInfo() : ArrayList<HotelData>{
            var list = ArrayList<HotelData>()
            if(!hotelDummyList.isNullOrEmpty()){
                return hotelDummyList
            }else{
                for(x in 0 .. 20){

                }
                return list
            }
        }

        private fun makeDummyMediaList() : ArrayList<MonitorMedia>{
            var list = ArrayList<MonitorMedia>()

            return if(!mediaList.isNullOrEmpty()){
                mediaList
            }else {
                for (x in 1..4) {
                    list.add(
                        MonitorMedia(
                            "https://www.newshub.co.nz/home/lifestyle/2019/11/dog-years-are-a-myth-2-year-old-dogs-already-middle-aged-scientists/_jcr_content/par/video/image.dynimg.1280.q75.jpg/v1574572358818/GETTY-labrador-puppy-1120.jpg",
                            "#산책하루${x}번 #신나요",
                            "2020-03-0${x}"
                        )
                    )
                }
                list
            }
        }


        private fun makeDummyReviews() : ArrayList<HotelReview>{
            var list = ArrayList<HotelReview>()
            if(!hotelReviews.isNullOrEmpty()){
                return hotelReviews
            }else{
                for(x in 1 .. 5){
                    list.add(
                        HotelReview(
                            x,
                            "2020-05-05",
                            "2020-05-05",
                            "${x}번 털실맘",
                            "아무 호텔 넘버 $x",
                            "This is a sample review. I will be changing this " +
                                    "part with real data in $x days.",
                            5
                        )
                    )
                }
                return list
            }
        }

    }
}

//    fun sss(){
//        Log.d(PlaceInfoFragment.TAG, "onQueryTextSubmit: ${query}")
//        // TODO 검색한 단어로 탭을 추가한다
//        //X 버튼을 옆에 누어서 누르면 해당 탭이 사라지도록 한다.
//        //검색 결과가 없을 경우의 화면을 만들어서 필요시 띄운다.
//        tabAdapter.addFragment(SearchResultFragment(), "#${query}")
//
//        val cancelableTab = LayoutInflater.from(requireContext()).inflate(R.layout.search_custom_tab, null)
//        cancelableTab.tab_text.text = "#${query}"
//        cancelableTab.tab_remove_button.tag = "#${query}"
//        cancelableTab.tab_remove_button.setOnClickListener {
//            val tabPosition = tabAdapter.fragmentTitleList.indexOf(it.tag)
//            tabAdapter.removeFragment(tabPosition)
//            Toast.makeText(requireContext(), "CANCEL", Toast.LENGTH_SHORT).show()
//        }
//        val resultTab = place_tabs.getTabAt(tabAdapter.fragmentList.lastIndex)
//        resultTab?.customView = cancelableTab
////                place_tabs.selectTab(resultTab, true)
//    }
