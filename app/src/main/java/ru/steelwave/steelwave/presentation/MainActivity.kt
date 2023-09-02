package ru.steelwave.steelwave.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ActivityMainBinding
import ru.steelwave.steelwave.presentation.main.project.AddProjectModal
import ru.steelwave.steelwave.presentation.modals.ConfirmExitAccountModal

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var stateProfileMenu = false
    private val rotateAnimation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_animation) }
    private val rotateReverseAnimation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_animation_reverse) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()
        navigationBottomMenu()
        settingsView()
        controlProfileMenu()
    }

    private fun settingsView(){
        setListenersInView()
    }

    private fun setListenersInView(){
        setListenerButtonsInProfileMenu()
    }

    private fun setListenerButtonsInProfileMenu(){
        with(binding) {
            btnRules.setOnClickListener{
                findNavController(R.id.fragment_container).navigate(R.id.rulesFragmentInMain)
                closeProfileMenu()
            }
            btnExit.setOnClickListener {
                ConfirmExitAccountModal().also {
                    it.show(supportFragmentManager, TAG_DIALOG_EXIT_ACCOUNT)
                }
                closeProfileMenu()
            }
        }
    }

    private fun controlProfileMenu(){
        binding.clProfile.setOnClickListener {
            if (stateProfileMenu){
                closeProfileMenu()
            } else {
                openProfileMenu()
            }
        }
    }

    private fun closeProfileMenu(){
        binding.cvProfile.visibility = View.GONE
        binding.ivDrop.startAnimation(rotateReverseAnimation)
        stateProfileMenu = false
    }

    private fun openProfileMenu(){
        binding.cvProfile.visibility = View.VISIBLE
        binding.ivDrop.startAnimation(rotateAnimation)
        stateProfileMenu = true
    }

    private fun navigationBottomMenu() {
        val navController = findNavController(R.id.fragment_container)
        val setFragments = setOf(
            R.id.projectFragment,
            R.id.financeFragment,
            R.id.traficFragment,
            R.id.employeesFragment,
            R.id.adsFragment
        )
        val appBarConfigurator = AppBarConfiguration(
            setFragments
        )
        setupActionBarWithNavController(navController, appBarConfigurator)
        binding.bottomMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentBackStackEntry = navController.currentBackStackEntry
            if (destination.id in setFragments && currentBackStackEntry?.destination?.id != destination.id) {
                navController.navigate(destination.id)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.icon_back)
        }
    }

    companion object{
        private const val TAG_DIALOG_EXIT_ACCOUNT = "tag_dialog_exit_account"
    }

}