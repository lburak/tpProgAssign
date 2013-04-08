###Quiz
##
###Question 1:
###Are there any inefficiencies in this code? How do you get rid of them?
##
##sum = 0
##for i in range(0, 1000000):
##    sum = sum + i
##print sum
##
###This code calculates the following expression:
###\sum\limits_{i=0}^999999 i = \frac{(999999)(1000000)}{2}
###as is evident this loop which sequentially calculates the sum
###from 0 to 999999 can replaced by the following expression in python
##
##sum = ((999999)*(1000000))/2
##print sum
##
##
###Question 2:
###Given two lists of equal length
### a = [a1, a2, a3]
### b = [b1, b2, b3]
###generate a sorted unique list of sums (a1+b1), (a2+b2), ...
##
def sumUniqueSort(a, b):
    sumedlist = list(map(add, a, b))
    sortedlist = quicksort(sumedlist)
    return removedupes(sortedlist)

def add(x, y):
    return x+y

def quicksort(array):
    if len(array) <= 1:
        return array
    pivot = array[0]
    less = []
    greater = []
    for x in array[1:]:
        if x < pivot:
            less.append(x)
        else:
            greater.append(x)
    return quicksort(less) + [pivot] + quicksort(greater)

def removedupes(array):
    if len(array) <= 1:
        return array
    first = array[0]
    second = array[1]
    if first == second:
        return [first] + removedupes(array[2:])
    else:
        return [first] + removedupes(array[1:])
