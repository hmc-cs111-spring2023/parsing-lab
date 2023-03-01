package calculator.parser

import scala.util.parsing.combinator._
import calculator.ir._

object CalcParser extends JavaTokenParsers with PackratParsers {

  // parsing interface
  def apply(s: String): ParseResult[Expr] = parseAll(expr, s)

  // expressions
  lazy val expr: PackratParser[Expr] =
    expr ~ "+" ~ term ^^ { case l ~ "+" ~ r => Plus(l, r) }
      | expr ~ "-" ~ term ^^ { case l ~ "-" ~ r => Minus(l, r) }
      | term

  // terms
  lazy val term: PackratParser[Expr] =
    term ~ "*" ~ fact ^^ { case l ~ "*" ~ r => Times(l, r) }
      | term ~ "/" ~ fact ^^ { case l ~ "/" ~ r => Divide(l, r) }
      | fact

  // factors
  def fact: Parser[Expr] =
    wholeNumber ^^ { case n => Num(n.toInt) }
      | "(" ~> expr <~ ")"

}
