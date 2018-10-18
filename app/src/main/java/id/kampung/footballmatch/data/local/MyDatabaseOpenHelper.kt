package id.kampung.footballmatch.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {


    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteDao.TABLE_FAVORITE, true,
                FavoriteDao.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteDao.EVENT_ID to TEXT + UNIQUE,
                FavoriteDao.JSON_DATA to TEXT)

        db?.createTable(FavoriteTeamDao.TABLE_FAVORITE, true,
                FavoriteTeamDao.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeamDao.TEAM_ID to TEXT + UNIQUE,
                FavoriteDao.JSON_DATA to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteDao.TABLE_FAVORITE, true)
    }

}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)