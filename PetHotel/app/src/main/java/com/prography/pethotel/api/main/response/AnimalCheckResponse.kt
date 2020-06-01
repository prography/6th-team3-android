package com.prography.pethotel.api.main.response

data class AnimalCheckResponse(
    val resultCode : Int, //결과 코드
    val resultMsg : String, //결과 메시지
    val dogRegNo : String, // 동물 등록 번호
    val rfidCd : String, //REID CD 번호
    val dogNm : String, //동물 이름
    val sexNm : String, //성별
    val kindNm : String, //품종
    val neuterYn : String, //중성화 여부
    val orgNm : String, //관할 기관
    val officeTel : String, //관할기관 연락처
    val aprGbNm : String //승인 여부
)

//서비스URL http://openapi.animal.go.kr/openapi/service/rest/animalInfoSrvc/animalInfo