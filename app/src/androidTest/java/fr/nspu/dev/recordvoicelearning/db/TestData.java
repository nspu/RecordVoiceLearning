package fr.nspu.dev.recordvoicelearning.db;

import java.util.Arrays;
import java.util.List;

import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;

/**
 * Created by nspu on 18-02-07.
 */

class TestData {
    static final FolderEntity FOLDER_ENTITY = new FolderEntity(1, "name", "type question", "type answer");
    private static final FolderEntity FOLDER_ENTITY2 = new FolderEntity(2, "name", "type question2", "type answer2");

    static final List<FolderEntity> FOLDERS = Arrays.asList(FOLDER_ENTITY, FOLDER_ENTITY2);

    static final PeerEntity PEER_ENTITY = new PeerEntity(1, FOLDER_ENTITY.getId(), "file name", "file name", 5,0);
    private static final PeerEntity PEER_ENTITY2 = new PeerEntity(2, FOLDER_ENTITY2.getId(), "file name2", "file name2", 6,0);

    static final List<PeerEntity> PEERS = Arrays.asList(PEER_ENTITY, PEER_ENTITY2);
}
