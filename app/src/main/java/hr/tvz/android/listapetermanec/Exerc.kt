package hr.tvz.android.listapetermanec

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exerc(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
)