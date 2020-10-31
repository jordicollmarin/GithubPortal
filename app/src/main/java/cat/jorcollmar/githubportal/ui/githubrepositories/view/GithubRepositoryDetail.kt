package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cat.jorcollmar.githubportal.databinding.FragmentGithubRepositoryDetailBinding

class GithubRepositoryDetail : Fragment() {
    lateinit var binding: FragmentGithubRepositoryDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}