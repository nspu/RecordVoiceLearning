package fr.nspu.dev.recordvoicelearning.model

/**
 * Created by nspu on 18-02-03.
 */

interface Peer : CollectionObject {
    val folderId: Int?
    val fileNameQuestion: String?
    val fileNameAnswer: String?
    val count: Int?
    val knowledge: Int?
}
