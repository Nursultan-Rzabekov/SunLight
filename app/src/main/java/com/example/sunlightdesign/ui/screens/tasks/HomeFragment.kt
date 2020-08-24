

package com.example.sunlightdesign.ui.screens.tasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sunlightdesign.BaseApplication
import javax.inject.Inject

/**
 * Display a grid of [Task]s. User can choose to view all, active or completed tasks.
 */
class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    //private val args: TasksFragmentArgs by navArgs()

    //private lateinit var viewDataBinding: TasksFragBinding

//    private lateinit var listAdapter: TasksAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BaseApplication).appComponent.tasksComponent().create().inject(this)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        viewDataBinding = TasksFragBinding.inflate(inflater, container, false).apply {
////            viewmodel = viewModel
////        }
////        setHasOptionsMenu(true)
////        return viewDataBinding.root
//    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
//        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
//        setupSnackbar()
//        setupListAdapter()
//        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.tasksList)
//        setupNavigation()
    }

    private fun setupNavigation() {

    }

    private fun setupSnackbar() {
//        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
//        arguments?.let {
//            viewModel.showEditResultMessage(args.userMessage)
//        }
    }

//    private fun setupListAdapter() {
//        val viewModel = viewDataBinding.viewmodel
//        if (viewModel != null) {
//            listAdapter = TasksAdapter(viewModel)
//            viewDataBinding.tasksList.adapter = listAdapter
//        } else {
//            Timber.w("ViewModel not initialized when attempting to set up adapter.")
//        }
//    }
}
