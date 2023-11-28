package xyz.rh.enjoyfragment.viewpager2.dota

import HeroGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.rh.common.xlog
import java.lang.Exception

/**
 * Created by rayxiong on 2022/10/30.
 */
class RepoApi {

    companion object {
        const val TAG = "RepoApi"
    }

    /**
     * 从网络获取
     */
    suspend fun getHeroFromDota2Api() : ArrayList<DotaHero>? {
        xlog(TAG, "getHeroFromDota2Api()")
        val heroGenerator = HeroGenerator()
        var heroList : ArrayList<DotaHero>? = null
        withContext(Dispatchers.IO) {
            try {
//                    heroList = NetworkUtils.instance.suspendGet<Hero>("https://api.opendota.com/api/heroes")
                heroList = HttpWorkUtils.instance.suspendGet<DotaHero, ArrayList<DotaHero>>("https://api.opendota.com/api/heroes")

                xlog(TAG, "herolist = $heroList")

                heroList?.let { list ->
                    for (hero in list) {
                        val sampleName = hero.name?.substring(14)
                        xlog(TAG, "sampleName = $sampleName")
                        sampleName?.let {
                            val heroImg = heroGenerator.heroImgPool.find { it.contains(sampleName) }
                            hero.imgUrl = heroImg!!
                        }
                    }
                }
            } catch (e: Exception) {
                xlog(TAG,"network error:: e=${e.message}")
            }
        }
        return heroList
    }


    /**
     * 本地mock生成
     */
    fun getHeroFromLocalMock() : ArrayList<DotaHero>? {
        val heroGen = HeroGenerator()
        return heroGen.createHeroList()
    }

}