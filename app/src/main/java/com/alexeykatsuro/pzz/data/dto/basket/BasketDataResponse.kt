package com.alexeykatsuro.pzz.data.dto.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketDataResponse(
    @Json(name = "id")
    val id: Any? = null,
    @Json(name = "sync")
    val sync: String? = null,
    @Json(name = "num")
    val num: String? = null,
    @Json(name = "on_edit")
    val onEdit: Int? = null,
    @Json(name = "full_edit")
    val fullEdit: Int? = null,
    @Json(name = "created_date")
    val createdDate: String? = null,
    @Json(name = "client_name")
    val clientName: String? = null,
    @Json(name = "price")
    val price: Int? = null,
    @Json(name = "total")
    val total: Int? = null,
    @Json(name = "total_mixed")
    val totalMixed: Int? = null,
    @Json(name = "delivery")
    val delivery: Int? = null,
    @Json(name = "base_delivery")
    val baseDelivery: Int? = null,
    @Json(name = "delivery_time")
    val deliveryTime: Int? = null,
    @Json(name = "pizzeria_id")
    val pizzeriaId: Int? = null,
    @Json(name = "pizzeria_type")
    val pizzeriaType: String? = null,
    @Json(name = "to_entrance")
    val toEntrance: Int? = null,
    @Json(name = "public_comment")
    val publicComment: String? = null,
    @Json(name = "coupon_id")
    val couponId: Int? = null,
    @Json(name = "coupon_code")
    val couponCode: String? = null,
    @Json(name = "self_discount")
    val selfDiscount: Int? = null,
    @Json(name = "discount_id")
    val discountId: Int? = null,
    @Json(name = "discount")
    val discount: Int? = null,
    @Json(name = "discount_type")
    val discountType: String? = null,
    @Json(name = "discount_unit")
    val discountUnit: String? = null,
    @Json(name = "discount_comment")
    val discountComment: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "renting")
    val renting: String? = null,
    @Json(name = "item_list_flat")
    val itemListFlat: String? = null,
    @Json(name = "comment")
    val comment: String? = null,
    @Json(name = "failure_comment")
    val failureComment: String? = null,
    @Json(name = "failure_acceptor")
    val failureAcceptor: Any? = null,
    @Json(name = "phone_comment")
    val phoneComment: String? = null,
    @Json(name = "preorder_date")
    val preorderDate: String? = null,
    @Json(name = "preorder_time")
    val preorderTime: String? = null,
    @Json(name = "payment")
    val payment: String? = null,
    @Json(name = "self_delivery_time")
    val selfDeliveryTime: Any? = null,
    @Json(name = "is_delivery")
    val isDelivery: Int? = null,
    @Json(name = "is_specgrill")
    val isSpecgrill: Int? = null,
    @Json(name = "specgrill_comment")
    val specgrillComment: String? = null,
    @Json(name = "is_15_min")
    val is15Min: Int? = null,
    @Json(name = "is_slowed")
    val isSlowed: Int? = null,
    @Json(name = "is_moved")
    val isMoved: Int? = null,
    @Json(name = "is_meal")
    val isMeal: Int? = null,
    @Json(name = "is_external")
    val isExternal: Int? = null,
    @Json(name = "is_payment_delivery")
    val isPaymentDelivery: Int? = null,
    @Json(name = "force_payment_delivery")
    val forcePaymentDelivery: Int? = null,
    @Json(name = "is_thin_pizza_available")
    val isThinPizzaAvailable: Int? = null,
    @Json(name = "is_former_employee")
    val isFormerEmployee: Int? = null,
    @Json(name = "is_current_employee")
    val isCurrentEmployee: Int? = null,
    @Json(name = "is_group_cc_meal")
    val isGroupCcMeal: Int? = null,
    @Json(name = "no_contact_on_delivery")
    val noContactOnDelivery: Int? = null,
    @Json(name = "save_new_address")
    val saveNewAddress: Int? = null,
    @Json(name = "meal_employee_id")
    val mealEmployeeId: Any? = null,
    @Json(name = "meal_restriction_id")
    val mealRestrictionId: Any? = null,
    @Json(name = "external")
    val `external`: String? = null,
    @Json(name = "external_id")
    val externalId: String? = null,
    @Json(name = "is_disabled_payment_by_card")
    val isDisabledPaymentByCard: Boolean? = null,
    @Json(name = "basket_at")
    val basketAt: String? = null,
    @Json(name = "is_disabled_no_contact_delivery")
    val isDisabledNoContactDelivery: Boolean? = null,
    @Json(name = "route_spell")
    val routeSpell: String? = null,
    @Json(name = "pizzeria_is_slowed")
    val pizzeriaIsSlowed: Int? = null,
    @Json(name = "doubles")
    val doubles: List<Any?>? = null,
    @Json(name = "free_sticks_count")
    val freeSticksCount: Int? = null,
    @Json(name = "free_garnishes_count")
    val freeGarnishesCount: Int? = null,
    @Json(name = "free_warmers_count")
    val freeWarmersCount: Int? = null,
    @Json(name = "items")
    val items: List<Products>? = null,
    @Json(name = "address")
    val address: Address? = null,
    @Json(name = "public_comment_html")
    val publicCommentHtml: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Products(
        @Json(name = "type")
        val type: String? = null,
        @Json(name = "id")
        val id: Long? = null,
        @Json(name = "size")
        val size: String? = null,
        @Json(name = "dough")
        val dough: String? = null,
        @Json(name = "is_failure")
        val isFailure: Int? = null,
        @Json(name = "is_3in2")
        val is3in2: Boolean? = null,
        @Json(name = "with_sauce")
        val withSauce: Boolean? = null,
        @Json(name = "parts")
        val parts: Int? = null,
        @Json(name = "item_id")
        val itemId: Int? = null,
        @Json(name = "is_new")
        val isNew: Boolean? = null,
        @Json(name = "auto_removed")
        val autoRemoved: Boolean? = null,
        @Json(name = "title")
        val title: String? = null,
        @Json(name = "free_sauces_count")
        val freeSaucesCount: Int? = null,
        @Json(name = "to_remove")
        val toRemove: Any? = null,
        @Json(name = "to_soft_remove")
        val toSoftRemove: Int? = null,
        @Json(name = "packed")
        val packed: Int? = null,
        @Json(name = "prepared")
        val prepared: Int? = null,
        @Json(name = "is_free")
        val isFree: Int? = null,
        @Json(name = "price")
        val price: Int? = null,
        @Json(name = "auto_add_type")
        val autoAddType: String? = null,
        @Json(name = "auto_add_id")
        val autoAddId: String? = null,
        @Json(name = "auto_add_size")
        val autoAddSize: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Address(
        @Json(name = "street_id")
        val streetId: Int? = null,
        @Json(name = "house_id")
        val houseId: Int? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "phone")
        val phone: String? = null,
        @Json(name = "street")
        val street: String? = null,
        @Json(name = "house")
        val house: String? = null,
        @Json(name = "flat")
        val flat: String? = null,
        @Json(name = "entrance")
        val entrance: String? = null,
        @Json(name = "floor")
        val floor: String? = null,
        @Json(name = "intercom")
        val intercom: String? = null
    )
}