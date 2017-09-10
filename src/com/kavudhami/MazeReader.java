package com.kavudhami;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kavudhami on 9/2/2017.
 */
public class MazeReader {

    private static final String file = "src/resources/mazes.txt";
    BufferedReader bufferedReader = null;
    FileReader fileReader = null;

    Cell[][] maze;
    List<Cell> path;

    List<String> result;

    public void process(){

        try{

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String currentLine = bufferedReader.readLine();

            while ( (currentLine != null ) || (!currentLine.equals("") ))  {

                BFSShortestPath bfsShortestPath = new BFSShortestPath();

                bfsShortestPath.featureCodes();

                StringTokenizer st = new StringTokenizer(currentLine,"-");
                String size = st.nextToken();
                String structure = st.nextToken();

                System.out.println(" size " + size);

                String[] str = structure.substring(1,structure.length()-1).split(",");

                int w = Integer.valueOf(size.substring(size.indexOf('(')+1,size.indexOf(',')));

                int h = Integer.valueOf(size.substring(size.indexOf(',')+1,size.indexOf(')')));

                maze = new Cell[w][h];

                maze = bfsShortestPath.constructMaze(w,h,str,maze);

                bfsShortestPath.constructFeatureCodes(maze,w,h);

                bfsShortestPath.getAllNeighbors(maze);

                Cell startCell = bfsShortestPath.getStartCell();
                Cell endCell = bfsShortestPath.getEndCell();

                path = bfsShortestPath.BFS(startCell);

                result = bfsShortestPath.getPath(path);

                if(path.size() == 0)
                    System.out.println("there is no path available for this maze with 3 lives");
                else
                    System.out.println(result);

                System.out.println();

                currentLine = bufferedReader.readLine();

                if(currentLine == null) break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public Cell[][] getMaze() {
        return maze;
    }

    public List<String> getPathOfMaze(){
        return result;
    }

    public static void main(String[] args) {
        MazeReader mazeReader = new MazeReader();
        mazeReader.process();
    }

}
