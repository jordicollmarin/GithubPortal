package cat.jorcollmar.githubportal.ui.githubrepositories.view.tester

import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.GenericTester

class GithubRepositoryDetailTester : GenericTester() {
    internal fun areRepositoryDetailsAvailable() {
        isVisible(R.id.txvRepositoryLink)
        isVisible(R.id.txvRepositoryStargazers)
        isVisible(R.id.txvRepositoryDescription)
        isVisible(R.id.lytRepositoryInfoDetails)
    }
}