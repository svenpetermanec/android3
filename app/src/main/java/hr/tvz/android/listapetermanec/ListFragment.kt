package hr.tvz.android.listapetermanec

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.room.Room

open class ListFragment : ListFragment() {
    interface Callbacks {
        fun onItemSelected(id: String?)
    }

    private lateinit var mCallbacks: Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(requireActivity().applicationContext, BazaPodataka::class.java, "newTest").allowMainThreadQueries().build()
        val exercDao = db.exercDao()

        listAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.simple_list_item_activated_1,
            R.id.text1,
            exercDao.getAll().map{ it.name})
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        check(context is Callbacks) { "Activity must implement fragment's callbacks." }

        mCallbacks = context
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        mCallbacks.onItemSelected(position.toString())
    }
}