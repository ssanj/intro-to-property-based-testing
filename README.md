# Intro To Property-based Testing #

An introduction to Property-Based Testing. The main goal of this presentation is to fill in the gaps that other PBT presentations do not.

It covers the following:

1. The similarities and differences between Example-Based Testing and Property-Based Testing
1. Introduction to Generators
1. How to choose Properties
1. Introduction to Shrinkers
1. A few examples of increasing complexity with both EBT and PBT.
1. Some guidelines on when to use EBT and PBT.

## Examples ##

1. [Addition](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/main/scala/net/ssanj/intro/pbt/Addition.scala) [EBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/AdditionTest.scala) [PBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/AdditionProps.scala)
1. [ToAsciiUpperCase](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/main/scala/net/ssanj/intro/pbt/ToAsciiUpperCase.scala) [EBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/ToAsciiUpperCaseTest.scala) [PBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/ToAsciiUpperCaseProps.scala)
1. [Generators](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/Gens.scala)
1. [Diamond Kata](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/main/scala/net/ssanj/intro/pbt/Diamond.scala) [EBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/DiamondTest.scala) [PBT](https://github.com/ssanj/intro-to-property-based-testing/blob/master/src/test/scala/net/ssanj/intro/pbt/DiamondProps.scala)


To continuously run any test through SBT use:

```
~test-only *TestName
```

For example:

```
~test-only *AdditionTest
```

or

```
~test-only *AdditionProps
```

To run a test with more than a hundred permutations use:

```
test:run -s number_of_permutations
```

and then choose the required test from the menu.

For example:

```
test:run -s 10000
```

## Presentation ##

1. See [intro-to-property-based-testing.key](https://github.com/ssanj/intro-to-property-based-testing/blob/master/intro-to-property-based-testing.key)
1. See [intro-to-property-based-testing-simple-msug.key](https://github.com/ssanj/intro-to-property-based-testing/blob/master/intro-to-property-based-testing-simple-msug.key)

## Links ##

1. [The lazy programmer's guide to writing 1000's of tests: An introduction to property based testing - Scott Wlaschin](https://skillsmatter.com/skillscasts/6432-the-lazy-programmers-guide-to-writing-1000s-of-tests-an-introduction-to-property-based-testing)
1. [SBTB 2014 - I Dream of Gen'ning - Kelsey Gilmore-Innis](https://www.youtube.com/watch?v=lgyGFG6hBa0)
1. [Practical Property-Based Testing - Charles O'Farrell](https://yow.eventer.com/yow-lambda-jam-2015-1305/practical-property-based-testing-by-charles-o-farrell-1884)
1. [Midwest.io 2014 - Property-Based Testing for Better Code - Jessica Kerr](https://www.youtube.com/watch?v=shngiiBfD80)
1. [Testing the Hard Stuff and Staying Sane - John Hughes](https://www.youtube.com/watch?v=zi0rHwfiX1Q)
