package com.alexeykatsuro.pzz

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexeykatsuro.pzz.base.DaggerMvRxActivity
import com.alexeykatsuro.pzz.databinding.ActivityMainBinding
import com.alexeykatsuro.pzz.utils.NavControllerOwner
import com.alexeykatsuro.pzz.utils.delegates.lazyFast
import com.alexeykatsuro.pzz.utils.extensions.hideSoftInput

class MainActivity : DaggerMvRxActivity(), NavControllerOwner {

    private lateinit var binding: ActivityMainBinding

    override val navController: NavController by lazyFast { navController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Pzz)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, _, _ ->
                // Ensure that the keyboard is dismissed when we navigate between fragments
                hideSoftInput()
            }
        }
    }

    override fun invalidate() = Unit

    private fun navController(): NavController {
        val host =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        return host.navController
    }
}