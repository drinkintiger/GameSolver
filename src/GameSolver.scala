/** Author:  M. Allen AND YOUR NAMES HERE
  * 
  * Solves the 8-puzzle game.
  */
  

object GameSolver {

  val defaultFile = "inputs/hw04_puzz1.txt"

  def main( args: Array[String] ): Unit =
    if ( !args.isEmpty ) {
      val gm = new GameMaker( args( 0 ) )
    }
    else {
      val gm = new GameMaker( defaultFile )
    }
}