import numpy as np
import matplotlib.pyplot as plt
import database,query
import pprint
import numpy as np
import matplotlib.pyplot as plt

def autolabel(rects):
    # attach some text labels
    for rect in rects:
        height = 1.2*rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.05*height, '%d'%int(height),
                ha='center', va='bottom')

