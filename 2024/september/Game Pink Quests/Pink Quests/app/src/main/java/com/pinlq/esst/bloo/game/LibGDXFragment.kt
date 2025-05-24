package com.pinlq.esst.bloo.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.pinlq.esst.bloo.GameActivity

class LibGDXFragment : AndroidFragmentApplication() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conf = AndroidApplicationConfiguration().apply {
            a = 8
            useAccelerometer = true
            useCompass = false
        }

        return initializeForView(LibGDXGame(requireActivity() as GameActivity), conf)
    }
}