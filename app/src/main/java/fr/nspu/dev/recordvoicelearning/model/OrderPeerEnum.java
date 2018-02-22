package fr.nspu.dev.recordvoicelearning.model;

public enum OrderPeerEnum{
        //Order
        ORDER_KNOWLEDGE_ASCENDING(0),
        ORDER_KNOWLEDGE_DESCENDING(1);

        int order = 0;

        OrderPeerEnum(int i) {
            this.order = i;
        }

        public int toInt(){
            return order;
        }

        public static OrderPeerEnum toOrderPeerEnum(int id){
            switch (id){
                case 0:
                    return ORDER_KNOWLEDGE_ASCENDING;
                case 1:
                    return ORDER_KNOWLEDGE_DESCENDING;
                default:
                    return ORDER_KNOWLEDGE_ASCENDING;
            }
        }
}