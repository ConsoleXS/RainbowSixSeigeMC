package com.consolex.rainbowsix.game;

import java.util.HashMap;

public class Count {
    private static HashMap<TeamType, Integer> points = new HashMap<>();

    public static void initializeTeamScore()
    {
        points.put(TeamType.DEFENDERS, 0);
        points.put(TeamType.ATTACKERS, 0);
    }

    public static Integer getPoints(TeamType team)
    {
        return points.get(team);
    }

    public static void addPoint(TeamType team)
    {
        int current = getPoints(team);
        points.put(team, current + 1);
    }

    public static void removePoint(TeamType team)
    {
        int current = getPoints(team);
        points.put(team, current - 1);
    }

    public static void clearPoints()
    {
        points.clear();
    }
}
