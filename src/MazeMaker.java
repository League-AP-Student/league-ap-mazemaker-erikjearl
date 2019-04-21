import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//select a random cell to start
		Cell randCell = new Cell(randGen.nextInt(w),randGen.nextInt(h));
		//call selectNextPath method with the randomly selected cell
		selectNextPath(randCell);
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
			currentCell.setBeenVisited(true);
		// check for unvisited neighbors
			if(getUnvisitedNeighbors(currentCell).size() > 0) {
		// if has unvisited neighbors,
			// select one at random.
				int randomInt = randGen.nextInt(getUnvisitedNeighbors(currentCell).size());
			// push it to the stack
				uncheckedCells.push(getUnvisitedNeighbors(currentCell).get(randomInt));
			// remove the wall between the two cells
				removeWalls(currentCell,getUnvisitedNeighbors(currentCell).get(randomInt));
			// make the new cell the current cell and mark it as visited
				currentCell = getUnvisitedNeighbors(currentCell).get(randomInt);
				currentCell.setBeenVisited(true);
			//call the selectNextPath method with the current cell
				selectNextPath(currentCell);
		// if all neighbors are visited
			}else {
			//if the stack is not empty
				if(!uncheckedCells.isEmpty()) {
				// pop a cell from the stack
					Cell popedCell = uncheckedCells.pop();
				// make that the current cell
					currentCell = popedCell;
				//call the selectNextPath method with the current cell
					selectNextPath(currentCell);
				}
			}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if(c1.getX() > c2.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		else if (c1.getX() < c2.getX()) {
			c2.setWestWall(false);
			c1.setEastWall(false);
		}
		else if (c1.getY() > c2.getY()) {
			c2.setSouthWall(false);
			c1.setNorthWall(false);
		}
		else if (c1.getY() < c2.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> theseCells = new ArrayList<>();
		if(c.getX() > 0 && !maze.getCell(c.getX()-1,c.getY()).hasBeenVisited()) {
			theseCells.add(maze.getCell(c.getX()-1,c.getY()));
		}
		if(c.getX() < maze.getWidth()-1 && !maze.getCell(c.getX()+1,c.getY()).hasBeenVisited()) {
			theseCells.add(maze.getCell(c.getX()+1,c.getY()));
		}
		if(c.getY() > 0 && !maze.getCell(c.getX(),c.getY()-1).hasBeenVisited()) {
			theseCells.add(maze.getCell(c.getX(),c.getY()-1));
		}
		if(c.getY() < maze.getHeight()-1 && !maze.getCell(c.getX(),c.getY()+1).hasBeenVisited()) {
			theseCells.add(maze.getCell(c.getX(),c.getY()+1));
		}
		
		return theseCells;
	}
}