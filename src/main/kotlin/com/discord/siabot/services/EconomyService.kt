package com.discord.siabot.services

import java.util.concurrent.ConcurrentHashMap

enum class PurchaseResult {
    SUCCESS,
    NOT_ENOUGH_COINS,
    ITEM_NOT_FOUND
}

data class ShopItem(val name: String, val description: String, val price: Int)

object EconomyService {
    private val userCoins = ConcurrentHashMap<String, Int>()
    private val dailyCooldown = ConcurrentHashMap<String, Long>()

    private val shopItems = mapOf(
        1 to ShopItem("🎭 역할 색상 변경권", "특별한 역할 색상으로 자신을 뽐내세요.", 500),
        2 to ShopItem("🌟 프리미엄 배지", "이름 옆에 표시되는 특별한 배지입니다.", 1000)
    )

    fun getBalance(userId: String): Int = userCoins.getOrDefault(userId, 0)

    fun getShopItems(): Map<Int, ShopItem> = shopItems

    fun purchaseItem(userId: String, itemId: Int): PurchaseResult {
        val item = shopItems[itemId] ?: return PurchaseResult.ITEM_NOT_FOUND
        val balance = getBalance(userId)

        if (balance < item.price) {
            return PurchaseResult.NOT_ENOUGH_COINS
        }

        userCoins[userId] = balance - item.price
        return PurchaseResult.SUCCESS
    }

    fun canClaimDaily(userId: String): Boolean {
        val lastDaily = dailyCooldown.getOrDefault(userId, 0)
        val cooldownTime = 24 * 60 * 60 * 1000 // 24시간
        return System.currentTimeMillis() - lastDaily >= cooldownTime
    }

    fun claimDaily(userId: String, reward: Int): Int {
        val currentBalance = getBalance(userId)
        userCoins[userId] = currentBalance + reward
        dailyCooldown[userId] = System.currentTimeMillis()
        return userCoins[userId]!!
    }

    fun getDailyCooldown(userId: String): Long {
        val lastDaily = dailyCooldown.getOrDefault(userId, 0)
        val cooldownTime = 24 * 60 * 60 * 1000
        return (lastDaily + cooldownTime)
    }

    fun transferCoins(fromUserId: String, toUserId: String, amount: Int): Boolean {
        val fromBalance = getBalance(fromUserId)
        if (fromBalance < amount) return false

        val toBalance = getBalance(toUserId)
        userCoins[fromUserId] = fromBalance - amount
        userCoins[toUserId] = toBalance + amount
        return true
    }

    fun getCoinLeaderboard(limit: Int): List<Pair<String, Int>> {
        return userCoins.toList().sortedByDescending { it.second }.take(limit)
    }
}
