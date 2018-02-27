package fr.nspu.dev.recordvoicelearning.utils

enum class OrderPeerEnum private constructor(i: Int) {
    KNOWLEDGE_ASCENDING(0),
    KNOWLEDGE_DESCENDING(1);

    internal var order: Int? = 0

    init {
        this.order = i
    }

    fun toInt(): Int = order!!

    companion object {
        fun toOrderPeerEnum(id: Int?): OrderPeerEnum {
            when (id) {
                0 -> return KNOWLEDGE_ASCENDING
                1 -> return KNOWLEDGE_DESCENDING
                else -> return KNOWLEDGE_ASCENDING
            }
        }
    }
}