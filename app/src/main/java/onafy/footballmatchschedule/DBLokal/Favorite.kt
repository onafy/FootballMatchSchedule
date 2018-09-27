package onafy.footballmatchschedule.DBLokal

data class Favorite(val id: Long?, val teamId: String?, val teamName: String?, val teamBadge: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_fAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}