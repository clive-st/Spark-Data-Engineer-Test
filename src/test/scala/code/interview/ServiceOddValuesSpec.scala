package code.interview

import org.scalatest.flatspec._
import org.scalatest.matchers._

class ServiceOddValuesSpec extends AnyFlatSpec with should.Matchers {
  "ServiceOddValues" should "be able to return multiple odds values" in {
    val oddValues= ServiceOddValues.filteringOddValues( (1,  Array(1,2,3,4,5,1,2)))
    assert( oddValues === (1, List(3,4,5)))
  }

  "ServiceOddValues" should "be able to return single odd value" in {
    val oddValues= ServiceOddValues.filteringOddValues( (1,  Array(1,2,3,4,5,1,2,3,4)))
    assert( oddValues === (1, List(5)))
  }

  "ServiceOddValues" should "be able to return empty list for the values if there is no odd one" in {
    val oddValues= ServiceOddValues.filteringOddValues( (1,  Array(1,1)))
    assert( oddValues === (1, List()))
  }

  "ServiceOddValues" should "be able to return reduce and keep odd values test 1" in {
    val oddValues = ServiceOddValues.filteringOddValues((1, Array(1, 2, 3, 4, 5, 1, 2, 3, 4,9)))
    assert(oddValues === (1, List(5,9)))

    val oddValues2 = ServiceOddValues.filteringOddValues((2, Array(1, 2, 3, 4, 6, 1, 2, 3, 4)))
    assert(oddValues2 === (2, List(6)))

    val result = List(List(oddValues,oddValues2)).reduce(ServiceOddValues.reduceKeepOnlyOddValues)
    assert( result === List((1, List(5,9)),(2,List(6))))
  }

  "ServiceOddValues" should "be able to return reduce and keep odd values test 2" in {
    val oddValues = ServiceOddValues.filteringOddValues((3, Array(1,1, 2, 3, 4, 5, 1, 2, 3, 4,9,9)))
    assert(oddValues === (3, List(1,5)))

    val oddValues2 = ServiceOddValues.filteringOddValues((2, Array(1, 2, 3, 4, 6, 1, 2, 3, 4)))
    assert(oddValues2 === (2, List(6)))

    val result = List(List(oddValues,oddValues2)).reduce(ServiceOddValues.reduceKeepOnlyOddValues)
    assert( result === List((3, List(1,5)),(2,List(6))))
  }

}
