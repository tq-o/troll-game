public class Treasure extends GamePiece {
   public Treasure(int score) {
       super(score);
   } 
   public String getType(){
      return "Treasure";
   } 
   
   public String show(){
	 return "$";
   } 
}