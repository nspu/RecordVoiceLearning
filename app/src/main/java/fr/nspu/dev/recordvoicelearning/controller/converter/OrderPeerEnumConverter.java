package fr.nspu.dev.recordvoicelearning.controller.converter;

import android.arch.persistence.room.TypeConverter;

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;


/**
 * Created by nspu on 18-02-22.
 */

public class OrderPeerEnumConverter {
    @TypeConverter
    public static OrderPeerEnum toOrderPeerEnum(int id) {
        return OrderPeerEnum.toOrderPeerEnum(id);
    }

    @TypeConverter
    public static int toInt(OrderPeerEnum orderPeerEnum) {
        return orderPeerEnum == null ? 0 : orderPeerEnum.toInt();
    }
}
