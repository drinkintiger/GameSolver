object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  type State = List[Int]
  trait Move {
      def change( state: State ): State
    }
  def Up(config: List[Int]): List[Int] = {
 		val slot = config.indexOf(0)
 		val temp = config(slot - 3)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		val temp1 = config updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		return temp1
 	}                                         //> Up: (config: List[Int])List[Int]
 	
 	def Down(config: List[Int]): List[Int] = {
 		val slot = config.indexOf(0)
 		val temp = config(slot + 3)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		val temp1 = config updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		return temp1
 	}                                         //> Down: (config: List[Int])List[Int]
 	
 	def Left(config: List[Int]) : List[Int] ={
 		val slot = config.indexOf(0)
 		val temp = config(slot - 1)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		val temp1 = config updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		return temp1
 	}                                         //> Left: (config: List[Int])List[Int]
 	
 	def Right(config: List[Int]) : List[Int] = {
 		val slot = config.indexOf(0)
 		val temp = config(slot + 1)
 		def change( state: State ) = state updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		val temp1 = config updated ( slot, temp ) updated ( config.indexOf(temp), 0 )
 		return temp1
 	}                                         //> Right: (config: List[Int])List[Int]
 	
 	val temp = List (2,4,3,1,8,0,7,6,5)       //> temp  : List[Int] = List(2, 4, 3, 1, 8, 0, 7, 6, 5)
 	
 	Up(temp)                                  //> res0: List[Int] = List(2, 4, 0, 1, 8, 3, 7, 6, 5)
 	Down(temp)                                //> res1: List[Int] = List(2, 4, 3, 1, 8, 5, 7, 6, 0)
 	Left(temp)                                //> res2: List[Int] = List(2, 4, 3, 1, 0, 8, 7, 6, 5)
 	Right(temp)                               //> res3: List[Int] = List(2, 4, 3, 1, 8, 7, 0, 6, 5)
}