package fr.nspu.dev.recordvoicelearning.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.arch.core.executor.testing.InstantTaskExecutorRule

import junit.framework.Assert

import fr.nspu.dev.recordvoicelearning.LiveDataTestUtil
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase
import fr.nspu.dev.recordvoicelearning.controller.dao.FolderDao
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import java.util.*

/**
 * Created by nspu on 18-02-07.
 */
@RunWith(AndroidJUnit4::class)
class FolderDaoTest {
    private var mDatabase: AppDatabase? = null

    private var mFolderDao: FolderDao? = null

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        mFolderDao = mDatabase!!.folderDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase!!.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getFoldersWhenNoFolderInserted() {
        val folder = LiveDataTestUtil.getValue(mFolderDao!!.loadAllFolders())

        Assert.assertTrue(folder.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getProductsAfterInserted() {
        mFolderDao!!.insertFoldersSync(TestData.FOLDERS)

        val folders = LiveDataTestUtil.getValue(mFolderDao!!.loadAllFolders())

        assertThat(folders.size, `is`<Int>(TestData.FOLDERS.size))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getProductById() {
        mFolderDao!!.insertFoldersSync(TestData.FOLDERS)

        val product = LiveDataTestUtil.getValue(mFolderDao!!
                .loadFolderById(TestData.FOLDER_ENTITY.id!!))

        assertThat<Int>(product.id, `is`<Int>(TestData.FOLDER_ENTITY.id))
        assertThat<String>(product.name, `is`<String>(TestData.FOLDER_ENTITY.name))
        assertThat<String>(product.typeAnswer, `is`<String>(TestData.FOLDER_ENTITY.typeAnswer))
        assertThat<String>(product.typeQuestion, `is`<String>(TestData.FOLDER_ENTITY.typeQuestion))
        assertThat<Date>(product.createdAt, `is`<Date>(TestData.FOLDER_ENTITY.createdAt))
        assertThat<Date>(product.updatedAt, `is`<Date>(TestData.FOLDER_ENTITY.updatedAt))
    }
}
