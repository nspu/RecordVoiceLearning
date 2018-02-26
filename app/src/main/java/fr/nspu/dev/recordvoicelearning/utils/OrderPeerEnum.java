package fr.nspu.dev.recordvoicelearning.utils;

public enum OrderPeerEnum{
        KNOWLEDGE_ASCENDING(0),
        KNOWLEDGE_DESCENDING(1);

        Integer order = 0;

        OrderPeerEnum(int i) {
            this.order = i;
        }

        public int toInt(){
            return order;
        }

        public static OrderPeerEnum toOrderPeerEnum(Integer id){
            switch (id){
                case 0:
                    return KNOWLEDGE_ASCENDING;
                case 1:
                    return KNOWLEDGE_DESCENDING;
                default:
                    //return default value
                    return KNOWLEDGE_ASCENDING;
            }
        }
}