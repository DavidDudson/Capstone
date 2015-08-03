def nextMove(game):
    selection = -1
    maxGain = -1
    for n in game:
        gain = n
        i = 1
        while 2*i < n :
            if n % i == 0 and i in game:
                gain = gain - i
            i = i+1
        if gain > maxGain:
            selection = n
            maxGain = gain
    return selection