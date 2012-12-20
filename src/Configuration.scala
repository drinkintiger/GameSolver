 case class Configuration (config: List[Int]) {
   val toMove = config.indexOf(0) 
   
   
   
   
   def move (m: Move): Configuration = {
     
     def isValid (m: Move): Boolean = m match {
       case Left => if (toMove % 3 == 0) false else true
       case Right => if (toMove == 2 || toMove == 5 || toMove == 8) false else true
       case Up => if (toMove <= 2) false else true
       case Down => if (toMove >= 6) false else true
     }
     
     if (isValid(m)) 
     
     
     Configuration(List(List(1)))
   }
   
   
   
   
   
	
 }

 sealed abstract class Move
 case object Left extends Move
 case object Right extends Move
 case object Up extends Move
 case object Down extends Move