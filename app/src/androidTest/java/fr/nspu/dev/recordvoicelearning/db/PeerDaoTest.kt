package fr.nspu.dev.recordvoicelearning.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import fr.nspu.dev.recordvoicelearning.LiveDataTestUtil
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase
import fr.nspu.dev.recordvoicelearning.controller.dao.FolderDao
import fr.nspu.dev.recordvoicelearning.controller.dao.PeerDao
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity
import junit.framework.Assert.assertTrue
import junit.framework.Assert.fail
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat

/**
 * Created by nspu on 18-02-07.
 */
@RunWith(AndroidJUnit4::class)
class PeerDaoTest {

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var mDatabase: AppDatabase? = null

    private var mPeerDao: PeerDao? = null

    private var mFolderDao: FolderDao? = null

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()

        mPeerDao = mDatabase!!.peerDao()
        mFolderDao = mDatabase!!.folderDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase!!.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPeersWhenNoPeerInserted() {
        val comments = LiveDataTestUtil.getValue(mPeerDao!!.loadPeerByFolderId(TestData.PEER_ENTITY.folderId!!))

        assertTrue(comments.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun cantInsertPeerWithoutProduct() {
        try {
            mPeerDao!!.insertPeersSync(TestData.PEERS)
            fail("SQLiteConstraintException expected")
        } catch (ignored: SQLiteConstraintException) {

        }

    }

    @Test
    @Throws(InterruptedException::class)
    fun getPeersAfterInserted() {
        mFolderDao!!.insertFoldersSync(TestData.FOLDERS)
        mPeerDao!!.insertPeersSync(TestData.PEERS)

        val comments = LiveDataTestUtil.getValue(
                mPeerDao!!.loadPeerByFolderId(TestData.PEER_ENTITY.folderId!!))

        assertThat(comments.size, `is`(1))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPeerByFolderId() {
        mFolderDao!!.insertFoldersSync(TestData.FOLDERS)
        mPeerDao!!.insertPeersSync(TestData.PEERS)

        val comments = LiveDataTestUtil.getValue(mPeerDao!!.loadPeerByFolderId(
                TestData.PEER_ENTITY.folderId!!))

        assertThat(comments.size, `is`(1))
    }
}
