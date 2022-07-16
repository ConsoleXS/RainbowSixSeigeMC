package com.consolex.rainbowsix.game;

import java.util.HashMap;

public class Count {
    private static HashMap<TeamTypes, Integer> points = new HashMap<>();

    public static void initializeTeamScore()
    {
        points.put(TeamTypes.DEFENDERS, 0);
        points.put(TeamTypes.ATTACKERS, 0);
    }

    public static Integer getPoints(TeamTypes team)
    {
        return points.get(team);
    }

    public static void addPoint(TeamTypes team)
    {
        int current = getPoints(team);
        points.put(team, current + 1);
    }

    public static void removePoint(TeamTypes team)
    {
        int current = getPoints(team);
        points.put(team, current - 1);
    }

    public static void clearPoints()
    {
        points.clear();
    }
}
