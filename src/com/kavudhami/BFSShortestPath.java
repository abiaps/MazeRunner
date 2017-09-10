package com.kavudhami;

import java.util.*;

/**
 * Created by Kavudhami on 9/2/2017.
 */

public class BFSShortestPath {

    private ArrayList<Cell> visitedCells;
    private Queue<Cell> unVisitedCells;
    private Map<Cell, Cell> predecessors;
    private List<Cell> path;

    private HashMap<Cell,ArrayList<String>> featureCodeOfCells;
    HashMap<Cell,ArrayList<Cell>> neighborsList;
    HashMap<Integer,String> featureCode;
    ArrayList<Cell> listOfCells;
    int life;

    static int[] code = {1,2,4,8,16,32,64};

    Cell startCell, endCell, neighbor;

    public BFSShortestPath(){
        featureCodeOfCells = new HashMap<Cell,ArrayList<String>>();
        neighborsList = new HashMap<Cell,ArrayList<Cell>>();
        featureCode = new HashMap<Integer,String>();
        listOfCells = new ArrayList<Cell>();
        startCell = new Cell(-1,-1,-1);
        endCell = new Cell(-1, -1, -1);
        neighbor = new Cell(-1, -1, -1);
    }


    // constructs the maze with the given size into a matrix of cells
    public Cell[][] constructMaze(int w, int h, String[] str, Cell[][] maze){
        int i=0,j=0,ind=0;
        life = 3;
        while(i<w && j<h && ind < str.length){
            Cell temp = new Cell(i,j,Integer.valueOf(str[ind]));
            maze[i][j] = temp;
            if(!listOfCells.contains(maze[i][j])) {
                listOfCells.add(maze[i][j]);
                neighborsList.put(maze[i][j], new ArrayList<Cell>());
            }
            //    System.out.print(maze[i][j].getValue() + "\t");
            j++;
            ind++;
            if(j==h){
                i++;
                j=0;
                //        System.out.println();
            }
        }
        return maze;
    }

    public Cell getStartCell(){
        return startCell;
    }

    public Cell getEndCell(){
        return endCell;
    }


    public void featureCodes(){
        featureCode.put(1,"UP");
        featureCode.put(2,"RIGHT");
        featureCode.put(4,"DOWN");
        featureCode.put(8,"LEFT");
        featureCode.put(16,"START");
        featureCode.put(32,"END");
        featureCode.put(64,"MINE");
    }


