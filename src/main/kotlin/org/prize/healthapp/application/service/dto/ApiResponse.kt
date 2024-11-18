package org.prize.healthapp.application.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiResponse(
    @JsonProperty("response")
    val response: Response,
)

data class Response(
    val header: Header,
    val body: Body,
)

data class Header(
    val resultCode: String,
    val resultMsg: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Body(
    val pageNo: Int,
    val totalCount: Int,
    val items: Items,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Items(
    val item: List<StadiumItem>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StadiumItem(
    var addr_ctpv_nm: String = "", // 시 (예: 서울특별시)
    var nation_yn: String = "", // 국가 여부 (예: "N")
    var faci_daddr: String = "", // 상세 주소
    var row_num: Int = 0, // 행 번호
    var base_ymd: String = "", // 기준 일자
    var faci_stat_nm: String = "", // 시설 상태 명칭
    var reg_dt: String = "", // 등록 일자
    var inout_gbn_nm: String = "", // 실내외 구분 명칭
    var faci_lat: String = "", // 위도
    var faci_cd: String = "", // 시설 코드
    var faci_zip: String = "", // 우편번호
    var faci_gfa: Int = 0, // 시설 면적
    var faci_road_daddr: String = "", // 도로명 상세 주소
    var ftype_nm: String = "", // 시설 유형 명칭 (예: "골프")
    var cpb_nm: String = "", // 구 (예: 송파구)
    var addr_emd_nm: String = "", // 동 (예: 잠실동)
    var addr_cpb_nm: String = "", // 구 (예: 송파구)
    var faci_road_zip: String = "", // 도로명 우편번호
    var faci_tel_no: String = "", // 전화번호
    var faci_lot: String = "", // 경도
    var updt_dt: String = "", // 업데이트 날짜
    var faci_gb_nm: String = "", // 시설 구분 명칭
    var cp_nm: String = "", // 시 (예: 서울특별시)
    var faci_nm: String = "", // 시설 이름
    var faci_road_addr: String = "", // 도로명 주소
    var atnm_chk_yn: String = "", // 자동체크 여부
    var fcob_nm: String = "", // 업종명
    var faci_addr: String = "", // 주소
)
