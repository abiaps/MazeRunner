package test.com.kavudhami;

import com.kavudhami.BFSShortestPath;
import com.kavudhami.Cell;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kavudhami on 9/3/2017.
 */
//@RunWith(Arquillian.class)
public class BFSShortestPathTest {

    static Cell[][] testMaze1, testMaze2;

    static List<String> pathResult1, pathResult2;
    static List<Cell> path1, path2;

    @org.junit.BeforeClass
    public static void setUp() throws Exception {

        BFSShortestPath bfsShortest = new BFSShortestPath();

        testMaze1 = new Cell[3][3];
        bfsShortest.featureCodes();
        testMaze1 = bfsShortest.constructMaze(3,3,new String[]{"34","14","12","6","77","5","1","19","9"},testMaze1);
        bfsShortest.constructFeatureCodes(testMaze1,3,3);
        bfsShortest.getAllNeighbors(testMaze1);

        Cell startCell1 = bfsShortest.getStartCell();
        Cell endCell1 = bfsShortest.getEndCell();

        path1 = bfsShortest.BFS(startCell1);
        pathResult1 = bfsShortest.getPath(path1);

        BFSShortestPath bfsShortest2 = new BFSShortestPath();
        testMaze2 = new Cell[3][3];
        bfsShortest2.featureCodes();

        testMaze2 = bfsShortest2.constructMaze(3,3,new String[]{"34","10","12","6","14","5","1","19","9"},testMaze2);
        bfsShortest2.constructFeatureCodes(testMaze2,3,3);
        bfsShortest2.getAllNeighbors(testMaze2);

        Cell startCell2 = bfsShortest2.getStartCell();
        Cell endCell2 = bfsShortest2.getEndCell();

        path2 = bfsShortest2.BFS(startCell2);
        pathResult2 = bfsShortest2.getPath(path2);
    }


    @org.junit.Test
    public void getPath1() throws Exception {
        List<String> path1 = new ArrayList<String>();
        path1.add("'UP'");
        path1.add("'UP'");
        path1.add("'LEFT'");
        Assert.assertEquals(path1, pathResult1);

    }

    @org.junit.Test
    public void getPath2() throws Exception {
        List<String> path2 = new ArrayList<String>();
        path2.add("'RIGHT'");
        path2.add("'UP'");
        path2.add("'UP'");
        path2.add("'LEFT'");
        path2.add("'LEFT'");
        Assert.assertEquals(path2, pathResult2);
    }

}
