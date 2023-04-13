## The Algorithm
    Given: An Array of Strings, lines
    Output: The Anagram Sets of lines

    for each line in lines: 
        line = {
            sorted = sort(line),
            unsorted = line
        }
    sort(lines) {
        comparison between line.sorted instances
    }
    for each line in lines: print(line.unsorted) {
        print a notification when line.sorted[i] != line.sorted[i-1] (when anagram sets change)
    }

## Complexity of the Algorithm
Assume n is the number of lines, m is the number of characters per line
The algorithm is split into two parts

1. The Reader
2. The Algorithm

The Reader merely reads through the given file, for sake of simplicity and since it is rather quick to read the file, I'll consider this subalgorithm's complexity to be O(n)

The Algorithm is further split into three parts

### 1. Sorting Individual Lines and Pairing the Sorted Line to its Unsorted Version
    I go line by line, sorting the line using insertion sort
    I used insertion sort since when I ran tests for both non-parallelized and parallelized quicksort, insertion sort still outperformed them both in raw time, still the complexity is O(m^2)
    This makes the complexity of this step O(nm^2), though in application on english words, this algorithm is quicker than the quicksorted version which has a compleixty of O(nmlogm)
### 2. Sorting the Array of Lines
    For this I used a parallelized quicksort algorithm. On my 16-core computer, all cores are obviously used by the end, thus not actually reducing the complexity, and no realistic PC would be able to parallelize the recursive layer with the most calls
    Thus, though the time I found to be around an order of magnitude quicker than non-parallelized quicksort, the complexity is not reduced, and in actual analysis the actual number of computational resources is still the same as non-parallelized, it's just quicker for runtime
    This means this part of the algorithm hasa complexity of O(nlogn)
### 3. Printing the Array of Lines, taking into account where different sets of Algorithms are delimited
    I simply run through the sorted list, marking when the sorted version changes, making this O(n)

## Optimizations to the Sorting Algorithm
I considered multiple optimizations to my sorting algorithm in this project
1. I parallelized the algorithm, which led to a drop in runtime to 1/10 of the non-parallelized version
2. I also considered parallelizing the algorithm for individual lines, but the overhead was high enough that the runtime was lengthened
3. I considered transfering between insertion sort and quick sort depending on the size of the subarray, in neither the case of the individuals lines nor the list of lines did this change the runtime significantly
4. I considered spliting up the quicksort algorithm to only sort strings of the same size (as the would be sorted separately anyways). I was unable to accomplish this along wiht the parallelized version of quicksort, but when I implemented it in the unparallized version it resulted in a sigificantly reduced runtime
    For this the C(n) looks like SUM( n[i] * log( n[i] )) whre n[i] is the number of strings of length i, this results in the runtime being less than nlogn

## End Analysis
Though I found a solution of O(nmlogm + nlogn + n), I opted for a solution of O(nm^2 + nlogn + n), as m is small enough that the overhead of the nmlogm version slows down the overall algorithm in runtime. If the lines were of a length much greater, then the mlogm version would be more effective. Threshholds could also be implemented to transfer between these dependent on the subarray in the quicksort algorithm.
By far the longest portion of runtime is printing despite it being linear (though I believe most print functions depend on the number of characters, so this might be quadratic as in O(nm))
