package com.example.todolistapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentAddEditTaskBinding
import com.example.todolistapp.data.model.Task
import com.example.todolistapp.data.repository.RepositoryImpl
import com.example.todolistapp.TodoApplication
import com.example.todolistapp.TodoApplication.Companion.applicationComponent
import com.example.todolistapp.ui.viewmodels.TaskViewModel
import com.example.todolistapp.ui.viewmodels.TaskViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddEditTaskFragment: Fragment() {

    @Inject
    lateinit var taskViewModelProviderFactory: TaskViewModelProviderFactory

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by activityViewModels { taskViewModelProviderFactory }

    private val args: AddEditTaskFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        applicationComponent.inject(this)
        super.onAttach(context)
    }

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
        setupAddTaskButtonClickListener(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAddTaskButtonClickListener(view: View) {
        binding.fabSaveTask.setOnClickListener {
            if (binding.editTextTaskName.text.isBlank()) {
                Snackbar.make(view, getString(R.string.task_name_is_empty), Snackbar.LENGTH_LONG).show()
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
}