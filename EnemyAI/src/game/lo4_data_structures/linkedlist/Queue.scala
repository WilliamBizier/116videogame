package game.lo4_data_structures.linkedlist

class Queue[A] {

  var front: LinkedListNode[A] = null
  var back: LinkedListNode[A] = null

  def empty(): Boolean = {
    front == null
  }

  def enqueue(a: A): Unit = {
    if (back == null) {
      this.back = new LinkedListNode[A](a, null)
      this.front = this.back
    } else {
      this.back.next = new LinkedListNode[A](a, null)
      this.back = this.back.next
    }
  }

  def dequeue(): A = {
    val toReturn = this.front.value
    this.front = this.front.next
    if(this.front == null){
      this.back = null
    }
    toReturn
  }

  def contains(a: A): Boolean = {
    if (this.front == null) {
      false
    }
    else if (null == this.front.find(a)) {
      false
    }
    else {
      true
    }
  }

  override def toString: String = {
    if (front == null) {
      ""
    }
    else {
      this.front.toString
    }

  }

}
