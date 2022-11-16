package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.graphs.Graph
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}
import game.lo4_data_structures.linkedlist.Queue

import scala.collection.mutable

class AIPlayer(val id: String) {


  // TODO: Replace this placeholder code with your own
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    if(playerLocations.value.playerId==playerId) {
      playerLocations.value
    }
    else {
      locatePlayer(playerId,playerLocations.next)
    }
  }

  // TODO: Replace this placeholder code with your own
  // TODO: suck a fucken dic
  /*
  def helperClosestPlayer(playerLocation: LinkedListNode[PlayerLocation],playerlocstat: PlayerLocation): PlayerLocation = {
    if(playerLocation.next==null){
      playerlocstat
    }else{
      var otherdudeloc: PlayerLocation = locatePlayer(this.id,playerLocation)
      var elords: Double = math.sqrt(math.pow((otherdudeloc.x - playerLocation.value.x), 2)
        + math.pow((otherdudeloc.y - playerLocation.value.y), 2))

    }
  }

   */

  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    var mapping:Map[Double,PlayerLocation]=Map()
    var playerlocthing: PlayerLocation = locatePlayer(this.id, playerLocations)
    var linky:LinkedListNode[PlayerLocation]=playerLocations
    while(linky.next != null) {
      if (linky.value.playerId != this.id) {
        var step1: Double = (Math.pow((playerlocthing.x - linky.value.x), 2))
        var step2: Double = (Math.pow((playerlocthing.y - linky.value.y), 2))
        var step3: Double = Math.sqrt(step1+step2)
        println(step3)
        mapping += (step3 -> linky.value)
        linky = linky.next
      } else {
        linky = linky.next
      }
    }
    if(linky.value.playerId !=this.id) {
      var lordsdistance:Double=(Math.sqrt(Math.pow((playerlocthing.x - linky.value.x), 2)+ Math.pow((playerlocthing.y - linky.value.y), 2)))
      mapping += (lordsdistance -> linky.value)
      var results : List[Double] = mapping.keys.toList
      var sorting: List[Double] = results.sortWith(_<_)
      var fin: PlayerLocation = mapping(sorting.head)
      println(mapping.size)
      fin
    }else{
      var values: List[Double] = mapping.keys.toList
      var valuesSorted: List[Double] = values.sortWith(_<_)
      var out: PlayerLocation = mapping(valuesSorted.head)
      out

    }
  }


  // TODO: Replace this placeholder code with your own

  def computePath(end: GridLocation, start: GridLocation): LinkedListNode[GridLocation] = {
    //end is start
    //start is end
    var startvalue: GridLocation = start
    var linky: LinkedListNode[GridLocation]=new LinkedListNode[GridLocation](start,null)
    while(startvalue.y != end.y) {
      println(567)
      if (startvalue.x == end.x) {
        if (startvalue.y > end.y) {
          startvalue = new GridLocation(startvalue.x, startvalue.y - 1)
          linky = new LinkedListNode[GridLocation](startvalue,linky)
        }

        else {
          startvalue = new GridLocation(startvalue.x, startvalue.y + 1)
          linky = new LinkedListNode[GridLocation](startvalue,linky)
        }
        //cock
      } else if (startvalue.x > end.x) {
        startvalue = new GridLocation(startvalue.x - 1, startvalue.y)
        linky = new LinkedListNode[GridLocation](startvalue,linky)
      }
      else {
        startvalue = new GridLocation(startvalue.x + 1, startvalue.y)
        linky = new LinkedListNode[GridLocation](startvalue,linky)
      }
    }
    linky
  }


  //------------------------task 2 -----------------------

  /*
      A method named "makeDecision" with:
      Parameters of type AIGameState and BinaryTreeNode of DecisionTreeValue
        The BinaryTreeNode is the root of a decision tree that will be used to determine which action the AI will take for this game state
        Review the provided DecisionTreeValue/DecisionNode/ActionNode classes for more context on these types
    Returns the action determined by the decision tree
      To determine the action, first call the check method on the DecisionTreeValue
        If a negative number is returned, navigate to the left child of the node
        If a positive number is returned, navigate to the right child of the node
        If 0 is returned, call the action method to determine which action to take and return this action
   */

  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    var tree: BinaryTreeNode[DecisionTreeValue] = decisionTree
    var whiley: Int = tree.value.check(gameState)
    while(whiley != 0){
      if(whiley>0){
        tree = tree.right
        whiley = tree.value.check(gameState)
      }
      else{
        tree = tree.left
        whiley = tree.value.check(gameState)
      }
    }
    //MovePlayer(this.id, Math.random() - 0.5, Math.random() - 0.5)
    //helperMakeDecision(a, this.root)
    tree.value.action(gameState)
  }

  //-------------------task 3--------------------------

    //pauls lecture cse 116 sec a 4/27/2022

  def breathFirstSeach[A](graph: Graph[A], startID: Int, endingID: Int): Int = {
    val toExplore: Queue[Int] = new Queue()
    val explored: Queue[Int] = new Queue()
    var themapbitch: Map[Int,Int] = Map(startID->0)
    toExplore.enqueue(startID)
    explored.enqueue(startID)
    while(!toExplore.empty()){
      val nodeToExplore=toExplore.dequeue()
      for (node <- graph.adjacencyList(nodeToExplore)){
        //println("explored: " + explored)
        //println("toexplore: " + toExplore)
        //println(themapbitch)
        if(!explored.contains(node)){
          //println("exploring: " + graph.nodes(node))
          toExplore.enqueue(node)
          explored.enqueue(node)
          themapbitch +=(node -> (themapbitch(nodeToExplore)+1))
        }
      }
    }
    themapbitch(endingID)
  }

  def distanceAvoidWalls(gameState: AIGameState,grid1: GridLocation, grid2: GridLocation): Int = {
    val gGraph: Graph[GridLocation]=gameState.levelAsGraph()
    val gridID: GridLocation => Int = location => location.x + (location.y * gameState.levelWidth)
    val start = gridID(grid1)
    val end = gridID(grid2)
    val call: Int = breathFirstSeach(gGraph,start,end)
    call

  }



  // TODO: Replace this placeholder code with your own
  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    closestPlayer(gameState.playerLocations)
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }

  def main(args: Array[String]): Unit = {

  }


}

