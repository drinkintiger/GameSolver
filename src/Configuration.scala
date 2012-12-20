 case class Configuration (config: List[Int]) {
   val initialPath = config
   val toMove = initialPath.indexOf(0) 
   
   type Configuration = List[Int]
   
   
   def move (m: Move): Configuration = {
     
     def isValid (m: Move): Boolean = m match {
       case Left => if (toMove % 3 == 0) false else true
       case Right => if (toMove == 2 || toMove == 5 || toMove == 8) false else true
       case Up => if (toMove <= 2) false else true
       case Down => if (toMove >= 6) false else true
     }       
       
     def makeMove (m: Move): Configuration = m match {
       case Left => val temp = initialPath(toMove - 1); initialPath updated ( toMove, temp ) updated ( config.indexOf(temp), 0 )
       case Right => val temp = initialPath(toMove + 1); initialPath updated ( toMove, temp) updated ( config.indexOf(temp), 0)
       case Up => val temp = initialPath(toMove - 3); initialPath updated ( toMove, temp) updated ( config.indexOf(temp), 0)
       case Down => val temp = initialPath(toMove + 3); initialPath updated ( toMove, temp) updated ( config.indexOf(temp), 0)
     }
     
     if (isValid(m)) makeMove(m) else config
   }
 }

 sealed abstract class Move
 case object Left extends Move
 case object Right extends Move
 case object Up extends Move
 case object Down extends Move