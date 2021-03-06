 class Path ( history: List[Move] ) {
    type State = List[Int]
    val startState = Nil
    
    // final state led to by the history of moves
    def endState: State = trackState( history )

    // method that applies the history list, starting at the END,
    // and working backwards to the front, moving from startState onwards
    def trackState( ms: List[Move] ): State = ms match {
      case Nil          => startState
      case move :: tail => Configuration(trackState( tail )) move(move)
    }

    // method to add create a new Path by adding some Move to front of history
    def extend( move: Move ) = new Path( move :: history )

    // for pretty printing
    override def toString = 
      ( history.reverse mkString " " ) + "==>> " + endState
      
 }