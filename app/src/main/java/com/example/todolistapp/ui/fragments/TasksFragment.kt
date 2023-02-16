package com.example.todolistapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.adapters.TasksAdapter
import com.example.todolistapp.databinding.FragmentTasksBinding
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import com.example.todolistapp.ui.TaskViewModel
import com.example.todolistapp.ui.TaskViewModelProviderFactory
import com.example.todolistapp.ui.TodoApplication
import kotlinx.coroutines.launch

class TasksFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private var tasksAdapter: TasksAdapter? = null
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelProviderFactory(TaskRepository((activity?.application as TodoApplication).database))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_tasks, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@TasksFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort_by_name -> {
                        true
                    }
                    R.id.action_sort_by_date_created -> {
                        true
                    }
                    R.id.action_hide_completed_tasks -> {
                        menuItem.isChecked = !menuItem.isChecked
                        true
                    }
                    R.id.action_delete_all_completed_tasks -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        lifecycleScope.launch {
            viewModel.taskRepository.insert(Task("asdasdasdasd"))
            viewModel.taskRepository.insert(Task("Buy groceries", important = true))
            viewModel.taskRepository.insert(Task("Prepare food", completed = true))
        }

        viewModel.task.observe(viewLifecycleOwner) {
            tasksAdapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        tasksAdapter = TasksAdapter()
        binding.apply {
            recyclerViewTasks.adapter = tasksAdapter
            recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            viewModel.searchQuery.value = newText
        }
        return true
    }
}

