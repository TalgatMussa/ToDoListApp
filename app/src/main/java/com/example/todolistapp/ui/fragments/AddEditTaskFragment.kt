package com.example.todolistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistapp.databinding.FragmentAddEditTaskBinding
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import com.example.todolistapp.ui.TodoApplication
import com.example.todolistapp.ui.viewmodels.TaskViewModel
import com.example.todolistapp.ui.viewmodels.TaskViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar

class AddEditTaskFragment: Fragment() {

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelProviderFactory(TaskRepository((activity?.application as TodoApplication).database))
    }

    private val args: AddEditTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task = args.task
        binding.editTextTaskName.setText(task?.name)
        binding.textViewDateCreated.text = task?.createdDateFormatted

        binding.fabSaveTask.setOnClickListener {
            if (binding.editTextTaskName.text.isBlank()) {
                Snackbar.make(view, "Task name is empty", Snackbar.LENGTH_LONG).show()
            } else {
                val name = binding.editTextTaskName.text.toString()
                if (args.task?.id == null) {
                    viewModel.insert(Task(name = name))
                    findNavController().popBackStack()
                } else {
                    viewModel.update(Task(id = args.task!!.id,name = name))
                    findNavController().popBackStack()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}