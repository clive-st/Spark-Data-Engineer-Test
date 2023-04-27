package code.interview

import scala.annotation.tailrec

object ServiceOddValues {

  /*
     requirement: values have to be sorted
     we keep only the value present an odd number of times
     (we do not need to count each value, we can "remove" them when we have 2 identic value ( 2 ,2, ... ) = (...)
     we only need to keep the odd ones, as even + odd = odd, even + even = odd, even does not provide
     any information.
     note: in the first impl, I was assuming one odd per files => recursive method, but finally we should be
     able to use fold too.
   */
  @tailrec
  private def searchOddValues(values: List[Int], currentValue:Option[Int] = None, result: List[Int] = List.empty):List[Int] = {
    if( values.isEmpty ) result ++ currentValue.toList
    else if (currentValue.exists(_ == values.head)) { //2 times same value => even => discard
      searchOddValues(values.tail, None, result)
    } else if (currentValue.isDefined) // 1 time value a, then b => odd => keep
      searchOddValues(values.tail, Some(values.head), result ++ currentValue.toList)
    else
      searchOddValues(values.tail, Some(values.head), result)
  }

  def filteringOddValues(input: (Int, Array[Int])): (Int, List[Int]) ={
    val (index , values ) =input
    (index, searchOddValues(values.sorted.toList))
  }

  /*
    we merge 2 sub results each time, if a key is present in both, we need to
    check if diff( a.values, b.values ) to get the odd ones ( if present in both, odd+odd == even)
   */
  def reduceKeepOnlyOddValues( a: List[(Int,List[Int])], b: List[(Int, List[Int])]): List[(Int, List[Int])] ={
    val commonKeys = a.map(_._1).intersect(b.map(_._1))
    val mergeCommon = for {i <- commonKeys} yield ((i, a.toMap.get(i).toList.flatMap(values => values.diff(b.toMap.get(i).toList.flatten))))
    a.filterNot(keyvalue => commonKeys.contains(keyvalue._1)) ++ b.filterNot(keyvalue => commonKeys.contains(keyvalue._1)) ++ mergeCommon
  }

}
