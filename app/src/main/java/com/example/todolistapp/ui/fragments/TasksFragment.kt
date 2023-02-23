package com.example.todolistapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.adapters.TasksAdapter
import com.example.todolistapp.databinding.FragmentTasksBinding
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import com.example.todolistapp.ui.viewmodels.TaskViewModel
import com.example.todolistapp.ui.viewmodels.TaskViewModelProviderFactory
import com.example.todolistapp.ui.TodoApplication
import com.google.android.material.snackbar.Snackbar

class TasksFragment : Fragment(), SearchView.OnQueryTextListener, TasksAdapter.OnItemClickListener {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private var tasksAdapter: TasksAdapter? = null
    private val viewModel: TaskViewModel by activityViewModels {
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

        binding.fabAddTask.setOnClickListener {
            openAddEditFragment()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = tasksAdapter!!.currentList[viewHolder.adapterPosition]
                viewModel.delete(task)
                Snackbar.make(requireView(), "Task deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        viewModel.insert(task)
                    }.show()

            }
        }).attachToRecyclerView(binding.recyclerViewTasks)


        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_tasks, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@TasksFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.task.observe(viewLifecycleOwner) {
            tasksAdapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(task: Task) {
        findNavController().navigate(
            R.id.action_tasksFragment_to_addEditTaskFragment,
            bundleOf("task" to task)
        )
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.update(task.copy(completed = isChecked))
    }

    private fun setupRecyclerView() {
        tasksAdapter = TasksAdapter(this@TasksFragment)
        binding.apply {
            recyclerViewTasks.adapter = tasksAdapter
            recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun openAddEditFragment() {
        val direction = TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(null)
        findNavController().navigate(direction)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            viewModel.searchTasks(query).observe(this) {
                tasksAdapter?.submitList(it)
            }
        }
        return true
    }
}
