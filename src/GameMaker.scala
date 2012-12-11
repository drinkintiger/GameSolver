 class GameMaker (fileName: String) {
  val goal = "123456780"
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
