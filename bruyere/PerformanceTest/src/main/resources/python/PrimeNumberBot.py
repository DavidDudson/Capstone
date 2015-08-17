def nextMove(game):
    for i in xrange(len(game), 1, -1):
        x = game[i]
        if isPrimeNumber(x):
            return x
    return game[-1]

primes = [2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97]
def isPrimeNumber(x):
    return (x in primes)

