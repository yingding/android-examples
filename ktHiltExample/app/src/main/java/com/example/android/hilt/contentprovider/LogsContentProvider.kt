package com.example.android.hilt.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.android.hilt.data.LogDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


/** The authority of this content provider **/
private const val LOGS_TABLE = "logs"

/** The authority of this content provider **/
private const val AUTHORITY = "com.example.android.hilt.provider"

/** The match code for some items in the Logs table. **/
private const val CODE_LOGS_DIR = 1

/** The match code for an item in the Logs table. **/
private const val CODE_LOGS_ITEM = 2

/**
 * A ContentProvider that exposes the logs outside the application process.
 */
class LogsContentProvider: ContentProvider() {

    /* An entry point is an interface with an accessor method for each binding type we want
     * (including its qualifier). The interface must be annotated with @InstallIn to specify
     * the component in which to install the entry point.
     *
     * The best practice is adding the new entry point interface inside the class that uses it.
     * Therefore, include the interface here
     */
    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface LogsContentProviderEntryPoint {
        fun logDao(): LogDao
    }

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, LOGS_TABLE, CODE_LOGS_DIR)
        addURI(AUTHORITY, "$LOGS_TABLE/*", CODE_LOGS_ITEM)
    }

    override fun onCreate(): Boolean {
        return true
    }

    /**
     * Queries all the logs or an individual log from the logs database.
     *
     * For the sake of this codelab , the logic has been simplified.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code: Int = matcher.match(uri)
        return if (code == CODE_LOGS_DIR || code == CODE_LOGS_ITEM) {
            val appContext = context?.applicationContext ?: throw IllegalStateException()
            val logDao: LogDao = getLogDao(appContext)

            val cursor: Cursor? = if (code == CODE_LOGS_DIR) {
                logDao.selectAllLogsCursor()
            } else {
                logDao.selectLogById(ContentUris.parseId(uri))
            }
            cursor?.setNotificationUri(appContext.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    /**
     * nothing to insert, but still need to override this template
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    private fun getLogDao(appContext: Context): LogDao {
        // use EntryPointAccessors.fromApplication, since the EntryPoint interface is installed in application
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            LogsContentProviderEntryPoint::class.java
        )
        /* get from hilt entry point an object has the same type defined by the interface.
         * https://developer.android.com/codelabs/android-hilt?hl=en#10
         *
         * Notice that DatabaseModule provides an static LogDao binding in the application scope
         */
        return hiltEntryPoint.logDao()
    }
}