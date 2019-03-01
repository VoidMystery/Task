package main;

import main.epam.com.api.GpsNavigator;
import main.epam.com.api.Path;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class ExampleApp {

    public static Points points = new Points();

    public static void main(String[] args) {

        final GpsNavigator navigator = new StubGpsNavigator();
        navigator.readData("D:\\Gps\\road_map.ext");
        final Path path = navigator.findPath("A", "E");
        System.out.println(path);
    }

    private static class StubGpsNavigator implements GpsNavigator {

        @Override
        public void readData(String filePath) {
            try{
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String strLine;
                while ((strLine = br.readLine()) != null){
                    String [] strings=strLine.split(" ");
                    points.addRoad(points.addPoint(strings[0]), points.addPoint(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                }
            }catch (IOException e){
                System.out.println("ReadFileException");
            }
        }

        @Override
        public Path findPath(String pointA, String pointB){
            try {
                int codeA = points.pointSearch(pointA);
                int codeB = points.pointSearch(pointB);
                if (codeA == -1 || codeB == -1) {
                    throw new Exception("MissPointException");

                }
                Vector<Integer> pars = new Vector<>();
                int[] cost = points.find(codeA, pars);
                if (codeA >= cost.length || cost[codeB] == Integer.MAX_VALUE) {
                    throw new Exception("NoPathException");
                }
                Vector<Integer> deps = new Vector<>();
                for (int i = codeB; i != codeA; i = pars.get(i))
                    deps.add(i);
                deps.add(codeA);
                Vector<String> path = new Vector<>();
                for (int i = deps.size() - 1; i >= 0; i--)
                    path.add(points.labelSearch(deps.get(i)));
                return new Path(path, cost[codeB]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return new Path(Arrays.asList("Остсутствует путь или точки маршрута"), Integer.MAX_VALUE);
        }
    }
}
