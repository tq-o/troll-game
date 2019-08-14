import java.util.Random;

/* The game is played in a series of rounds. The game board is a 2D array with
 * a set number of rows and columns. The array contains Objects of type GamePiece.
 * There are four types of GamePieces: Player- the user, Troll- the villain, Treasure- the 
 * goal, and EmptyPiece- represents cells that are not one of the previous types.
 * The player's goal is to reach the treasure without encountering the Troll.
 * The Troll's goal is to intercept the player before the player reaches the treasure.
 * The player starts in the upper left-hand corner- position 0,0.
 * The treasure is located at the lower right-hand corner- position 7, 9.
 * The Troll starts at a random position in the board that is not
 * the player's starting position or the treasure's position.
 * The player makes a move one step in one of four directions: up, down, left, right.
 * If the player is on a border and the requested move would take the player off the 
 * board, the move is ignored- i.e. the player does not move.
 * The Troll knows the player's position and after the player moves, the Troll will
 * move towards the player. If the Troll's next move reaches the player, the Troll
 * wins. If the player reaches the treasure and the Troll also reaches the treasure 
 * in the same round, the player wins.
 * The player starts with 160 life points. Each move the player makes costs 10 life points. 
 * This deduction occurs for all player moves- even if the player attempts to move off the board.
 * If a player's life level drops to 0 or less, the player is not alive an cannot move.
*/

public class TrollGame {
   //constants for this version of the game
   private static final int INIT_PLAYER_POINTS = 160;
   private static final int PLAYER_POINTS_DEC = -10;
   private static final int TREASURE_POINTS = 500;
   private static final int ROWS = 8;
   private static final int COLS = 10;
   // random number generator
   private Random rand;
   // the game board, a 2D array of GamePieces
   private GamePiece[][] gameBoard;
   // variables to keep track of the locations of player, troll
   private int curPlayerRow, curPlayerCol;
   private int curTrollRow, curTrollCol;
   // the player's status
   private boolean playerWins;
   private boolean playerLoses;
   
   Player playerData = new Player(INIT_PLAYER_POINTS);
   Treasure treasureScore = new Treasure(TREASURE_POINTS);
   EmptyPiece emptyPieces = new EmptyPiece();
   Troll trollData = new Troll();
   /* Constructor that uses an unseeded instance of the random number generator.
    * Calls initBoard.
    */
   public TrollGame () {
     //TODO- implement this method.
     rand = new Random();
     gameBoard = initBoard(ROWS, COLS, rand);
     
   }

   /* Constructor that uses a seeded instance of the random number generator.
    * Calls initBoard.
    */   
   public TrollGame (int seed){
      //TODO- implement this method.
      rand = new Random(seed);
      gameBoard = initBoard(ROWS, COLS, rand);
   }
   
   /*
    * Manages a player move. This is a multi-step process that involves:
    * 1- if the player is alive:
    *    - determine the direction of the user's input.
    *    - check that the user move is valid. If not, player does not change position,
    *      if valid, adjust player position accordingly.
    *    - adjust player life level- regardless of validity of move.
    * 2- calculate troll's move given the player's new position.
    * 3- check if troll has same position as player.if so, player loses. Adjust the GamePieces
    *    so that the board is displayed properly, i.e. the player is gone and is replaced by the troll.
    * 4- check if the player reached the treasure. If so, the player wins. Adjust the GamePieces
    *    so that the board is displayed properly, i.e. the player now appears in the treasure position. 
    *    Also, update the troll's last position.
    * 5- If neither 3 nor 4 above apply, then update the player and trollpositions.
    *    Adjust the GamePieces so that the board is displayed properly, i.e. the player and troll appear 
    *    in their new positions.
    */
   public void movePlayer(String direction) {
      int oldPlayerCol = curPlayerCol;
      int oldPlayerRow = curPlayerRow;
      
      if (playerAlive(curPlayerRow, curPlayerCol)) {
      
      if (direction.equals("u") && curPlayerRow != 0) curPlayerRow -=1;
      else if (direction.equals("d") && curPlayerRow != 7) curPlayerRow +=1;
      else if (direction.equals("l") && curPlayerCol != 0) curPlayerCol -=1;
      else if (direction.equals("r") && curPlayerCol != 9) curPlayerCol +=1;
           
      swapPosition(oldPlayerRow, oldPlayerCol, curPlayerRow, curPlayerCol);
      int[] troll = calcNewTrollCoordinates(curPlayerRow, curPlayerCol, curTrollRow, curTrollCol);
      if (troll[0] == curPlayerRow && troll[1] == curPlayerCol){
         gameBoard[troll[0]][troll[1]] = trollData;
         gameBoard[curTrollRow][curTrollCol] = emptyPieces;
         playerLoses = true;
      }   
      
      else {
      swapPosition(curTrollRow, curTrollCol, troll[0], troll[1]);
      curTrollRow = troll[0];
      curTrollCol = troll[1];
      }
      
      if (playerLoses == true) gameBoard[curTrollRow][curTrollCol] = trollData;
      
      
      if (playerFoundTreasure(curPlayerRow,curPlayerCol)) {
      gameBoard[ROWS -1][COLS -1] = playerData;
      playerWins();
      }
      }   
      
      else playerLoses = true;
      playerLoses();
      adjustPlayerLifeLevel(curPlayerRow, curPlayerCol);
   }
   
