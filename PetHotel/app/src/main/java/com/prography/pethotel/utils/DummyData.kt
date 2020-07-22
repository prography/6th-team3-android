package com.prography.pethotel.utils

import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.monitor.MonitorMedia

class DummyData {


    companion object{
        val hotelDummyList : ArrayList<HotelData> = makeDummyHotelInfo()

        val mediaList : ArrayList<MonitorMedia> = makeDummyMediaList()

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

    }
}
