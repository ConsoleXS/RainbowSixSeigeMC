package com.consolex.rainbowsix.game;

public class GameManager {
    public GameState gameState = GameState.LOBBY;

    public void setGameState(GameState gameState)
    {
        switch (gameState)
        {
            case LOBBY:
                // Teleport to lobby, Clear Inventory, Clear Effects, Clear Teams, Clear Scores, Adventure mode
                break;
            case LOBBY_STARTING:
                //Start timer, Teleport players to random map
                break;
            case GAME_STARTING:
                //Start timer for kit selection, only happens once per game!
                break;
            case ROUND_STARTING:
                //Give all players starting 800 credits!
                //Clear items and Teleport teams to their spawns, players can buy guns with money, not select kits.
                break;
            case ROUND_ACTIVE:
                //Start bomb timer
                break;
            case ROUND_ENDED_ATTACKERS:
                // Attackers get a point! (And money)
                // Give players money per kill, add their kills to their summative kills, and remove their current kill count.
                // End game if 5 rounds are won.
                break;
            case ROUND_ENDED_DEFENDERS:
                // Defenders get a point! (And money)
        }
    }



}
