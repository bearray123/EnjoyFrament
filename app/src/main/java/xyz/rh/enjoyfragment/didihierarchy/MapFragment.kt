package xyz.rh.enjoyfragment.didihierarchy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.rh.enjoyfragment.R

/**
 * Created by rayxiong on 2024/10/16.
 */
class MapFragment : Fragment() {

    private var rootView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false) as ViewGroup?
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}