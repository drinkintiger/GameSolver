 class Path ( history: List[Int] ) {
   /* // final state led to by the history of moves
    def endState: State = trackState( history )

    // method that applies the history list, starting at the END,
    // and working backwards to the front, moving from startState onwards
    def trackState( ms: List[Move] ): State = ms match {
      case Nil          => startState
      case move :: tail => move change trackState( tail )
    }

    // method to add create a new Path by adding some Move to front of history
    def extend( move: Move ) = new Path( move :: history )

    // for pretty printing
    override def toString = 
      ( history.reverse mkString " " ) + "==>> " + endState
  */
 }