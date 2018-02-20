package fr.nspu.dev.recordvoicelearning.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import junit.framework.Assert;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.LiveDataTestUtil;
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase;
import fr.nspu.dev.recordvoicelearning.controller.dao.FolderDao;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;

import static fr.nspu.dev.recordvoicelearning.db.TestData.FOLDERS;
import static fr.nspu.dev.recordvoicelearning.db.TestData.FOLDER_ENTITY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nspu on 18-02-07.
 */
@RunWith(AndroidJUnit4.class)
public class FolderDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private FolderDao mFolderDao;

    @Before
    public void initDb() throws Exception{
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        mFolderDao = mDatabase.folderDao();
    }

    @After
    public void closeDb() throws Exception{
        mDatabase.close();
    }

    @Test
    public void getFoldersWhenNoFolderInserted() throws InterruptedException{
        List<FolderEntity> folder = LiveDataTestUtil.getValue(mFolderDao.loadAllFolders());

        Assert.assertTrue(folder.isEmpty());
    }

    @Test
    public void getProductsAfterInserted() throws InterruptedException {
        mFolderDao.insertFoldersSync(FOLDERS);

        List<FolderEntity> folders = LiveDataTestUtil.getValue(mFolderDao.loadAllFolders());

        assertThat(folders.size(), is(FOLDERS.size()));
    }

    @Test
    public void getProductById() throws InterruptedException {
        mFolderDao.insertFoldersSync(FOLDERS);

        FolderEntity product = LiveDataTestUtil.getValue(mFolderDao
                .loadFolderById(FOLDER_ENTITY.getId()));

        assertThat(product.getId(), is(FOLDER_ENTITY.getId()));
        assertThat(product.getName(), is(FOLDER_ENTITY.getName()));
        assertThat(product.getTypeAnswer(), is(FOLDER_ENTITY.getTypeAnswer()));
        assertThat(product.getTypeQuestion(), is(FOLDER_ENTITY.getTypeQuestion()));
        assertThat(product.getCreatedAt(), is(FOLDER_ENTITY.getCreatedAt()));
        assertThat(product.getUpdatedAt(), is(FOLDER_ENTITY.getUpdatedAt()));
    }
}
