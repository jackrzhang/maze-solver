/* 
 * MazeSolver.java
 *
 * @author jackrzhang
 * @version January 27, 2015
 *
 * Automatically solves text mazes ('#' = wall, '.' = path) using Right-hand
 * Wall Traversal.
*/

import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;


public class MazeSolver 
{/*
    private static char[][] maze = 
    {
        { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
        { '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
        { '.', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#' },
        { '#', '#', '#', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
        { '#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '.', '#' },
        { '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#' },
        { '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
        { '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
        { '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
        { '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '.' },
        { '#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#' },
        { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
    };
    */
    
    private static char[][] maze = 
        {
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
            { '.', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#' },
            { '#', '#', '#', '.', '#', '.', '.', '.', '.', '#', '.', '#' },
            { '#', '.', '.', '.', '.', '.', '#', '#', '.', '#', '.', '.' },
            { '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#' },
            { '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
            { '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
            { '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
            { '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#' },
            { '#', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
        };
    
    // Used to store and change the direction the algorithm faces
    private static String[] direction = {"North", "East", "South", "West" };
    private static int directionIndex;
    
    private static String forward; // String representation of the current direction
    
    private static int iterations; // Used to count iterations of traverseTheMaze
    
    private static long startTime; // Used to calculate algorithm time
    private static long endTime;
    
    public static void main(String[] args)
    {
        directionIndex = 1; // Start out by facing east.
        forward = direction[directionIndex]; 
        Point startingPoint = new Point( 0, 2 ); // starting point of the maze
        iterations = -1; // First iteration will come in as iteration 0.
        
        startTime = System.currentTimeMillis(); // Start timer
        traverseTheMaze( startingPoint ); 
    }
    
    // Precondition: P is a viable point(not a wall) in the maze.
    // Postcondition: The maze will be solved to the exit.
    /**
     * Recursively traverse the maze and locate the exit using the right-hand 
     * on the wall algorithm
     * @param Point p, the current point within the maze
     */
    private static void traverseTheMaze( Point p )
    {
        maze[p.y][p.x] = 'X'; // Capital 'X' marks the current spot
        
        iterations++; // Adds a count for each addition function
        endTime = System.currentTimeMillis(); 
        //Takes the "end" time at the start of each iteration
        
        try {
            Thread.sleep(150); // delay for 150 milliseconds
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 50; ++i) System.out.println(); // Clear Console
        
        
        if ( isExit(p) ) { // base case
            maze[p.y][p.x] = '$'; // '$' when the exit is located
            System.out.println("RECURSIVE MAZE SOLVER - J. Zhang\n");
            printMaze();
            
            System.out.println("\nCurrently facing: " + forward );
            System.out.println("Iterations: " + iterations);
            
            // Prints out the total time for the algorithm (does not include delay time)
            System.out.println("Total Algorithm Time: " + 
                                ((endTime - startTime)-(iterations*150)) + " milliseconds");
        }
        else {
            System.out.println("RECURSIVE MAZE SOLVER - J. Zhang\n");
            printMaze();
            
            System.out.println("\nCurrently facing: " + forward );
            System.out.println("Iterations: " + iterations);
            
            // Prints out the time for the algorithm (does not include delay time)
            System.out.println("Algorithm Time: " + 
                                ((endTime - startTime)-(iterations*150)) + " milliseconds");
            
            if ( lookRight(p) != '#' ) {
                turnRight();
                moveForward(p);
                traverseTheMaze(p);
            }
            else if ( lookForward(p) != '#' ) {
                moveForward(p);
                traverseTheMaze(p);
            }
            else if ( lookLeft(p) != '#' ) {
                turnLeft();
                moveForward(p);
                traverseTheMaze(p); 
            }
            else { // Dead End
                turnRight();
                turnRight();
                moveForward(p);
                traverseTheMaze(p);
            }
                
        }
    }
    
    // No precondition; postcondition: directionIndex and forward reflect a right turn.
    /**
     * Changes forward direction by turning to the right.
     */
    private static void turnRight()
    {
        directionIndex = (directionIndex + 1) % 4;
        forward = direction[directionIndex];
    }
    
    // No precondition; postcondition: directionIndex and forward reflect a left turn.
    /**
     * Changes forward direction by turning to the left.
     */
    private static void turnLeft()
    {
        directionIndex = (directionIndex + 3) % 4;
        forward = direction[directionIndex];
    }
    
    // No precondition; 
    // postcondition: returns the character to the right of the current location.
    /**
     * Based on the forward direction and current point, the method determines 
     * what is immediately ahead.
     * @param current point in the maze
     * @return character that is currently to the right
     */
    private static char lookRight( Point p )
    {
        if ( forward.equals("North") )          
            return maze[p.y][p.x+1];
        else if ( forward.equals("East") )
            return maze[p.y+1][p.x];
        else if ( forward.equals("South") )
            return maze[p.y][p.x-1];
        else // forward equals "west"
            return maze[p.y-1][p.x];
    }
    
    // No precondition; 
    // postcondition: returns the character in front of the current location.
    /**
     * Based on the forward direction and current point, the method determines 
     * what is immediately ahead.
     * @param current point in the maze
     * @return character that is immediately ahead
     */
    private static char lookForward( Point p )
    {
        if ( forward.equals("North") )          
            return maze[p.y-1][p.x];
        else if ( forward.equals("East") )
            return maze[p.y][p.x+1];
        else if ( forward.equals("South") )
            return maze[p.y+1][p.x];
        else // forward equals "west"
            return maze[p.y][p.x-1];
    }
    
    // No precondition; 
    // postcondition: returns the character to the left of the current location.
    /**
     * Based on the forward direction and current point, the method determines 
     * what is immediately to the left.
     * @param current point in the maze
     * @return character that is immediately to the left
     */
    private static char lookLeft( Point p )
    {
        if ( forward.equals("North") )          
            return maze[p.y][p.x-1];
        else if ( forward.equals("East") )
            return maze[p.y-1][p.x];
        else if ( forward.equals("South") )
            return maze[p.y][p.x+1];
        else // forward equals "west"
            return maze[p.y+1][p.x];
    }
    
    // No precondition; 
    // Postcondition: Point p will be moved forward one position in the maze
    /**
     * Based on forward direction, this method changes the x or y values
     * of Point p in order to move to the point directly ahead, and marks the
     * previous point.
     * @param current point in the maze
     */
    private static void moveForward( Point p )
    {
        maze[p.y][p.x] = 'x'; // Marks the current point before moving
        
        if ( forward.equals("North") )          
            p.y--;
        else if ( forward.equals("East") )
            p.x++;
        else if ( forward.equals("South") )
            p.y++;
        else // forward equals "west"
            p.x--;
    }
    
    // No precondition;
    // Postcondition: Whether point p is an exit or not will be determined
    /**
     * Based on the forward direction, the method determines whether or not the 
     * current point on the maze is an exit.
     * @param current point
     */
    private static boolean isExit( Point p )
    {
        // Combines direction and location on border of the maze to determine an exit
        if ( forward.equals("North") && p.y == 0 )          
            return true;
        else if ( forward.equals("East") && p.x == 11 )
            return true;
        else if ( forward.equals("South") && p.y == 11 )
            return true;
        else if ( forward.equals("West") && p.x == 0 )
            return true;
        else
            return false;
    }
    
    // No precondition;
    // Postcondition: the maze in its current state will be printed to console
    /**
     * Print the maze (instantiated as a 2D character array) to console
     */
    private static void printMaze ()
    {
        for(int y = 0; y < 12; y++)
        {
            for(int x = 0; x < 12; x++)
            {
                System.out.print(" " + maze[y][x]);
            }
            System.out.println();
        }
    }

}