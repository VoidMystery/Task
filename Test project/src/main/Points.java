package main;

import java.util.Vector;

public class Points {
    private final int MAX = 100;
    private Point[] pointsList;
    private int pointsCount;
    private int[][] matrix;

    public Points() {
        matrix = new int[MAX][MAX];
        for (int i = 0; i < MAX; i++)
            for (int j = 0; j < MAX; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        pointsCount = 0;
        pointsList = new Point[MAX];
    }

    public int addPoint(String label) {
        for (int i = 0; i < pointsCount; i++) {
            if (label.equals(pointsList[i].getLabel())) {
                return i;
            }
        }
        pointsList[pointsCount++] = new Point(label);
        return pointsCount - 1;
    }

    public void addRoad(int begin, int end, int cost, int length) {
        matrix[begin][end] = cost * length;
    }

    public int pointSearch(String label) {
        for (int i = 0; i < pointsCount; i++) {
            if (label.equals(pointsList[i].getLabel())) {
                return i;
            }
        }
        return -1;
    }

    public String labelSearch(int i) {
        return pointsList[i].getLabel();
    }

    public int[] find(int start, Vector<Integer> pars) {
        int[] cost = new int[pointsCount];
        for (int i = 0; i < cost.length; i++)
            cost[i] = Integer.MAX_VALUE;
        boolean[] isVisitedArray = new boolean[pointsCount];
        pars.setSize(pointsCount);
        cost[start] = 0;
        for (int i = 0; i < pointsCount; i++) {
            int cur = -1;
            for (int j = 0; j < pointsCount; j++) {
                if (!isVisitedArray[j] && (cur == -1 || cost[j] < cost[cur])) {
                    cur = j;
                }
            }
            if (cost[cur] == Integer.MAX_VALUE)
                break;
            isVisitedArray[cur] = true;
            for (int j = 0; j < pointsCount; j++) {
                if (matrix[cur][j] == Integer.MAX_VALUE)
                    continue;
                if ((cost[cur] + matrix[cur][j]) < cost[j]) {
                    cost[j] = cost[cur] + matrix[cur][j];
                    pars.set(j, cur);
                }
            }
        }
        return cost;
    }
}