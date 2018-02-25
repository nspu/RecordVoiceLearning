package fr.nspu.dev.recordvoicelearning.controller

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting

import fr.nspu.dev.recordvoicelearning.AppExecutors
import fr.nspu.dev.recordvoicelearning.controller.converter.DateConverter
import fr.nspu.dev.recordvoicelearning.controller.converter.OrderPeerEnumConverter
import fr.nspu.dev.recordvoicelearning.controller.converter.QuestionToAnswerEnumConverter
import fr.nspu.dev.recordvoicelearning.controller.dao.FolderDao
import fr.nspu.dev.recordvoicelearning.controller.dao.PeerDao
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity

/**
 * Created by nspu on 18-02-04.
 */

@Database(entities = arrayOf(FolderEntity::class, PeerEntity::class), version = 1)
@TypeConverters(DateConverter::class, OrderPeerEnumConverter::class, QuestionToAnswerEnumConverter::class)
abstract class AppDatabase : RoomDatabase() {

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated

    abstract fun folderDao(): FolderDao

    abstract fun peerDao(): PeerDao

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    companion object {

        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "folder-peer-db"

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context.applicationContext, executors)
                        sInstance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }

            return sInstance
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context,
                                  executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO().execute {
                                val database = AppDatabase.getInstance(appContext, executors)
                                database!!.setDatabaseCreated()
                            }
                        }
                    }).build()
        }
    }
}
