package code.interview


object ContentTransformer {

  /*
     split each line using specific separator depending on the file type,
     replace empty value per 0.
   */
  def transform( input: (String, String) ): Array[(Int, Int)] = {
    val (fileName, content) = input
    val separator = if(fileName.contains(".csv")) "," else "\t"
    val stringToInt = (input: String ) => if (input.isEmpty) 0 else input.toInt

    content.split("\\r?\\n").drop(1).map(_.split(separator)).map(line =>
      line.toList match {
        case (index: String) :: (value: String) :: Nil =>
          ( stringToInt(index), stringToInt(value) )
        case (index: String) ::  Nil =>
          ( stringToInt(index), 0 )
        case (index: String) ::  (value: String) :: _ =>
          // more than 2 values => error
          ( stringToInt(index),  stringToInt(value) )
        case Nil =>
          ( 0, 0 )
      }
    )
  }
}