    public void constructFeatureCodes(Cell[][] maze, int w, int h){
        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                performAnd(maze[i][j]);
            }
        }
        //   printFeatureCodeCells();
    }

    private void printFeatureCodeCells(){
        for(Map.Entry<Cell,ArrayList<String>> entry : featureCodeOfCells.entrySet()){
            System.out.println("cell " + entry.getKey().getValue() + "-" + entry.getKey().getWidth() + "," + entry.getKey().getHeight() + " features " + entry.getValue());
        }
    }

    // adds the feature codes of all cells by performing AND operation
    private void performAnd(Cell cell){
        ArrayList<String> arr;
        StringBuilder sb = new StringBuilder();
        if(featureCodeOfCells.containsKey(cell))
            return;
        for(int i=0; i<code.length; i++){

            if ((code[i]& cell.getValue()) != 0) {
                if(code[i] == 16)
                    startCell = cell;

                if(code[i] == 32)
                    endCell = cell;

                if(!featureCodeOfCells.containsKey(cell)){
                    arr = new ArrayList<String>();
                    arr.add(featureCode.get(code[i]));
                    featureCodeOfCells.put(cell,arr);
                }

                else{
                    arr = featureCodeOfCells.get(cell);
                    arr.add(featureCode.get(code[i]));
                    featureCodeOfCells.put(cell,arr);
                }

            }
        }
    }

    // implemented bfs algorithm for finding shortest paths in maze
    public List<Cell> BFS(Cell source) {
        visitedCells = new ArrayList<Cell>();
        unVisitedCells = new LinkedList<Cell>();
        predecessors = new HashMap<Cell, Cell>();
        path = new ArrayList<Cell>();
        unVisitedCells.add(source);

        while (unVisitedCells.peek() != null) {
            Cell currentCell = unVisitedCells.remove();
            //  System.out.println(currentCell.getValue());
            if((currentCell.getValue()&64) > 0 )
                currentCell.die();
            if(currentCell.getLife() > 0){
                if((currentCell.getValue()&32) > 0) {
                    path.add(currentCell);

                    visitedCells.add(currentCell);
                    while (currentCell != null) {
                        path.add(currentCell);
                        currentCell = predecessors.get(currentCell);
                    }
                    return path;

                }
                else if(!visitedCells.contains(currentCell)){
                    visitedCells.add(currentCell);
                    ArrayList<Cell> neighbors = getNeighbors(currentCell);
                    // System.out.println(currentCell.getValue());
                    for(Cell target : neighbors){
                        if(!visitedCells.contains(target)){
                            // target.setPredecessor(currentCell);
                            predecessors.put(target,currentCell);
                            unVisitedCells.add(target);
                        }
                    }
                }
            }
        }
        return path;
    }

    // gets the neighborlist of the cell
    private ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        for(Map.Entry<Cell,ArrayList<Cell>> cellMap : neighborsList.entrySet()){
            Cell temp = cellMap.getKey();
            if(temp.equals(cell)){
                for(Cell nbr : cellMap.getValue())
                    neighbors.add(nbr);
                break;
            }
        }
        return neighbors;
    }

    // constructs the neighborlist of entire maze
    public void getAllNeighbors(Cell[][] maze){
        for(Cell c : listOfCells){
            ArrayList<String> directions = featureCodeOfCells.get(c);
            ArrayList<Cell> n;
            int neighborHeight = 0, neighborWidth = 0, neighborValue = 0;
            for(String s : directions){
                if(s.equals("START") || s.equals("END") || s.equals("MINE"))
                    continue;
                else if(s.equals("UP")){
                    neighborHeight = c.getHeight();
                    neighborWidth = c.getWidth()-1;
                    neighborValue = maze[neighborWidth][neighborHeight].getValue();
                    neighbor = new Cell(neighborWidth,neighborHeight,neighborValue);
                }
                else if(s.equals("DOWN")){
                    neighborHeight = c.getHeight();
                    neighborWidth = c.getWidth()+1;
                    neighborValue = maze[neighborWidth][neighborHeight].getValue();
                    neighbor = new Cell(neighborWidth,neighborHeight,neighborValue);
                }
                else if(s.equals("LEFT")){
                    neighborHeight = c.getHeight()-1;
                    neighborWidth = c.getWidth();
                    neighborValue = maze[neighborWidth][neighborHeight].getValue();
                    neighbor = new Cell(neighborWidth,neighborHeight,neighborValue);
                }
                else if(s.equals("RIGHT")){
                    neighborHeight = c.getHeight()+1;
                    neighborWidth = c.getWidth();
                    neighborValue = maze[neighborWidth][neighborHeight].getValue();
                    neighbor = new Cell(neighborWidth,neighborHeight,neighborValue);
                }
                n = neighborsList.get(c);
                n.add(neighbor);
                neighborsList.put(c,n);
            }
        }
        //   printNeighbor();
    }

    // to print the neighborlist
    private void printNeighbor(){
        System.out.println("--Neighbors--");
        for(Map.Entry<Cell,ArrayList<Cell>> m : neighborsList.entrySet()) {
            System.out.println("----" + m.getKey().getValue() + " - " +  " width = " + m.getKey().getWidth() + " height = " + m.getKey().getHeight());
            ArrayList<Cell> list = new ArrayList<Cell>();
            list = m.getValue();
            for(Cell item : list)
                System.out.println(item.getValue()+" w = " + item.getWidth() + " h = " + item.getHeight());
        }
    }

    private boolean isVisited(Cell cell) {
        return visitedCells.contains(cell);
    }

    //returns the path from the start cell to the end cell
    public List<String> getPath(List<Cell> path) {
        List<String> result = new ArrayList<String>();
        for(int i=path.size()-1; i>0; i--){
            if(isUp(path.get(i),path.get(i-1)))
                result.add("'UP'");
            else if(isDown(path.get(i),path.get(i-1)))
                result.add("'DOWN'");
            else if(isRight(path.get(i),path.get(i-1)))
                result.add("'RIGHT'");
            else if(isLeft(path.get(i),path.get(i-1)))
                result.add("'LEFT'");

        }
        return result;

    }

    private boolean isUp(Cell src, Cell dest){
        if(src.getWidth() > dest.getWidth() && src.getHeight() == dest.getHeight())
            return true;
        return false;
    }

    private boolean isDown(Cell src, Cell dest){
        if(src.getWidth() < dest.getWidth() && src.getHeight() == dest.getHeight())
            return true;
        return false;
    }

    private boolean isRight(Cell src, Cell dest){
        if(src.getHeight() < dest.getHeight() && src.getWidth() == dest.getWidth())
            return true;
        return false;
    }

    private boolean isLeft(Cell src, Cell dest){
        if(src.getHeight() > dest.getHeight() && src.getWidth() == dest.getWidth())
            return true;
        return false;
    }

}
