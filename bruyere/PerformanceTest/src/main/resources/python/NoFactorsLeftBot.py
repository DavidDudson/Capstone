def nextMove(game):
    for i in range(len(game), 0):
        x = game[i]
        if not factorsLeft(x):
            return x
    return game[-1]
        
def factorsLeft(x):
    for i in range(2,x):
        if x%i == 0:
            return TRUE
    return FALSE