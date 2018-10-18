package id.kampung.footballmatch.view.team.tabDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.model.PlayerModel
import kotlinx.android.synthetic.main.player_activity.*

class DetailPlayer : AppCompatActivity() {

    private lateinit var playerModel: PlayerModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        playerModel = intent.getParcelableExtra("data")

        Glide.with(this).load(playerModel.strThumb).into(playerIvPoster)
        playerTvHeight.text = playerModel.strHeight
        playerTvWeight.text = playerModel.strWeight
        playerTvDescription.text = playerModel.strDescriptionEN
        playerTvPosition.text = playerModel.strPosition

        supportActionBar?.title = playerModel.strPlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
