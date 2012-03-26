import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.measurement_stats())

N = len(result)
x_ticks = tuple_gen.base_str(result,0)
wifi_vals = tuple_gen.base_int(result,2)
mobile_vals = tuple_gen.base_int(result,3)

ind = np.arange(N)  # the x locations for the groups
width = 0.35       # the width of the bars

fig = plt.figure()
ax = fig.add_subplot(111)
rects1 = ax.bar(ind, wifi_vals, width, color='r')

rects2 = ax.bar(ind+width, mobile_vals, width, color='y')

# add some
ax.set_ylabel('Count')
ax.set_title('Usage over Hour of Day')
ax.set_xticks(ind+width)
ax.set_xticklabels(x_ticks,rotation='vertical')

ax.legend( (rects1[0], rects2[0]), ('Measurements', 'Users') )

plt.show()