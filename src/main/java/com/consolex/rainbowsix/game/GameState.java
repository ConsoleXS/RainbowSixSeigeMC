package com.consolex.rainbowsix.game;

public enum GameState {
    LOBBY, //When there is no game active.
    LOBBY_STARTING, //Timer for the players to get sent to the map to start the actual game.
    GAME_STARTING, //Players will be able to choose different kits (This state happens when players are first sent into the game)
    ROUND_STARTING, //Happens when kit choosing time ends, starting a new countdown. Players can buy guns with coins.
    ROUND_ACTIVE, //Round Starts, the bomb will explode in 3 minutes.
    ROUND_ENDED_DEFENDERS, //If the bomb was mined, the attackers win, otherwise, the defenders win. Reset cycle, First to win 5 rounds wins the game!
    // Also give players money per kill and different money for winning and losing.
    ROUND_ENDED_ATTACKERS,
}
