 import scala.io.Source._

class GameMaker (fileName: String) {
  val goal = "123456780"
  val lines = fromFile(fileName).getLines.toList
  val capacities = lines map (x => (x + '0').toInt) 
  
  println("Starting Configuration")
  for(e <- lines) println(e) 
  
  if ( lines == goal ) println("Already the solution.")
  
  else{
    // States
  type State = List[Int]
  val startState = capacities map ( x => 0 )
  
   // Moves:
  // base trait
  trait Move {
    def change( state: State ): State
  }
  
    // Empty changes any state by reducing contents of given glass to 0
  case class Empty( glass: Int ) extends Move {
    def change( state: State ) = state updated ( glass, 0 )
  }
  
    // Fill changes state by filling glass to capacity
  case class Fill( glass: Int ) extends Move {
    def change( state: State ) = state updated ( glass, capacities( glass ) )
  }
  
    // Pour changes state by transferring whatever will fit into glass "to",
  // taken from glass "from"
  case class Pour( from: Int, to: Int ) extends Move {
    def change( state: State ) = {
      // "amount" is the smallest of current contens of "from" glass and
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
        } yield Pour( from, to ) )
        
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
}