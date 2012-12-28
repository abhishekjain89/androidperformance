import sys
sys.path.append('../')
import matplotlib.pyplot as plt
import database, query, tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt

# EXAMPLE: python avg_over_tod_dst_nt.py www.google.com EVDO_A
result = database.query(query.lastmile_over_time_finegrain_dst_nt(sys.argv[1],sys.argv[2]))


data = tuple_gen.group_by(result, 0)

plt.figure(1)                # the first figure

axisNum = 0
row = 0
col = 0
for key in data.keys():
    
    data2 = tuple_gen.group_by(data[key], 1)
    list_keys = data2.keys()
    row += 1
    col = 0
    for key2 in sorted(list_keys):
        axisNum += 1
        col += 1
        ax = plt.subplot(len(data), len(data2), axisNum)

        if row == 1 :
            plt.title(key2)
        if col == 1 :
            plt.ylabel(key)
                    
        x_ticks = tuple_gen.hour_minute_period(data2[key2], 2, 3, 30)
        #y_vals = tuple_gen.normalize(tuple_gen.base_int(data2[key2], 4))
        y_vals = tuple_gen.base_int(data2[key2], 4)
        plt.plot(x_ticks, y_vals)
        
        for tick in ax.get_xticklabels():
            tick.set_visible(True)
        for tick in ax.get_yticklabels():
            tick.set_visible(True)



plt.show()



