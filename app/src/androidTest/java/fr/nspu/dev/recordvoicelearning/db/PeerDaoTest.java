package fr.nspu.dev.recordvoicelearning.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.LiveDataTestUtil;
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase;
import fr.nspu.dev.recordvoicelearning.controller.dao.FolderDao;
import fr.nspu.dev.recordvoicelearning.controller.dao.PeerDao;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;

import static fr.nspu.dev.recordvoicelearning.db.TestData.*;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nspu on 18-02-07.
 */
@RunWith(AndroidJUnit4.class)
public class PeerDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private PeerDao mPeerDao;

    private FolderDao mFolderDao;

    @BeforeEach
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mPeerDao = mDatabase.peerDao();
        mFolderDao = mDatabase.folderDao();
    }

    @AfterEach
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getPeersWhenNoPeerInserted() throws InterruptedException {
        List<PeerEntity> comments = LiveDataTestUtil.getValue(mPeerDao.loadPeerByFolderId
                (PEER_ENTITY.getFolderId()));

        assertTrue(comments.isEmpty());
    }

    @org.junit.jupiter.api.Test
    public void cantInsertPeerWithoutProduct() throws InterruptedException {
        try {
            mPeerDao.insertPeersSync(PEERS);
            fail("SQLiteConstraintException expected");
        } catch (SQLiteConstraintException ignored) {

        }
    }

    @org.junit.jupiter.api.Test
    public void getPeersAfterInserted() throws InterruptedException {
        mFolderDao.insertFoldersSync(FOLDERS);
        mPeerDao.insertPeersSync(PEERS);

        List<PeerEntity> comments = LiveDataTestUtil.getValue(
                mPeerDao.loadPeerByFolderId(PEER_ENTITY.getFolderId()));

        assertThat(comments.size(), is(1));
    }

    @org.junit.jupiter.api.Test
    public void getPeerByFolderId() throws InterruptedException {
        mFolderDao.insertFoldersSync(FOLDERS);
        mPeerDao.insertPeersSync(PEERS);

        List<PeerEntity> comments = LiveDataTestUtil.getValue(mPeerDao.loadPeerByFolderId(
                (PEER_ENTITY.getFolderId())));

        assertThat(comments.size(), is(1));
    }
}
