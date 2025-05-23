package com.example.eccomerce_app.data.repository

import com.example.eccomerce_app.Dto.AuthResultDto
import com.example.eccomerce_app.Util.General
import com.example.eccomerce_app.dto.ModelToDto.toProdcutVarientRequestDto
import com.example.eccomerce_app.dto.request.LocationRequestDto
import com.example.eccomerce_app.dto.request.LoginDto
import com.example.eccomerce_app.dto.request.SubCategoryRequestDto
import com.example.eccomerce_app.dto.request.SubCategoryUpdateDto
import com.example.eccomerce_app.dto.response.AddressResponseDto
import com.example.eccomerce_app.dto.response.BannerResponseDto
import com.example.eccomerce_app.dto.response.CategoryReponseDto
import com.example.eccomerce_app.dto.response.ProductResponseDto
import com.example.eccomerce_app.dto.response.StoreResposeDto
import com.example.eccomerce_app.dto.response.SubCategoryResponseDto
import com.example.eccomerce_app.dto.response.UserDto
import com.example.eccomerce_app.dto.response.VarientResponseDto
import com.example.eccomerce_app.model.Banner
import com.example.eccomerce_app.model.MyInfoUpdate
import com.example.eccomerce_app.model.ProductVarientSelection
import com.example.eccomerce_app.util.Secrets
import com.example.hotel_mobile.Modle.NetworkCallHandler
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import java.io.File
import java.io.IOException
import java.net.UnknownHostException
import java.util.UUID

class HomeRepository(val client: HttpClient) {

    /*
    suspend fun getUserAddress(): NetworkCallHandler {
        return try {
            var result = client.get(
                Secrets.getBaseUrl() + "/User/address"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if (result.status == HttpStatusCode.OK) {
                NetworkCallHandler.Successful(result.body<List<AddressResponseDto>>())
            } else {
                NetworkCallHandler.Error(
                    result.body<String>()
                )
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }
*/

