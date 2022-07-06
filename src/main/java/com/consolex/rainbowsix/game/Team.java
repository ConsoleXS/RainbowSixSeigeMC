package com.consolex.rainbowsix.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private static List<String> attackers = new ArrayList<String>();
    private static List<String> defenders = new ArrayList<String>();

    public static void addToTeam(TeamType type, Player player)
    {
        if (isInTeam(player))
        {
            attackers.remove(player.getName());
            defenders.remove(player.getName());
        }

        switch (type)
        {
            case DEFENDERS:
                defenders.add(player.getName());
                break;
            case ATTACKERS:
                attackers.add(player.getName());
                break;
        }
    }

    public static boolean isInTeam(Player player)
    {
        return attackers.contains(player.getName()) || defenders.contains(player.getName());
    }

    public static void clearTeams()
    {
        defenders.clear();
        attackers.clear();
    }

    public static TeamType getTeam(Player player)
    {
        if(!isInTeam(player))
        {
            return null;
        }


        TeamType teamType = null;

        if (attackers.contains(player.getName()))
        {
            teamType = TeamType.ATTACKERS;
        }
        else if (defenders.contains(player.getName()))
        {
            teamType = TeamType.DEFENDERS;
        }



        return teamType;
    }


}
