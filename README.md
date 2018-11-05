# java11-collectors-collectingAndThen
The main goal of this project is to show how to use `collectingAndThen`
from collectors API.

# preface
```
public static<T,A,R,RR> 
Collector<T,A,RR> 
collectingAndThen(Collector<T,A,R> downstream,
                  Function<R,RR> finisher)
```
* `<T>`: the type of the input elements
* `<A>`: intermediate accumulation type of the downstream collector
* `<R>`: result type of the downstream collector
* `<RR>`: result type of the resulting collector
* **downstream** - a collector
* **finisher** - a function to be applied to the final result of the 
downstream collector
* **return** - a collector which performs the action of the 
downstream collector, followed by an additional finishing step

# project description
All tests are in `CollectingAndThenTest` class
1. collect stream values to ImmutableList from guava
    ```
    ImmutableList<Integer> collect = stream
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(), 
                    ImmutableList::copyOf));    
    ```
1. in a stream of integers find a max positive value or return -1
    ```
    Integer max = stream
            .filter(x -> x >= 0)
            .collect(Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.<Integer>naturalOrder()),
                    x -> x.orElse(-1)));    
    ```
1. collect stream to list and check if result list is not empty
    ```
	Boolean isEmpty = stream
                      // other operations
                      .collect(Collectors.collectingAndThen(
                              Collectors.toList(),
                              List::isEmpty)));    
    ```