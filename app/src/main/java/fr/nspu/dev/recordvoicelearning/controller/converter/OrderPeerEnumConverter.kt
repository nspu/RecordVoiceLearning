package fr.nspu.dev.recordvoicelearning.controller.converter

import android.arch.persistence.room.TypeConverter

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum


/**
 * Created by nspu on 18-02-22.
 */

object OrderPeerEnumConverter {
    @TypeConverter
    fun toOrderPeerEnum(id: Int?): OrderPeerEnum = OrderPeerEnum.toOrderPeerEnum(id!!)

    @TypeConverter
    fun toInt(orderPeerEnum: OrderPeerEnum?): Int? = orderPeerEnum?.let { 0 }
}
