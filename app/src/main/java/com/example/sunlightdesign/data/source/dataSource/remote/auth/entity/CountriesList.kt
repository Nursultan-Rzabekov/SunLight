package com.example.sunlightdesign.data.source.dataSource.remote.auth.entity



data class CountriesList(
    val cities: List<City>?,
    val countries: List<Country>?,
    val regions: List<Region>?
)

data class City(
    val city_code: Int?,
    val city_description: String?,
    val city_name: String?,
    val country_id: Int?,
    val id: Int?,
    val region_id: Int?
){
    override fun toString(): String {
        return city_name.toString()
    }
}

data class Country(
    val country_code: String?,
    val country_name: String?,
    val currency_id: Int?,
    val id: Int?
){
    override fun toString(): String {
        return country_name.toString()
    }
}

data class Region(
    val country_id: Int?,
    val id: Int?,
    val region_code: Int?,
    val region_name: String?
){
    override fun toString(): String {
        return region_name.toString()
    }
}