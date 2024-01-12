package mx.com.moonsmileh.perritos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mx.com.moonsmileh.perritos.databinding.ActivityMainBinding
import mx.com.moonsmileh.perritos.network.ApiService
import retrofit2.create

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogsAdapter

    private val viewModel: DogsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        binding.searchView.setOnQueryTextListener(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogsState.collect { uiState ->
                    when (uiState) {
                        is DogsUiState.Success -> showDogs(uiState.dogs)
                        is DogsUiState.Error -> showError()
                    }
                }
            }
        }
    }


    private fun initRecyclerView() {
        adapter = DogsAdapter(emptyList())
        binding.recyclerViewDogs.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewDogs.adapter = adapter
    }

    private fun searchByName(query: String) {
        viewModel.fetchUsers(query)
    }

    private fun showDogs(dogs: List<String>) {
        hideKeyboard()
        adapter.dogs = dogs
        adapter.notifyDataSetChanged()
    }

    private fun showError() {
        Toast.makeText(this, "Error fetching dogs", Toast.LENGTH_SHORT).show()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty())
            searchByName(query.lowercase())
        return true
    }

    override fun onQueryTextChange(newText: String?) = true

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewParent.windowToken, 0)
    }
}