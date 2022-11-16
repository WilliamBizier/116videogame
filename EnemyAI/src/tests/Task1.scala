package tests

import game.enemyai.PlayerLocation
import game.lo4_data_structures.linkedlist.LinkedListNode
import org.scalatest._
import game.enemyai.AIPlayer
import game.maps.GridLocation

class Task1 extends FunSuite {



  test("locate player") {

    var linkPlayerLocationList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(1,1,"balls"), null)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(4,4,"cock"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(1,10,"yourfuckenmom"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(11,1,"darthvader"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(20,5,"tyrone"), linkPlayerLocationList)

    val playerloc: PlayerLocation = new AIPlayer("yourfuckenmom").locatePlayer("yourfuckenmom", linkPlayerLocationList)
    assert(playerloc == linkPlayerLocationList.next.next.value)
    println(linkPlayerLocationList.next.next.value.toString)
    //ask jesse how to print the actual value and not the mf refrence to the shit

    val playerloc2: PlayerLocation = new AIPlayer("darthvader").locatePlayer("darthvader", linkPlayerLocationList)
    assert(playerloc2 == linkPlayerLocationList.next.value)
    println(linkPlayerLocationList.next.value.toString)
  }

  test("closetPlayer"){

    var linkPlayerLocationList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(1,1,"balls"), null)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(4,4,"cock"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(1,10,"yourfuckenmom"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(2,10,"darthvader"), linkPlayerLocationList)
    linkPlayerLocationList = new LinkedListNode[PlayerLocation](new PlayerLocation(20,5,"tyrone"), linkPlayerLocationList)

    val finddacloset: PlayerLocation = new AIPlayer("yourfuckenmom").closestPlayer(linkPlayerLocationList)
    println(finddacloset)
    assert(finddacloset==linkPlayerLocationList.next.value)


  }

  test("the path way shit"){
    val startingpoint: GridLocation = new GridLocation(5,5)
    val endingpoint: GridLocation = new GridLocation(1,3)

    val cockandballs: LinkedListNode[GridLocation] = new AIPlayer("yourfuckenmom").computePath(startingpoint,endingpoint)
    println(cockandballs.toString)
    assert(endingpoint.x==cockandballs.next.next.next.next.next.next.value.x)
    assert(endingpoint.y==cockandballs.next.next.next.next.next.next.value.y)
    assert(cockandballs.size() == 7)
    assert(cockandballs.value.x == 5)
    assert(cockandballs.value.y == 5)


  }

}
