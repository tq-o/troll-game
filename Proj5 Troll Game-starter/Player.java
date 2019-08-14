public class Player extends GamePiece {
   public Player(int score) {
       super(score);
   } 
   public String getType(){
      return "Player";
   } 
   
   public String show(){
	 return "P";
   } 
}
