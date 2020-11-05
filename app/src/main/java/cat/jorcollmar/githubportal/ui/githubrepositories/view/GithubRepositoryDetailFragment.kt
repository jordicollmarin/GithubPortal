package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.extensions.kFormat
import cat.jorcollmar.githubportal.commons.extensions.loadImageCenterCrop
import cat.jorcollmar.githubportal.databinding.FragmentGithubRepositoryDetailBinding
import cat.jorcollmar.githubportal.ui.customviews.InfoItem
import org.koin.android.viewmodel.ext.android.sharedViewModel

class GithubRepositoryDetailFragment : Fragment() {
    lateinit var binding: FragmentGithubRepositoryDetailBinding
    val viewModel: GithubRepositoriesViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRepositoryDetails()
    }

    private fun setUpRepositoryDetails() {
        viewModel.selectedRepository?.let { repository ->
            with(repository) {
                binding.ctlRepositoryDetail.title = name

                binding.imvRepositoryDetail.loadImageCenterCrop(Uri.parse(owner?.avatarUrl))

                description?.let {
                    binding.lytContent.txvRepositoryDescription.text = it
                } ?: run {
                    binding.lytContent.txvRepositoryDescription.visibility = View.GONE
                }

                htmlUrl?.let {
                    binding.lytContent.txvRepositoryLink.text = it
                } ?: run {
                    binding.lytContent.txvRepositoryLink.visibility = View.GONE
                }

                stargazersCount?.let {
                    binding.lytContent.txvRepositoryStargazers.text = it.kFormat()
                } ?: run {
                    binding.lytContent.txvRepositoryStargazers.visibility = View.GONE
                }

                language?.let {
                    binding.lytContent.lytRepositoryInfoDetails.addView(InfoItem(requireContext()).apply {
                        setUpItem(getString(R.string.repository_detail_language_label), it)
                    })
                }

                owner?.login?.let {
                    binding.lytContent.lytRepositoryInfoDetails.addView(InfoItem(requireContext()).apply {
                        setUpItem(getString(R.string.repository_detail_owner_name_label), it)
                    })
                }

                license?.name?.let {
                    binding.lytContent.lytRepositoryInfoDetails.addView(InfoItem(requireContext()).apply {
                        setUpItem(getString(R.string.repository_detail_license_label), it)
                    })
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbarRepositoryDetail)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
            else -> super.onOptionsItemSelected(item)
        }
    }
}