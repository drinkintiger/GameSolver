 import scala.io.Source._
//
class GameMaker (fileName: String) {
  val goal = "123456780"
  val lines = (fromFile(fileName).mkString.replaceAll(" ", "").replaceAll("\\n", "").toList) map (x => (x + '0').toInt)
  
  println("Starting Configuration")
  for(e <- lines) println(e) 
  // States
  type State = List[Int]
  val startState = lines

  // Moves:
  // base trait
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
 			val amount = state( from ) min ( lines( to ) - state( to ) )
 					state updated ( from, state( from ) - amount ) updated ( to, state( to ) + amount )
 		}
  
 	}

  //val glasses = 0 until lines.length
  val slot = startState.indexOf(0)
  val moves =
    (if(slot>2) Up(startState)) ++
    (if(slot<6) Down( startState ) ) ++
    (if(slot != 0 || slot !=3 || slot != 6) Left( startState ) ) ++
    (if(slot != 2 || slot !=5 || slot != 8) Right( startState ) )

  // Paths
  class Path( history: List[Move] ) {
    // final state led to by the history of moves
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
  }

  // start with empty Path
  val initialPath = new Path( Nil )

  // function to take a set of Paths and generate extensions of that Set
  // (i.e., paths that are longer by a single step than those in the Set)
  // in the form of a Stream of progressively longer Paths
  def from( paths: Set[Path], explored: Set[State] ): Stream[Set[Path]] = {
    if ( paths.isEmpty ) Stream.empty
    else {
      // the set of all paths with an additional move added to current paths,
      // so long as we never revisit anything in explored set
      val more = for {
        path <- paths
        next <- moves map path.extend
        if !( explored contains next.endState )
      } yield next

      // new states visited 
      val moreExplored = more map ( _.endState )

      // now add the input paths to front of Stream generated recursively
      // on the paths in more (adding in new explored states)
      paths #:: from( more, explored ++ moreExplored )
    }
  }

  // now we have all possible paths, as a Stream
  // (so we do not actually have to generate infinitely long paths, 
  // but will only generate them to precisely the lengths we need)
  val pathSets = from( Set( initialPath ), Set( startState ) )

  // function to solve the problem
  def solution( target: Int ): Stream[Path] =
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
}