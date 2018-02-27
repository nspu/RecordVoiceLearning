package fr.nspu.dev.recordvoicelearning.db

import java.util.Arrays

import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity

/**
 * Created by nspu on 18-02-07.
 */

internal object TestData {
    val FOLDER_ENTITY = FolderEntity(1, "name", "type question", "type answer")
    private val FOLDER_ENTITY2 = FolderEntity(2, "name", "type question2", "type answer2")

    val FOLDERS = Arrays.asList(FOLDER_ENTITY, FOLDER_ENTITY2)

    val PEER_ENTITY = PeerEntity(1, FOLDER_ENTITY.id!!, "file name", "file name", 5, 0)
    private val PEER_ENTITY2 = PeerEntity(2, FOLDER_ENTITY2.id!!, "file name2", "file name2", 6, 0)

    val PEERS = Arrays.asList(PEER_ENTITY, PEER_ENTITY2)
}