    suspend fun userAddNewAddress(locationData: LocationRequestDto): NetworkCallHandler {
        return try {
            var result = client.post(
                Secrets.getBaseUrl() + "/User/address"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    locationData
                )
                contentType(ContentType.Application.Json)

            }

            if (result.status == HttpStatusCode.Created) {
                NetworkCallHandler.Successful(result.body<AddressResponseDto?>())
            } else {
                NetworkCallHandler.Error(
                    result.body<String>()
                )
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun setAddressAsCurrent(addressId: UUID): NetworkCallHandler {
        return try {
            var result = client.post(
                Secrets.getBaseUrl() + "/User/address/active${addressId}"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if (result.status == HttpStatusCode.OK) {
                NetworkCallHandler.Successful(result.body<Boolean>())
            } else {
                NetworkCallHandler.Error(
                    result.body<String>()
                )
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun getCategory(pageNumber: Int = 1): NetworkCallHandler {
        return try {
            var result = client.get(
                Secrets.getBaseUrl() + "/Category/all/${pageNumber}"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<List<CategoryReponseDto>>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getVarient(pageNumber: Int = 1): NetworkCallHandler {
        return try {
            var result = client.get(
                Secrets.getBaseUrl() + "/Varient/all/${pageNumber}"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<List<VarientResponseDto>>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    //user
    suspend fun getMyInfo(): NetworkCallHandler {
        return try {
            var result = client.get(
                Secrets.getBaseUrl() + "/User"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<UserDto>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun UpdateMyInfo(data: MyInfoUpdate): NetworkCallHandler {
        return try {
            var result = client.put(
                Secrets.getBaseUrl() + "/User"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            if (!data.name.isNullOrEmpty())
                                append("name", data.name!!)

                            if (!data.oldPassword.isNullOrEmpty())
                                append("name", data.oldPassword!!)


                            if (!data.phone.isNullOrEmpty())
                                append("phone", data.phone!!)


                            if (!data.newPassword.isNullOrEmpty())
                                append("name", data.newPassword!!)

                            if (data.thumbnail != null)
                                append(
                                    key = "thumbnail", // Must match backend expectation
                                    value = data.thumbnail!!.readBytes(),
                                    headers = Headers.build {
                                        append(
                                            HttpHeaders.ContentType,
                                            "image/${data.thumbnail!!.extension}"
                                        )
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=${data.thumbnail!!.name}"
                                        )
                                    }
                                )

                        }
                    )
                )
            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<UserDto>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }



    suspend fun createStore(
        name: String,
        wallpaper_image: File,
        small_image: File,
        longitude: Double,
        latitude: Double
    ): NetworkCallHandler {
        return try {
            var result = client.post(
                Secrets.getBaseUrl() + "/Store/new"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("name", name)
                            append("longitude", latitude)
                            append("latitude", longitude)
                            append(
                                key = "wallpaper_image", // Must match backend expectation
                                value = wallpaper_image.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${wallpaper_image.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${wallpaper_image.name}"
                                    )
                                }
                            )
                            append(
                                key = "small_image", // Must match backend expectation
                                value = small_image.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${small_image.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${small_image.name}"
                                    )
                                }
                            )

                        }
                    )
                )
            }
            if (result.status == HttpStatusCode.Created) {
                return NetworkCallHandler.Successful(result.body<StoreResposeDto>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun updateStore(
        name: String,
        wallpaper_image: File?,
        small_image: File?,
        longitude: Double,
        latitude: Double
    ): NetworkCallHandler {
        return try {
            var result = client.put (
                Secrets.getBaseUrl() + "/Store"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            if(name.trim().length>0)
                            append("name", name)
                            if(latitude!=0.0)
                            append("longitude", latitude)
                            if(longitude!=0.0)
                            append("latitude", longitude)
                            if(wallpaper_image!=null)
                            append(
                                key = "wallpaper_image", // Must match backend expectation
                                value = wallpaper_image.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${wallpaper_image.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${wallpaper_image.name}"
                                    )
                                }
                            )
                            if(small_image!=null)
                            append(
                                key = "small_image", // Must match backend expectation
                                value = small_image.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${small_image.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${small_image.name}"
                                    )
                                }
                            )

                        }
                    )
                )
            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<StoreResposeDto>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun createSubCategory(data:SubCategoryRequestDto): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/SubCategory/new";
            val result = client.post(full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(data)
                contentType(ContentType.Application.Json)

            }

            if(result.status== HttpStatusCode.Created){
                NetworkCallHandler.Successful(result.body<SubCategoryResponseDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun updateSubCategory(data: SubCategoryUpdateDto): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/SubCategory";
            val result = client.put (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(data)
                contentType(ContentType.Application.Json)

            }

            if(result.status== HttpStatusCode.Created){
                NetworkCallHandler.Successful(result.body<SubCategoryResponseDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun getBannerByStoreId(store_id: UUID,pageNumber:Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Banner/${store_id}/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }

            }

            if(result.status== HttpStatusCode.Created){
                NetworkCallHandler.Successful(result.body<List<BannerResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getRandomBanner(): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Banner";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }

            }

            if(result.status== HttpStatusCode.Created){
                NetworkCallHandler.Successful(result.body<List<BannerResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }



    suspend fun createBanner(
        endDate: String,
        image: File,
    ): NetworkCallHandler {
        return try {
            var result = client.post(
                Secrets.getBaseUrl() + "/Banner"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("end_at", endDate)
                            append(
                                key = "image", // Must match backend expectation
                                value = image.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${image.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${image.name}"
                                    )
                                }
                            )

                        }
                    )
                )
            }
            if (result.status == HttpStatusCode.Created) {
                return NetworkCallHandler.Successful(result.body<BannerResponseDto>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun deleteBanner(
        banner_id: UUID
    ): NetworkCallHandler {
        return try {
            var result = client.delete (
                Secrets.getBaseUrl() + "/Banner/${banner_id}"
            ) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }

            }
            if (result.status == HttpStatusCode.OK) {
                return NetworkCallHandler.Successful(result.body<String>())
            } else {
                return NetworkCallHandler.Error(result.body())
            }
        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }



    suspend fun getStoreById(id: UUID): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Store/${id}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<StoreResposeDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getStoreAddress(id: UUID,pageNumber: Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Store/${id}/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<AddressResponseDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getStoreSubCategory(id: UUID,pageNumber: Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/SubCategory/${id}/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<List<SubCategoryResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun getProduct(pageNumber: Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<List<ProductResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getProduct(store_id: UUID,pageNumber: Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product/${store_id}/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<List<ProductResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun getProduct(store_id: UUID, subCatgory: UUID, pageNumber: Int): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product/${store_id}/${subCatgory}/${pageNumber}";
            val result = client.get (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<List<ProductResponseDto>>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun  createProduct(
        name:String,
        description:String,
        thmbnail: File,
        subcategory_id: UUID,
        store_id: UUID,
        price: Double,
        productVarients:List<ProductVarientSelection>,
        images:List<File>
        ): NetworkCallHandler{
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product";
            val result = client.post  (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("name", name)
                            append("description", description)
                            append(
                                key = "thmbnail", // Must match backend expectation
                                value = thmbnail.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${thmbnail.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${thmbnail.name}"
                                    )
                                }
                            )

                            append("subcategory_id", subcategory_id.toString())
                            append("store_id", store_id.toString())
                            append("price", price)
                          if(productVarients.isNotEmpty())
                            productVarients.forEachIndexed { it,value->
                                append("productVarients[${it}].name",value.name)
                                append("productVarients[${it}].precentage",value.precentage!!)
                                append("productVarients[${it}].varient_id",value.varient_id.toString())
                            }
                            images.forEachIndexed { it,value->
                                append(
                                    key = "images", // Must match backend expectation
                                    value = value.readBytes(),
                                    headers = Headers.build {
                                        append(
                                            HttpHeaders.ContentType,
                                            "image/${value.extension}"
                                        )
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=${value.name}"
                                        )
                                    }
                                )
                                 }



                        }
                    )
                )
            }

            if(result.status== HttpStatusCode.Created){
                NetworkCallHandler.Successful(result.body<ProductResponseDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }

    suspend fun  updateProduct(
        id: UUID,
        name:String?,
        description:String?,
        thmbnail: File?,
        subcategory_id: UUID?,
        store_id: UUID,
        price: Double?,
        productVarients:List<ProductVarientSelection>?,
        images:List<File>?,
        deletedProductVarients:List<ProductVarientSelection>?,
        deletedimages:List<String>?

    ): NetworkCallHandler{
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product";
            val result = client.put (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("id",id.toString())
                            if(name!=null)
                            append("name", name)
                            if(description!=null)
                            append("description", description)
                            if(thmbnail!=null)
                            append(
                                key = "thmbnail", // Must match backend expectation
                                value = thmbnail.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        "image/${thmbnail.extension}"
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${thmbnail.name}"
                                    )
                                }
                            )
                            if(subcategory_id!=null)
                            append("subcategory_id", subcategory_id.toString())
                            append("store_id", store_id.toString())

                            if(price!=null)
                            append("price", price)

                            if(!productVarients.isNullOrEmpty())
                                productVarients.forEachIndexed { it,value->
                                    append("productVarients[${it}].name",value.name)
                                    append("productVarients[${it}].precentage",value.precentage!!)
                                    append("productVarients[${it}].varient_id",value.varient_id.toString())
                                }
                            if(!deletedProductVarients.isNullOrEmpty())
                                deletedProductVarients.forEachIndexed { it,value->
                                    append("deletedProductVarients[${it}].name",value.name)
                                    append("deletedProductVarients[${it}].precentage",value.precentage!!)
                                    append("deletedProductVarients[${it}].varient_id",value.varient_id.toString())

                                }

                            if(!deletedimages.isNullOrEmpty())
                                deletedimages.forEachIndexed { it,value->
                                    val startIndex= "staticFiles"
                                    val indexAt = value.indexOf("staticFiles")
                                    append("deletedimages[${it}]",value.substring(indexAt+startIndex.length,
                                        value.length))
                                }


                            if(!images.isNullOrEmpty())
                            images.forEachIndexed { it,value->
                                append(
                                    key = "images", // Must match backend expectation
                                    value = value.readBytes(),
                                    headers = Headers.build {
                                        append(
                                            HttpHeaders.ContentType,
                                            "image/${value.extension}"
                                        )
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=${value.name}"
                                        )
                                    }
                                )
                            }



                        }
                    )
                )
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<ProductResponseDto>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }


    suspend fun deleteProduct(store_id: UUID,product_id: UUID): NetworkCallHandler {
        return try {
            val full_url =Secrets.getBaseUrl()+"/Product/${store_id}/${product_id}";
            val result = client.delete  (full_url){
                headers{
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${General.authData.value?.refreshToken}"
                    )
                }
            }

            if(result.status== HttpStatusCode.OK){
                NetworkCallHandler.Successful(result.body<Boolean>())
            }else{
                NetworkCallHandler.Error(result.body<String>())
            }

        } catch (e: UnknownHostException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: IOException) {

            return NetworkCallHandler.Error(e.message)

        } catch (e: Exception) {

            return NetworkCallHandler.Error(e.message)
        }
    }




}