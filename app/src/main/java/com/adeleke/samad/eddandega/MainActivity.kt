package com.adeleke.samad.eddandega

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adeleke.samad.eddandega.anthro.AnthroFragment
import com.adeleke.samad.eddandega.databinding.ActivityMainBinding
import com.adeleke.samad.eddandega.fluid.FluidFragment
import com.adeleke.samad.eddandega.lmp.LMPFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private var lmpFragment: LMPFragment? = null
    private var weightFragment: AnthroFragment? = null
    private var fluidFragment: FluidFragment? = null

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Have Access to the toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize the fragments
        lmpFragment = LMPFragment()
        weightFragment = AnthroFragment()
        fluidFragment = FluidFragment()


        sharedPreferences = getSharedPreferences("com.adeleke.samad.eddandega", MODE_PRIVATE)

        when (sharedPreferences.getInt("night", 0)) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Configure tab layout and viewPager
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(lmpFragment!!, "EDD")
        viewPagerAdapter.addFragment(weightFragment!!, "Weight")
        viewPagerAdapter.addFragment(fluidFragment!!, "Fluid")
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_pregnant_woman)
        binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_child)
        binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_water_drop)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var value = 0
        when (item.itemId) {
            R.id.night_on -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                value = 1
            }
            R.id.night_off -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                value = 2
            }
            R.id.night_default -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                value = 0
            }
        }
        editor = sharedPreferences.edit();
        editor.putInt("night", value);
        editor.apply()
        return false
    }

    private inner class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
        private val fragments: MutableList<Fragment> = ArrayList()
        private val fragmentTitle: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            fragmentTitle.add(title)
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitle[position]
        }
    }

}