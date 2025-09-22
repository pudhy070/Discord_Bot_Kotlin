package com.discord.siabot.services

import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

object ExperienceService {
    private val userExp = ConcurrentHashMap<String, Int>()
    private val userLevel = ConcurrentHashMap<String, Int>()

    fun addExperience(userId: String): Boolean {
        val expToAdd = Random.nextInt(10, 25)
        val currentExp = userExp.getOrDefault(userId, 0) + expToAdd
        val currentLevel = getLevel(userId)
        val requiredExp = getRequiredExp(currentLevel)

        userExp[userId] = currentExp

        if (currentExp >= requiredExp) {
            userLevel[userId] = currentLevel + 1
            userExp[userId] = currentExp - requiredExp
            return true // 레벨업!
        }
        return false
    }

    fun getLevel(userId: String): Int = userLevel.getOrDefault(userId, 1)
    fun getExp(userId: String): Int = userExp.getOrDefault(userId, 0)
    fun getRequiredExp(level: Int): Int = level * 100

    fun getLevelLeaderboard(limit: Int): List<Pair<String, Int>> {
        return userLevel.entries.sortedWith(compareByDescending<Map.Entry<String, Int>> { it.value }
            .thenByDescending { userExp.getOrDefault(it.key, 0) })
            .map { it.key to it.value }
            .take(limit)
    }
}
