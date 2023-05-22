package hr.tvz.android.listapetermanec

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExercDao {
    @Query("SELECT * FROM Exerc ORDER BY id ASC")
    fun getAll(): MutableList<Exerc>

    @Insert
    fun insertOne(exerc: Exerc)
}