   /* Returns true if the player wins, false otherwise. */
   public boolean playerWins(){
   //TODO- implement this method.
      
         
      return playerWins;
   }
   
   /* Returns true if the player loses, false otherwise. */
   public boolean playerLoses(){
   //TODO- implement this method.
   
      return playerLoses;
   }
   
   /* Returns the number of treasure points. */
   public int getTreasurePoints(){
   //TODO- implement this method.
      return TREASURE_POINTS;
   }
   
   /* Resets the game variables and game board (call initBoard).
    * Does NOT change the random number generator instance.
   */
   public void resetGame(){
      //TODO- implement this method.
      playerWins = false; playerLoses = false;
      curPlayerRow = 0; curPlayerCol = 0;
      playerData = new Player(INIT_PLAYER_POINTS);
      
      getTreasurePoints();
      getRandTrollRow(rand, ROWS); getRandTrollCol(rand, COLS);
      gameBoard = initBoard(ROWS, COLS, rand);
   }
   
   /* Returns a String version of the game. */
   public String getGameStr() {
      StringBuilder outStr = new StringBuilder();
      for(int i=0;i<ROWS;i++) {
         for(int j=0;j<COLS;j++) 
            outStr.append("|").append(gameBoard[i][j].show());
         outStr.append("|").append(System.getProperty("line.separator"));
      }
      return outStr.toString();
   }
   
   /** private methods below **/
      
   /* Creates the game board array with rows and cols. The player starts in the upper left-hand corner. 
    * The treasure is at the lower right-hand corner. The rest of the cells are empty pieces.
    * The player starts with INIT_PLAYER_POINTS life points. The treasure is initialized to
    * TREASURE_POINTS. The Troll position is determined at random by calling the getRandTrollRow
    * and getRandTrollCol methods. This method returns the initialized array.
   */
   private GamePiece[][] initBoard(int rows, int cols, Random rand) {
   //TODO- implement this method.   
      gameBoard = new GamePiece[rows][cols];
      
      boolean correctPlace = false;
      while (correctPlace == false) {
         curTrollRow = getRandTrollRow(rand, ROWS);
         curTrollCol = getRandTrollCol(rand, COLS);
         if ((curTrollRow !=0 && curTrollCol !=0) || (curTrollRow !=7 && curTrollCol !=9))
            correctPlace = true;
         else correctPlace = false;
      }

      for (int i = 0; i < rows; i++){
         for (int j = 0; j < cols; j++) {
      if (i == 0 && j == 0)
         gameBoard [i][j] = playerData; 
      else if (i==7 && j==9)
         gameBoard [i][j] = treasureScore;
      else if (i==curTrollRow && j==curTrollCol)
         gameBoard [i][j] = trollData;
      else
         gameBoard [i][j] = emptyPieces;
         }
      }
      return gameBoard;
   }
   
   /* Returns true if the player is alive, false otherwise.*/
   private boolean playerAlive(int curPlayerRow, int curPlayerCol){
   //TODO- implement this method.
     
      return gameBoard[curPlayerRow][curPlayerCol].isAlive();
     
   }
   
