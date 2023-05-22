package hr.tvz.android.listapetermanec

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room

class DetailFragment : Fragment() {
    private var exerc: Exerc = Exerc(99, "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(requireActivity().applicationContext, BazaPodataka::class.java, "newTest").allowMainThreadQueries().build()
        val exercDao = db.exercDao()

        if(arguments?.containsKey("id") == true) {
            val id = requireArguments().getString("id")?.toInt()
            exerc = exercDao.getAll()[id!!]
        }
    }

    @SuppressLint("MissingInflatedId", "DiscouragedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =inflater.inflate(R.layout.item_detail_fragment, container, false)
        (root.findViewById(R.id.item_detail) as TextView).text = exerc.name
        (root.findViewById(R.id.imageView) as ImageView).setImageResource(resources.getIdentifier("e" + (exerc.id), "drawable", activity?.packageName))
        (root.findViewById(R.id.ex_desc) as TextView).text = exerc.description
        return root
    }
}