package cat.jorcollmar.githubportal.ui.githubrepositories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.databinding.ActivityGithubPortalBinding

class GithubPortalActivity : AppCompatActivity() {
    lateinit var binding: ActivityGithubPortalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Replace Splash theme with a NoActionBar theme
        setTheme(R.style.Theme_GithubPortal_NoActionBar)

        super.onCreate(savedInstanceState)
        binding = ActivityGithubPortalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}