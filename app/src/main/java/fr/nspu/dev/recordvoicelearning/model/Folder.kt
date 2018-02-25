package fr.nspu.dev.recordvoicelearning.model

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-03.
 */


interface Folder : CollectionObject {
    val name: String?
    val typeQuestion: String?
    val typeAnswer: String?
    val order: OrderPeerEnum?
    val questionToAnswer: QuestionToAnswerEnum?
}
