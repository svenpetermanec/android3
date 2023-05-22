package hr.tvz.android.listapetermanec

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Exerc::class], version = 1)
abstract class BazaPodataka : RoomDatabase() {
    abstract fun exercDao(): ExercDao
}