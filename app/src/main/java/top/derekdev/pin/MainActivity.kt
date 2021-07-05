package top.derekdev.pin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import top.derekdev.pin.databinding.ActivityMainBinding
import top.derekdev.pin.databinding.ViewMainInputBinding


/**
 * Created by  on 5/7/21.
 * Project: Pin Lock
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            viewmodel = this@MainActivity.viewModel
        }
    }

}