   /* Adjusts the player's life level by the amount PLAYER_POINTS_DEC. */
   private void adjustPlayerLifeLevel(int curPlayerRow, int curPlayerCol){
      //TODO- implement this method.
      gameBoard[curPlayerRow][curPlayerCol].updateLifePoints(PLAYER_POINTS_DEC);
      
   }
   
   /* Returns true if the player row and column passed in equals
      the treasure row and column. */
   private boolean playerFoundTreasure(int playerRow, int playerCol){
   //TODO- implement this method.
   if (playerRow == ROWS -1  && playerCol == COLS-1) {
      //overwritePosition(int oldRow, int oldCol, int newRow, int newCol) 
      playerWins = true;
      return true;
      }
   else return false;
   }
   
   /* Returns a random number in [1,numRows-1] */
   private int getRandTrollRow(Random rand, int numRows){
   //TODO- implement this method.
      int trollrow = rand.nextInt(numRows-1)+1;   
      return trollrow;
      
   }
   
   /* Returns a random number in [1,numCols-1] */
   private int getRandTrollCol(Random rand, int numCols){
   //TODO- implement this method.
   
      int trollcol = rand.nextInt(numCols-1)+1;
      return trollcol;
   }
   
   /* 
    * This method calculates the direction the troll will move to get as close to the player as possible.
    * The player and current troll positions are passed in. The method chooses a move in one direction
    * that will bring it closer to the player. The method returns an int array [row, col], where row is the 
    * index of the verical position and col is the index of the horizontal position in the game board.
    * The strategy of calculating the new troll position is to minimize the distance between the troll and player.
    * This can be done by using the Manhattan, or city block, distance between the two pieces. 
    * Determine the horizontal distance and then the vertical distance. The troll will want to move to 
    * decrease the greater of the two distances. Note that the direction is also important. The sign of
    * the difference can indicate the direction to move in. Asume the player is never out of bounds.
    * Note: the player should loose if they move onto the troll's position. The troll should detect this
    * situation and not make a move.
   */ 
   private int[] calcNewTrollCoordinates(int playerRow, int playerCol, int trollRow, int trollCol){
   //TODO- implement this method.
      int Rowdistance = playerRow - trollRow;
      int Coldistance = playerCol - trollCol;
      //int a = trollRow;
      //int b= trollCol;
      if (Math.abs(Rowdistance) > Math.abs(Coldistance)) {
         //if (trollRow != 0 || trollRow !=9) {
            if (Rowdistance < 0) {trollRow -= 1; /*curTrollRow -=1;*/}
            else if (Rowdistance > 0){trollRow += 1; /*curTrollRow +=1;*/}
         //}
     
      }
      else if (Rowdistance == 0 && Coldistance == 0) {
      playerLoses = true;
      //overwritePosition(int oldRow, int oldCol, int newRow, int newCol);
      //trollRow = curPlayerRow;
      //trollCol = curPlayerCol;
      
      }
      else {
         //if (trollCol != 0 || trollRow !=7) {
            if (Coldistance < 0) {trollCol -= 1;/*curTrollCol -=1;*/}
            else if (Coldistance > 0){trollCol += 1; /*curTrollCol +=1;*/}
         //}
      }
      int [] newTroll = {trollRow, trollCol};
      return newTroll;
   }
   
 // The following three methods may be helpful when adjusting the GamePieces in the movePlayer method. */
   
   /* Overwrite the GamePiece at the coordinates passed in with an empty GamePiece. */
   private void overwritePositionWithEmpty(int row, int col){
      //TODO- implement this method.
      gameBoard[row][col] = emptyPieces;
      
   }
   
   /* Overwrite the GamePiece at the new coordinates with the GamePiece at the 
      old coordinates. Place a new EmptyPiece at the old coordinates. */
   private void overwritePosition(int oldRow, int oldCol, int newRow, int newCol){
      //TODO- implement this method.
      gameBoard[newRow][newCol] = gameBoard[oldRow][oldCol];
      gameBoard[oldRow][oldCol] = emptyPieces;
   }
      
   /* Swap the position of the GamePiece at the current position with the 
    * GamePiece at the new position. */
   private void swapPosition(int curRow, int curCol, int newRow, int newCol){
      //TODO- implement this method.  
      
      GamePiece gameBoardPiece = gameBoard[curRow][curCol];
      gameBoard[curRow][curCol] = gameBoard[newRow][newCol];
      gameBoard[newRow][newCol] = gameBoardPiece;
   }

}