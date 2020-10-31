package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cat.jorcollmar.githubportal.databinding.FragmentGithubRepositoriesListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GithubRepositoriesList : Fragment() {
    lateinit var binding: FragmentGithubRepositoriesListBinding
    private val viewModel: GithubRepositoriesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubRepositoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }
}