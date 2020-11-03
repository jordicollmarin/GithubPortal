package cat.jorcollmar.githubportal.ui.githubrepositories.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.databinding.GithubRepositoryItemBinding
import cat.jorcollmar.githubportal.commons.extensions.kFormat

class RepositoriesAdapter(
    private val onRepositoryClick: (GithubRepositoryModel) -> Unit
) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private var repositoriesList: MutableList<GithubRepositoryModel> = mutableListOf()

    fun updateItems(repositories: List<GithubRepositoryModel>) {
        repositoriesList = repositories.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            GithubRepositoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onRepositoryClick
        )
    }

    override fun getItemCount(): Int = repositoriesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(repositoriesList[position])
    }

    inner class ViewHolder(
        private val githubRepositoryItemBinding: GithubRepositoryItemBinding,
        private val onRepositoryClick: (GithubRepositoryModel) -> Unit
    ) :
        RecyclerView.ViewHolder(githubRepositoryItemBinding.root) {

        fun load(repository: GithubRepositoryModel) = with(itemView) {
            setOnClickListener { onRepositoryClick(repositoriesList[adapterPosition]) }

            with(githubRepositoryItemBinding) {
                with(repository) {
                    name?.let {
                        txvRepositoryName.text = it
                    } ?: run {
                        txvRepositoryName.text = NO_NAME
                    }

                    language?.let {
                        txvRepositoryLanguage.text =
                            context.getString(R.string.github_repository_language, it)
                    } ?: run {
                        txvRepositoryLanguage.visibility = View.GONE
                    }

                    license?.name?.let {
                        txvRepositoryLicenseName.text =
                            context.getString(R.string.github_repository_license, it)
                    } ?: run {
                        txvRepositoryLicenseName.visibility = View.GONE
                    }

                    stargazersCount?.let {
                        txvRepositoryStargazers.text = it.kFormat()
                    } ?: run {
                        txvRepositoryStargazers.visibility = View.GONE
                    }
                }
            }
        }
    }

    companion object {
        private const val NO_NAME = "-"
    }
}