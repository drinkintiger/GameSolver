 case class Configuration (config: List[List[Int]]) {
    
	// States
    type State = List[Int]
    val startState = config.head
  
    trait Move {
      def change( state: State ): State
    }

    // Empty changes any state by reducing contents of given glass to 0
 	case class Up(config: List[Int]) extends Move {
 		val slot = config.indexOf(0)
 		val temp = config(slot - 3)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 	}
 	
 	case class Down(config: List[Int]) extends Move {
 		val slot = config.indexOf(0)
 		val temp = config(slot + 3)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 	}
 	
 	case class Left(config: List[Int]) extends Move {
 		val slot = config.indexOf(0)
 		val temp = config(slot - 1)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 	}
 	
 	case class Right(config: List[Int]) extends Move {
 		val slot = config.indexOf(0)
 		val temp = config(slot + 1)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 	}
 	
 	// Pour changes state by transferring whatever will fit into glass "to",
 	// taken from glass "from"
 	case class Pour( from: Int, to: Int ) extends Move {
 		def change( state: State ) = {
 			// "amount" is the smallest of current contents of "from" glass and
 			// the room left-over in "to" glass
 			val amount = state( from ) min ( capacities( to ) - state( to ) )
 					state updated ( from, state( from ) - amount ) updated ( to, state( to ) + amount )
 		}
  
 	}
 	
 	val glasses = 0 until capacities.length

 	val moves =
 		( for ( g <- glasses ) yield Empty( g ) ) ++
 		( for ( g <- glasses ) yield Fill( g ) ) ++
 		( for { 
          from <- glasses 
          to <- glasses if from != to 
 		  } yield Pour( from, to ) ) */
 }
