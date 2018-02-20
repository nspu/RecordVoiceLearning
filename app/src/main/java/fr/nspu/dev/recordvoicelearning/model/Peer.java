package fr.nspu.dev.recordvoicelearning.model;

/**
 * Created by nspu on 18-02-03.
 */

public interface Peer extends CollectionObject {
     int getFolderId();
     String getFileNameQuestion();
     String getFileNameAnswer();
     int getCount();
     int getKnowledge();
}
